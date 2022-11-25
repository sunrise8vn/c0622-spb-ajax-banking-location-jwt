package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.dto.CartItemResponseDTO;
import com.cg.service.cart.ICartService;
import com.cg.service.cartItem.ICartItemService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;


@RestController
@RequestMapping("/api/carts")
public class CartAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IProductService productService;

    @PostMapping("/add/{productId}")
    public ResponseEntity<?> addCart(@PathVariable Long productId) {

        Optional<Product> productOptional = productService.findById(productId);

        if (!productOptional.isPresent()) {
            throw new DataInputException("Product ID not valid");
        }

        Product product = productOptional.get();

        Optional<CartItem> cartItemOptional = cartItemService.findCartItemByProductId(productId);

        if (!cartItemOptional.isPresent()) {
            String username = appUtils.getPrincipalUsername();

            Optional<Cart> cartOptional = cartService.findCartByCreatedBy(username);

            if (!cartOptional.isPresent()) {

                String title = product.getTitle();
                BigDecimal price = product.getPrice();
                Long quantity = 1L;
                BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

                Cart cart = new Cart();
                cart.setCreatedBy(username);
                cart.setTotalAmount(BigDecimal.valueOf(0L));
                cart = cartService.save(cart);

                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProductId(productId);
                cartItem.setTitle(title);
                cartItem.setPrice(price);
                cartItem.setQuantity(quantity);
                cartItem.setAmount(amount);

                cartItemService.save(cartItem);

                cart.setTotalAmount(amount);
                cartService.save(cart);

                CartItemResponseDTO cartItemResponseDTO = cartItem.toCartItemResponseDTO();

                List<CartItemResponseDTO> cartItemResponseDTOS =new ArrayList<>();
                cartItemResponseDTOS.add(cartItemResponseDTO);

                Map<String, Object> result = new HashMap<>();
                result.put("cartItems", cartItemResponseDTOS);
                result.put("totalAmount", amount);

                return new ResponseEntity<>(result, HttpStatus.OK);

            }
            else {
                Cart cart = cartOptional.get();

                String title = product.getTitle();
                BigDecimal price = product.getPrice();
                Long quantity = 1L;
                BigDecimal amount = price.multiply(BigDecimal.valueOf(quantity));

                CartItem cartItem = new CartItem();
                cartItem.setCart(cart);
                cartItem.setProductId(productId);
                cartItem.setTitle(title);
                cartItem.setPrice(price);
                cartItem.setQuantity(quantity);
                cartItem.setAmount(amount);
                cartItemService.save(cartItem);

                BigDecimal currentTotalAmount = cart.getTotalAmount();
                BigDecimal newTotalAmount = currentTotalAmount.add(amount);
                cart.setTotalAmount(newTotalAmount);
                cartService.save(cart);

                List<CartItemResponseDTO> cartItemResponseDTOS =new ArrayList<>();

                List<CartItem> newCartItems = cartItemService.findAllByCart(cart);

                for (CartItem item : newCartItems) {
                    CartItemResponseDTO cartItemResponseDTO = item.toCartItemResponseDTO();
                    cartItemResponseDTOS.add(cartItemResponseDTO);
                }

                Map<String, Object> result = new HashMap<>();
                result.put("cartItems", cartItemResponseDTOS);
                result.put("totalAmount", newTotalAmount);

                return new ResponseEntity<>(result, HttpStatus.OK);
            }
        }
        else {

            BigDecimal price = product.getPrice();

            CartItem cartItem = cartItemOptional.get();
            Long currentQuantity = cartItem.getQuantity();
            Long newQuantity = currentQuantity + 1;

            BigDecimal amount = price.multiply(BigDecimal.valueOf(newQuantity));

            cartItem.setPrice(price);
            cartItem.setQuantity(newQuantity);
            cartItem.setAmount(amount);
            cartItemService.save(cartItem);

            Cart cart = cartItem.getCart();

            BigDecimal currentTotalAmount = cart.getTotalAmount();
            BigDecimal newTotalAmount = currentTotalAmount.add(price);
            cart.setTotalAmount(newTotalAmount);
            cartService.save(cart);

            List<CartItemResponseDTO> cartItemResponseDTOS =new ArrayList<>();

            List<CartItem> newCartItems = cartItemService.findAllByCart(cart);

            for (CartItem item : newCartItems) {
                CartItemResponseDTO cartItemResponseDTO = item.toCartItemResponseDTO();
                cartItemResponseDTOS.add(cartItemResponseDTO);
            }

            Map<String, Object> result = new HashMap<>();
            result.put("cartItems", cartItemResponseDTOS);
            result.put("totalAmount", newTotalAmount);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }
}
