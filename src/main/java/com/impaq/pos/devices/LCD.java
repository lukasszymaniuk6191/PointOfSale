package com.impaq.pos.devices;

import com.impaq.pos.entity.Product;

public class LCD {

    public String totalSum(String totalSum) {
        return displayOnScreen("total sum: " + totalSum);
    }

    public String productExistInDataBase(Product product) {
        return displayOnScreen("name: " + product.getName() + ",   price: " + product.getPrice());
    }

    public String productNotFound() {
        return displayOnScreen("Product not found!");
    }

    public String codeScannedIsEmpty() {
        return displayOnScreen("Invalid bar-code!");
    }

    public String displayOnScreen(String str) {
        return "********************* LCD *******************************\n" +
                str + "\n" +
                "*********************************************************\n";
    }


}
