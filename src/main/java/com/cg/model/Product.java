package com.cg.model;

import com.cg.model.dto.ProductAvatarDTO;
import com.cg.model.dto.ProductDTO;
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
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "id")
    private ProductAvatar productAvatar;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    private Long quantity;

    private String unit;

    private String description;


    public ProductDTO toProductDTO() {
        return new ProductDTO()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setUnit(unit)
                .setDescription(description)
                .setAvatar(productAvatar.toProductAvatarDTO());
    }


}
