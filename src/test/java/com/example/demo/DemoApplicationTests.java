package com.example.demo;

import com.impaq.pos.DemoApplication;
import com.impaq.pos.entity.Product;
import com.impaq.pos.entity.Receipt;
import com.impaq.pos.main.PointOfSale;
import com.impaq.pos.dto.Client;
import com.impaq.pos.repository.ShopProductsRepository;
import com.impaq.pos.service.ShopProductsService;
import com.impaq.pos.service.ShopReceiptsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {


    @Autowired
    private ShopProductsRepository shopProductsRepository;
    @Autowired
    public ShopProductsService shopProductsService;
    @Autowired
    private ShopReceiptsService shopReceiptsService;
    @Autowired
    private PointOfSale pointOfSale;

    private Client client;

    @Before
    public void init() {
        List<Product> productsList = new ArrayList<Product>();
        productsList.add(new Product("111-111-111", "Mineral Water", "5$", 50));
        productsList.add(new Product("222-222-222", "Coca-Cola", "2$", 20));
        productsList.add(new Product("333-333-333", "Coffee", "3$", 15));
        productsList.add(new Product("444-444-444", "Beer", "2$", 25));
        productsList.add(new Product("555-555-555", "Orange juice", "4$", 0));
        productsList.add(new Product("666-666-666", "Vodka", "20$", 10));
        productsList.add(new Product("777-777-777", "Wine", "12$", 12));

        //shopProductsRepositoryMock = Mockito.mock(ShopProductsRepositoryImpl.class);
        shopProductsRepository.saveAll(productsList);

        client = new Client();
        client.addProduct(new Product("222-222-222", "Coca-Cola", "2$", 20));
        client.addProduct(new Product("222-222-222", "Coca-Cola", "2$", 20));
        client.addProduct(new Product("333-333-333", "Coffee", "3$", 15));
        client.addProduct(new Product("444-444-444", "Beer", "2$", 25));
        client.addProduct(new Product("777-777-777", "Wine", "12$", 12));

    }


    @Test
    public void testShopProductRepository() {

        Product product = shopProductsRepository.getProductByBarCode("777-777-777");
        assertEquals("Wine", product.getName());
        assertEquals("12$", product.getPrice());
        assertEquals(12, product.getAmountOfProducts());

        List<Product> productList = shopProductsRepository.findAll();
        assertEquals(7, productList.size());
        assertTrue(productList.stream().filter(o -> o.equals(
                new Product("111-111-111", "Mineral Water", "5$", 50))).findFirst().isPresent());
        assertTrue(productList.stream().filter(o -> o.equals(
                new Product("444-444-444", "Beer", "2$", 25))).findFirst().isPresent());

        shopProductsRepository.reduceTheNumberOfProducts("444-444-444");

        assertTrue(productList.stream().filter(o -> o.equals(
                new Product("444-444-444", "Beer", "2$", 24))).findFirst().isPresent());

    }

    @Test
    public void testShopProductsService() {
        Product product = shopProductsService.getProductFromStore("777-777-777");
        assertEquals("Wine", product.getName());
        assertEquals("12$", product.getPrice());
        assertEquals(11, product.getAmountOfProducts());

        product = shopProductsService.getProductFromStore("777-777-777");
        assertEquals("Wine", product.getName());
        assertEquals("12$", product.getPrice());
        assertEquals(10, product.getAmountOfProducts());

        product = shopProductsService.getProductFromStore("777-777-777");
        assertEquals("Wine", product.getName());
        assertEquals("12$", product.getPrice());
        assertEquals(9, product.getAmountOfProducts());

        product = shopProductsService.getProductFromStore("222-222-223");
        assertNull(product);
    }

    @Test
    public void testShopRecipmentsService() {

        Receipt receipt = shopReceiptsService.generateReceipt(client);
        assertTrue(receipt.getPriceOfAllProducts().equals("21.0$"));
        assertTrue(receipt.getProductList().stream().filter(o -> o.equals(
                new Product("444-444-444", "Beer", "2$", 1))).findFirst().isPresent());
        assertTrue(receipt.getProductList().stream().filter(o -> o.equals(
                new Product("333-333-333", "Coffee", "3$", 1))).findFirst().isPresent());
        assertTrue(receipt.getProductList().stream().filter(o -> o.equals(
                new Product("222-222-222", "Coca-Cola", "2$", 2))).findFirst().isPresent());
        assertTrue(receipt.getProductList().stream().filter(o -> o.equals(
                new Product("777-777-777", "Wine", "12$", 1))).findFirst().isPresent());


        String printReceipt = shopReceiptsService.printReceipt();
        assertTrue(printReceipt.contains("Coca-Cola x 2  -  2$"));
        assertTrue(printReceipt.contains("Coffee x 1  -  3$"));
        assertTrue(printReceipt.contains("Beer x 1  -  2$"));
        assertTrue(printReceipt.contains("Wine x 1  -  12$"));
        assertTrue(printReceipt.contains("sum: 21.0$"));


    }


    @Test
    public void testPointOfSale() {
        client = new Client();

        String lcdMessage = pointOfSale.scanProducts(client, "111-111-111");
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("name: Mineral Water,   price: 5$"));

        lcdMessage = pointOfSale.scanProducts(client, "777-777-777");
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("name: Wine,   price: 12$"));

        lcdMessage = pointOfSale.scanProducts(client, "777-777-771");
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("Product not found!"));

        lcdMessage = pointOfSale.scanProducts(client, "555-555-555");
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("Product not found!"));

        lcdMessage = pointOfSale.scanProducts(client, "");
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("Invalid bar-code!"));

        List<String> exitTransactionObjects = pointOfSale.exitTransactionAndPrintReceipt(client);

        lcdMessage = exitTransactionObjects.get(0);
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("total sum: 17.0$"));


        String strReceipt = exitTransactionObjects.get(1);
        assertTrue(strReceipt.contains("*** Printer ***"));
        assertTrue(strReceipt.contains("Mineral Water x 1  -  5$"));
        assertTrue(strReceipt.contains("Wine x 1  -  12$"));
        assertTrue(strReceipt.contains("sum: 17.0$"));

        pointOfSale.scanProducts(client, "666-666-666");
        pointOfSale.scanProducts(client, "777-777-777");
        exitTransactionObjects = pointOfSale.exitTransactionAndPrintReceipt(client);

        lcdMessage = exitTransactionObjects.get(0);
        assertTrue(lcdMessage.contains("*** LCD ***"));
        assertTrue(lcdMessage.contains("total sum: 49.0$"));


        strReceipt = exitTransactionObjects.get(1);
        assertTrue(strReceipt.contains("*** Printer ***"));
        assertTrue(strReceipt.contains("Mineral Water x 1  -  5$"));
        assertTrue(strReceipt.contains("Wine x 2  -  12$"));
        assertTrue(strReceipt.contains("Vodka x 1  -  20$"));
        assertTrue(strReceipt.contains("sum: 49.0$"));

    }


}
