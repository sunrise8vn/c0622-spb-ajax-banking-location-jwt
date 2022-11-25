package com.cg.service.cartItem;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICartItemService extends IGeneralService<CartItem> {

    Optional<CartItem> findCartItemByProductId(Long productId);

    List<CartItem> findAllByCart(Cart cart);
}
