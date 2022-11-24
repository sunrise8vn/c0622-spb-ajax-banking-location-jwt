package com.cg.model.dto;

import com.cg.model.ProductAvatar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String title;
    private BigDecimal price;
    private String unit;
    private String description;

    private ProductAvatarDTO avatar;

    public ProductDTO(Long id, String title, BigDecimal price, String unit, String description, ProductAvatar avatar) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.unit = unit;
        this.description = description;
        this.avatar = avatar.toProductAvatarDTO();
    }
}
