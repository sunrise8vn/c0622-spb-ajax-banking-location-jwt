package com.cg.repository;


import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
                "p.id, " +
                "p.title, " +
                "p.price, " +
                "p.unit, " +
                "p.description, " +
                "p.productAvatar " +
            ") " +
            "FROM Product AS p ")
    List<ProductDTO> getAllProductDTO();
}
