package com.cg.service.cart;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.service.IGeneralService;

import java.util.Optional;

public interface ICartService extends IGeneralService<Cart> {

    Optional<Cart> findCartByCreatedBy(String createdBy);
}
