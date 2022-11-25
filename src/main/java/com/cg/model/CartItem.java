package com.cg.model;

import com.cg.model.dto.CartItemResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @Column(name = "product_id")
    private Long productId;

    private String title;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    private Long quantity;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal amount;


    public CartItemResponseDTO toCartItemResponseDTO() {
        return new CartItemResponseDTO()
                .setId(id)
                .setProductId(productId)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setAmount(amount);
    }

}
