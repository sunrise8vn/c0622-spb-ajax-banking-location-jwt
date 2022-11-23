package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.service.IGeneralService;

public interface IProductService extends IGeneralService<Product> {

    Product create(ProductCreateDTO productCreateDTO);
}
