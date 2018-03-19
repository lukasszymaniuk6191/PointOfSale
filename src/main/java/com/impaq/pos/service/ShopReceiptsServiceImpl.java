package com.impaq.pos.service;

import com.impaq.pos.dto.Client;
import com.impaq.pos.entity.Product;
import com.impaq.pos.entity.Receipt;
import com.impaq.pos.repository.ShopReceiptsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopReceiptsServiceImpl implements ShopReceiptsService {


    private ShopReceiptsRepository shopReceiptsRepository;
    private Receipt receipt;

    @Autowired
    public ShopReceiptsServiceImpl(ShopReceiptsRepository shopReceiptsRepository) {
        this.shopReceiptsRepository = shopReceiptsRepository;

    }

    public Receipt generateReceipt(Client client) {
        List<Product> productList = client.getProductList();
        String priceOfAllProducts = getPriceOfAllProducts(productList);

        receipt = new Receipt(productList, priceOfAllProducts);
        this.shopReceiptsRepository.save(receipt);

        return receipt;

    }

    public String getPriceOfAllProducts(List<Product> productList) {
        double priceOfAllProducts = 0;

        for (Product product : productList) {
            priceOfAllProducts += getPriceOfAllProductsOneType(product);
        }

        return String.valueOf(priceOfAllProducts) + "$";
    }

    public double getPriceOfAllProductsOneType(Product product) {
        double price = Double.parseDouble(product.getPrice().replace("$", ""));
        int amount = product.getAmountOfProducts();

        return price * amount;
    }


    public String printReceipt() {
        String allProductsData = "";
        String productData = "";

        for (Product product : receipt.getProductList()) {
            productData = product.getName() + " x " + product.getAmountOfProducts() + "  -  " + product.getPrice();
            allProductsData += productData + System.lineSeparator();
        }

        return System.lineSeparator()
                + allProductsData
                + "sum: " + receipt.getPriceOfAllProducts() + System.lineSeparator() + System.lineSeparator();
    }


}
