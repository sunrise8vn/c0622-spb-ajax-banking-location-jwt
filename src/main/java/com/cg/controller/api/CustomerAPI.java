package com.cg.controller.api;


import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.model.dto.CustomerAvatarCreateDTO;
import com.cg.model.dto.CustomerCreateDTO;
import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.LocationRegionDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {

        List<CustomerDTO> customers = customerService.getAllCustomerDTO();

        if (customers.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> create(@PathVariable Long customerId) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Customer ID not valid");
        }

        return new ResponseEntity<>(customerOptional.get().toCustomerDTO(), HttpStatus.OK);
    }

    @PostMapping("/create-with-avatar")
    public ResponseEntity<?> createWithAvatar(@Validated CustomerAvatarCreateDTO customerAvatarCreateDTO, BindingResult bindingResult) {

        if(bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Customer> customerOptional = customerService.findByEmail(customerAvatarCreateDTO.getEmail());

        if (customerOptional.isPresent()) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        customerAvatarCreateDTO.setId(null);
        LocationRegion locationRegion = customerAvatarCreateDTO.toLocationRegion();

        customerAvatarCreateDTO.setBalance(BigDecimal.ZERO);

        Customer newCustomer = customerService.createWithAvatar(customerAvatarCreateDTO, locationRegion);

        return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated @RequestBody CustomerCreateDTO customerCreateDTO, BindingResult bindingResult) {

        if(bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Optional<Customer> customerOptional = customerService.findByEmail(customerCreateDTO.getEmail());

        if (customerOptional.isPresent()) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        customerCreateDTO.setId(null);
        customerCreateDTO.setBalance(BigDecimal.ZERO);
        customerCreateDTO.getLocationRegion().setId(null);

        Customer customer = customerCreateDTO.toCustomer();

        Customer newCustomer = customerService.save(customer);

        return new ResponseEntity<>(newCustomer.toCustomerDTO(), HttpStatus.CREATED);
    }

    @PatchMapping("/{customerId}")
        public ResponseEntity<Customer> update(@PathVariable Long customerId, @RequestBody Customer customer) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Customer ID not valid");
        }

        Optional<Customer> emailOptional = customerService.findByEmailAndIdIsNot(customer.getEmail(), customerId);

        if (emailOptional.isPresent()) {
            throw new EmailExistsException("Email is exists");
        }

        customer.setId(customerId);
        customer.setBalance(customerOptional.get().getBalance());
        Customer updatedCustomer = customerService.save(customer);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}
