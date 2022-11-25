package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findCartItemByProductId(Long productId);

    List<CartItem> findAllByCart(Cart cart);
}
