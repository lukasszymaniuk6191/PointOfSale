package com.impaq.pos.repository;

import com.impaq.pos.entity.Product;

import java.util.List;

public interface ShopProductsRepository {

    Product getProductByBarCode(String barCode);

    void reduceTheNumberOfProducts(String barCode);

    public void saveAll(List<Product> productList);

    public List<Product> findAll();
}
