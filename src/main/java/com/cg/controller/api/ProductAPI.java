package com.cg.controller.api;


import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.service.product.IProductService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {

    @Autowired
    private IProductService productService;


    @GetMapping
    public ResponseEntity<?> getAll() {

        List<Product> products = productService.findAll();

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product item : products) {
            ProductDTO productDTO = item.toProductDTO();
            productDTOS.add(productDTO);
        }

        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(ProductCreateDTO productCreateDTO) {

        productCreateDTO.setId(null);
        productCreateDTO.setQuantity(0L);

        Product newProduct = productService.create(productCreateDTO);

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
}
