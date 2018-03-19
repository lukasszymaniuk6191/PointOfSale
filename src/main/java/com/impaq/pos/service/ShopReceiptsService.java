package com.impaq.pos.service;

import com.impaq.pos.entity.Receipt;
import com.impaq.pos.dto.Client;

public interface ShopReceiptsService {

    public Receipt generateReceipt(Client client);

    public String printReceipt();
}
