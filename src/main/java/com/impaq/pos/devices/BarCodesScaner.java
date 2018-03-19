package com.impaq.pos.devices;

import java.util.Scanner;

public class BarCodesScaner {

    private String barCode;
    private Scanner sc;

    public BarCodesScaner() {
        sc = new Scanner(System.in);
    }


    public String addProduct() {
        System.out.println("Add product code - Enter, exit transaction - 'exit'");
        return this.barCode = sc.nextLine();
    }


}
