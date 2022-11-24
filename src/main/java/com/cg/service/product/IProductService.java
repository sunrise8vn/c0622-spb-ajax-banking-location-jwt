package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {

    List<ProductDTO> getAllProductDTO();

    Product create(ProductCreateDTO productCreateDTO);
}
