package com.impaq.pos.repository;

import com.impaq.pos.entity.Receipt;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopReceiptsRepositoryImpl implements ShopReceiptsRepository {

    private List<Receipt> receiptList = new ArrayList<>();

    public void save(Receipt receipt) {
        receiptList.add(receipt);
    }

    public List<Receipt> findAll() {
        return receiptList;
    }

}
