package com.cg.repository;

import com.cg.model.Customer;
import com.cg.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    @Query("SELECT NEW com.cg.model.dto.CustomerDTO (" +
                "c.id, " +
                "c.fullName, " +
                "c.email, " +
                "c.phone, " +
                "c.balance, " +
                "c.locationRegion" +
            ") FROM Customer AS c")
    List<CustomerDTO> getAllCustomerDTO();

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailAndIdIsNot(String email, Long id);


    List<Customer> findAllByFullNameLikeOrEmailLike(String fullName, String email);


    List<Customer> findAllByDeletedIsFalse();

    List<Customer> findAllByDeletedIsFalseAndIdNot(Long id);

    @Modifying
    @Query("UPDATE Customer AS c " +
            "SET c.balance = c.balance + :balance " +
            "WHERE c.id = :customerId")
    void incrementBalance(@Param("customerId") Long customerId, @Param("balance") BigDecimal balance);


    @Modifying
    @Query("UPDATE Customer AS c " +
            "SET c.balance = c.balance - :balance " +
            "WHERE c.id = :customerId")
    void reduceBalance(@Param("customerId") Long customerId, @Param("balance") BigDecimal balance);
}
