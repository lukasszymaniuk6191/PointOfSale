package com.impaq.pos.repository;

import com.impaq.pos.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopProductsRepositoryImpl implements ShopProductsRepository {

    private List<Product> productsList = new ArrayList<Product>();

    public ShopProductsRepositoryImpl() {
        productsList.add(new Product("111-111-111", "Mineral Water", "5$", 50));
        productsList.add(new Product("222-222-222", "Coca-Cola", "2$", 20));
        productsList.add(new Product("333-333-333", "Coffee", "3$", 15));
        productsList.add(new Product("444-444-444", "Beer", "2$", 25));
        productsList.add(new Product("555-555-555", "Orange juice", "4$", 0));
    }


    public void saveAll(List<Product> productList) {
        this.productsList = productList;
    }

    public List<Product> findAll() {
        return this.productsList;
    }

    public Product getProductByBarCode(String barCode) {
        for (Product product : productsList) {
            if (product.getBarCode().equals(barCode)) {
                if (product.getAmountOfProducts() > 0) {
                    return product;
                } else {
                    return null;
                }
            }

        }
        return null;
    }


    public void reduceTheNumberOfProducts(String barCode) {
        for (Product product : productsList) {
            if (product.getBarCode().equals(barCode)) {
                product.setAmountOfProducts(product.getAmountOfProducts() - 1);
            }
        }
    }


}
