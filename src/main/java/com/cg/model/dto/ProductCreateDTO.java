package com.cg.model.dto;

import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreateDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;

    private MultipartFile file;


    public Product toProduct(ProductAvatar productAvatar) {
        return new Product()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setProductAvatar(productAvatar);
    }
}
