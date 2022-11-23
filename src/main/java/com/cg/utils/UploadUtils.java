package com.cg.utils;

import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.CustomerAvatar;
import com.cg.model.ProductAvatar;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UploadUtils {
    public static final String PRODUCT_IMAGE_UPLOAD_FOLDER = "product_images";
    public static final String CUSTOMER_IMAGE_UPLOAD_FOLDER = "customer_images";
    public static final String VIDEO_UPLOAD_FOLDER = "product_videos";

    public Map buildImageUploadParams(CustomerAvatar customerAvatar) {
        if (customerAvatar == null || customerAvatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của khách hàng chưa được lưu");

        String publicId = String.format("%s/%s", CUSTOMER_IMAGE_UPLOAD_FOLDER, customerAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageUploadParams(ProductAvatar productAvatar) {
        if (productAvatar == null || productAvatar.getId() == null)
            throw new DataInputException("Không thể upload hình ảnh của sản phẩm chưa được lưu");

        String publicId = String.format("%s/%s", PRODUCT_IMAGE_UPLOAD_FOLDER, productAvatar.getId());

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }

    public Map buildImageDestroyParams(Customer customer, String publicId) {
        if (customer == null || customer.getId() == null)
            throw new DataInputException("Không thể destroy hình ảnh của sản phẩm không xác định");

        return ObjectUtils.asMap(
                "public_id", publicId,
                "overwrite", true,
                "resource_type", "image"
        );
    }
}
