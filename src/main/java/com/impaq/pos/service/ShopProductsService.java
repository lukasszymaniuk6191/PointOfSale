package com.impaq.pos.service;

import com.impaq.pos.entity.Product;

public interface ShopProductsService {

    public Product getProductFromStore(String barCode);

}
