package com.cg.model.dto;


import com.cg.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCreateDTO {

    private Long id;

    @NotEmpty(message = "Họ tên là bắt buộc")
    private String fullName;

    @Pattern(regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$", message = "Vui lòng nhập đúng định dạng email")
    @NotEmpty(message = "Email là bắt buộc")
    private String email;

    private String phone;

    private BigDecimal balance;

    @Valid
    private LocationRegionDTO locationRegion;


    public Customer toCustomer() {
        return new Customer()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegion());
    }
}
