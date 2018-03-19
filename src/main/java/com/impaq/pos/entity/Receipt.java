package com.impaq.pos.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Receipt {

    @Id
    @GeneratedValue
    private long id;
    private List<Product> productList;
    private String priceOfAllProducts;

    public Receipt() {
        productList = new ArrayList<>();
    }

    public Receipt(List<Product> productList, String priceOfAllProducts) {
        this.productList = productList;
        this.priceOfAllProducts = priceOfAllProducts;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getPriceOfAllProducts() {
        return priceOfAllProducts;
    }

    public void setPriceOfAllProducts(String priceOfAllProducts) {
        this.priceOfAllProducts = priceOfAllProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receipt)) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(getProductList(), receipt.getProductList()) &&
                Objects.equals(getPriceOfAllProducts(), receipt.getPriceOfAllProducts());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getProductList(), getPriceOfAllProducts());
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", productList=" + productList +
                ", priceOfAllProducts='" + priceOfAllProducts + '\'' +
                '}';
    }
}
