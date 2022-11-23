package com.cg.service.product;


import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.CustomerAvatar;
import com.cg.model.Product;
import com.cg.model.ProductAvatar;
import com.cg.model.dto.CustomerAvatarCreateDTO;
import com.cg.model.dto.ProductCreateDTO;
import com.cg.model.enums.EFileType;
import com.cg.repository.ProductAvatarRepository;
import com.cg.repository.ProductRepository;
import com.cg.service.upload.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAvatarRepository productAvatarRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private UploadUtils uploadUtils;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product create(ProductCreateDTO productCreateDTO) {

        ProductAvatar productAvatar = new ProductAvatar();
        productAvatar = productAvatarRepository.save(productAvatar);

        ProductAvatar newProductAvatar = uploadAndSaveProductAvatar(productCreateDTO, productAvatar);

        Product product = productRepository.save(productCreateDTO.toProduct(newProductAvatar));

        return product;
    }

    private ProductAvatar uploadAndSaveProductAvatar(ProductCreateDTO productCreateDTO, ProductAvatar productAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(productCreateDTO.getFile(), uploadUtils.buildImageUploadParams(productAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productAvatar.setFileName(productAvatar.getId() + "." + fileFormat);
            productAvatar.setFileType(EFileType.IMAGE.getValue());
            productAvatar.setFileUrl(fileUrl);
            productAvatar.setFileFolder(UploadUtils.PRODUCT_IMAGE_UPLOAD_FOLDER);
            productAvatar.setCloudId(productAvatar.getFileFolder() + "/" + productAvatar.getId());
            productAvatar = productAvatarRepository.save(productAvatar);
            return productAvatar;

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
