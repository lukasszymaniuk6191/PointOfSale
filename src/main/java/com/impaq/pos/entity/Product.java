package com.impaq.pos.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;


@Entity
public class Product {

    @Id
    @GeneratedValue
    private long id;
    private String barCode;
    private String name;
    private String price;
    private int amountOfProducts;

    public Product() {
    }

    public Product(String barCode, String name, String price, int amountOfProducts) {
        this.barCode = barCode;
        this.name = name;
        this.price = price;
        this.amountOfProducts = amountOfProducts;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmountOfProducts() {
        return amountOfProducts;
    }

    public void setAmountOfProducts(int amountOfProducts) {
        this.amountOfProducts = amountOfProducts;
    }

    public void incrementAmountOfProducts() {
        this.amountOfProducts++;
    }

    public void decrementAmountOfProducts() {
        this.amountOfProducts--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getAmountOfProducts() == product.getAmountOfProducts() &&
                Objects.equals(getBarCode(), product.getBarCode()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getBarCode(), getName(), getPrice(), getAmountOfProducts());
    }

    @Override
    public String toString() {
        return "Product{" +
                "barCode='" + barCode + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", amountOfProducts=" + amountOfProducts +
                '}';
    }
}
