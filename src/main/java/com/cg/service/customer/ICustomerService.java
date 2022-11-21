package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.LocationRegion;
import com.cg.model.Transfer;
import com.cg.model.dto.CustomerAvatarCreateDTO;
import com.cg.model.dto.CustomerDTO;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer> {

    List<CustomerDTO> getAllCustomerDTO();

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndIdIsNot(String email, Long id);

    List<Customer> findAllByFullNameLikeOrEmailLike(String fullName, String email);

    List<Customer> findAllByDeletedIsFalse();

    List<Customer> findAllByDeletedIsFalseAndIdNot(Long id);

    Customer createWithAvatar(CustomerAvatarCreateDTO customerAvatarCreateDTO, LocationRegion locationRegion);

    void deposit(Deposit deposit, Customer customer);

    void transfer(Transfer transfer);
}
