package com.impaq.pos.repository;

import com.impaq.pos.entity.Receipt;

import java.util.List;

public interface ShopReceiptsRepository {

    public void save(Receipt receipt);

    public List<Receipt> findAll();
}
