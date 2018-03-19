package com.impaq.pos.devices;

public class Printer {


    public String print(String receipt) {
        return "************************ Printer *******************************\n" +
                receipt +
                "****************************************************************\n";
    }
}
