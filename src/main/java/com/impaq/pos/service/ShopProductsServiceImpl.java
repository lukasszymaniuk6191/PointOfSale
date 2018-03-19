package com.impaq.pos.service;

import com.impaq.pos.entity.Product;
import com.impaq.pos.repository.ShopProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopProductsServiceImpl implements ShopProductsService {

    private ShopProductsRepository shopRepository;

    public ShopProductsServiceImpl() {
    }

    @Autowired
    public ShopProductsServiceImpl(ShopProductsRepository shopRepository) {
        this.shopRepository = shopRepository;
    }


    public Product getProductFromStore(String barCode) {
        Product product = null;
        product = shopRepository.getProductByBarCode(barCode);

        if (product != null) {
            shopRepository.reduceTheNumberOfProducts(barCode);
        }
        return product;

    }


}
