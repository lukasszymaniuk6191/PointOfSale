package com.impaq.pos.main;

import com.impaq.pos.devices.BarCodesScaner;
import com.impaq.pos.devices.LCD;
import com.impaq.pos.devices.Printer;
import com.impaq.pos.entity.Product;
import com.impaq.pos.entity.Receipt;
import com.impaq.pos.dto.Client;
import com.impaq.pos.service.ShopProductsService;
import com.impaq.pos.service.ShopReceiptsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class PointOfSale {


    private ShopProductsService shopProductsService;
    private ShopReceiptsService shopReceiptsService;

    private BarCodesScaner barCodesScaner;
    private LCD lcd;
    private Printer printer;

    private String lcdMessage = "";
    private String strReceipt = "";
    private boolean scanProduct = true;
    private Product product = null;
    private Receipt receipt = null;


    @Autowired
    public PointOfSale(ShopProductsService shopProductsService, ShopReceiptsService shopReceiptsService) {
        this.shopProductsService = shopProductsService;
        this.shopReceiptsService = shopReceiptsService;

        barCodesScaner = new BarCodesScaner();
        lcd = new LCD();
        printer = new Printer();


    }

    public void shopping() {
        Client client = null;

        Scanner sc = new Scanner(System.in);

        while (true) {


            System.out.println("add new client -'nc', exit aplication - 'exit'");
            System.out.println("add new products to client - 'np', exit transaction 'exit'");
            String str = sc.next();

            if (str.equals("nc")) {
                client = new Client();
                scanProduct = true;
                System.out.println("********************* New client added *******************");
            }
            if (str.equals("np")) {
                scanProduct = true;
                addProductsToReceipt(client);
            }
            if (str.equals("exit")) {
                break;
            }


        }


    }

    public void addProductsToReceipt(Client client) {


        while (scanProduct) {
            String barCode = barCodesScaner.addProduct();

            if (!barCode.equals("exit")) {

                lcdMessage = scanProducts(client, barCode);

            } else {
                scanProduct = false;
                lcdMessage = exitTransactionAndPrintReceipt(client).get(0);
                strReceipt = exitTransactionAndPrintReceipt(client).get(1);
            }


            System.out.println(lcdMessage);
        }
        System.out.println(strReceipt);
    }


    public String scanProducts(Client client, String barCode) {
        if (barCode.isEmpty()) {
            lcdMessage = lcd.codeScannedIsEmpty();
        } else {
            product = shopProductsService.getProductFromStore(barCode);

            if (product != null) {
                client.addProduct(product);
                lcdMessage = lcd.productExistInDataBase(product);
            } else {
                lcdMessage = lcd.productNotFound();
            }
        }

        return lcdMessage;
    }

    public List<String> exitTransactionAndPrintReceipt(Client client) {
        receipt = shopReceiptsService.generateReceipt(client);
        List<String> objectList = new ArrayList<>();
        objectList.add(lcd.totalSum(receipt.getPriceOfAllProducts()));
        objectList.add(printer.print(shopReceiptsService.printReceipt()));

        return objectList;
    }


}
