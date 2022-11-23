package com.cg.service.customer;


import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.CustomerAvatarCreateDTO;
import com.cg.model.dto.CustomerDTO;
import com.cg.repository.*;
import com.cg.service.upload.UploadService;
import com.cg.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private UploadUtils uploadUtils;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerAvatarRepository customerAvatarRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private LocationRegionRepository locationRegionRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<CustomerDTO> getAllCustomerDTO() {
        return customerRepository.getAllCustomerDTO();
    }

    @Override
    public List<Customer> findAllByDeletedIsFalse() {
        return customerRepository.findAllByDeletedIsFalse();
    }

    @Override
    public List<Customer> findAllByDeletedIsFalseAndIdNot(Long id) {
        return customerRepository.findAllByDeletedIsFalseAndIdNot(id);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Optional<Customer> findByEmailAndIdIsNot(String email, Long id) {
        return customerRepository.findByEmailAndIdIsNot(email, id);
    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLike(String fullName, String email) {
        return customerRepository.findAllByFullNameLikeOrEmailLike(fullName, email);
    }

    @Override
    public Customer getById(Long id) {
        return null;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public void deposit(Deposit deposit, Customer customer) {
        deposit.setId(0L);
        deposit.setCustomer(customer);
        depositRepository.save(deposit);

        BigDecimal newBalance = deposit.getTransactionAmount();
        customerRepository.incrementBalance(customer.getId(), newBalance);
    }

    @Override
    public void transfer(Transfer transfer) {

        Long senderId = transfer.getSender().getId();
        Long recipientId = transfer.getRecipient().getId();

        BigDecimal transferAmount = transfer.getTransferAmount();
        BigDecimal transactionAmount = transfer.getTransactionAmount();

        customerRepository.reduceBalance(senderId, transactionAmount);

        customerRepository.incrementBalance(recipientId, transferAmount);

        transferRepository.save(transfer);
    }

    @Override
    public Customer save(Customer customer) {
        locationRegionRepository.save(customer.getLocationRegion());
        return customerRepository.save(customer);
    }

    @Override
    public Customer createWithAvatar(CustomerAvatarCreateDTO customerAvatarCreateDTO, LocationRegion locationRegion) {
        String fileType = customerAvatarCreateDTO.getFile().getContentType();

        assert fileType != null;

        fileType = fileType.substring(0, 5);

        customerAvatarCreateDTO.setFileType(fileType);

        CustomerAvatar customerAvatar = new CustomerAvatar();
        customerAvatar = customerAvatarRepository.save(customerAvatar);

        CustomerAvatar newCustomerAvatar = uploadAndSaveCustomerAvatar(customerAvatarCreateDTO, customerAvatar);

        locationRegion = locationRegionRepository.save(locationRegion);

        Customer customer = customerRepository.save(customerAvatarCreateDTO.toCustomer(locationRegion, newCustomerAvatar));

        return customer;
    }

    private CustomerAvatar uploadAndSaveCustomerAvatar(CustomerAvatarCreateDTO customerAvatarCreateDTO, CustomerAvatar customerAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(customerAvatarCreateDTO.getFile(), uploadUtils.buildImageUploadParams(customerAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            customerAvatar.setFileName(customerAvatar.getId() + "." + fileFormat);
            customerAvatar.setFileUrl(fileUrl);
            customerAvatar.setFileFolder(UploadUtils.CUSTOMER_IMAGE_UPLOAD_FOLDER);
            customerAvatar.setCloudId(customerAvatar.getFileFolder() + "/" + customerAvatar.getId());
            customerAvatar = customerAvatarRepository.save(customerAvatar);
            return customerAvatar;

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại");
        }
    }

    @Override
    public void remove(Long id) {

    }
}
