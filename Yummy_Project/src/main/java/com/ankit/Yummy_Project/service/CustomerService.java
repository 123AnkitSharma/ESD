package com.ankit.Yummy_Project.service;

import com.ankit.Yummy_Project.dto.CustomerRequest;
import com.ankit.Yummy_Project.dto.CustomerResponse;
import com.ankit.Yummy_Project.dto.CustomerUpdateRequest;
import com.ankit.Yummy_Project.dto.LoginRequest;
import com.ankit.Yummy_Project.entity.Customer;
import com.ankit.Yummy_Project.exception.CustomerNotFoundException;
import com.ankit.Yummy_Project.helper.EncryptionService;
import com.ankit.Yummy_Project.helper.JWTHelper;
import com.ankit.Yummy_Project.mapper.CustomerMapper;
import com.ankit.Yummy_Project.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final EncryptionService encryptionService;
    private final JWTHelper jwtHelper;


    public String createCustomer(CustomerRequest request) {
        Customer customer = customerMapper.toCustomer(request);
        customer.setPassword(encryptionService.encode(customer.getPassword()));
        customerRepo.save(customer);
        return "Customer Created Successfully";
    }

    public Customer getCustomer(String email) {
        return customerRepo.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update Customer:: No customer found with the provided ID:: %s", email)
                ));
    }

    public CustomerResponse retrieveCustomer(String email) {
        Customer customer = getCustomer(email);
        return customerMapper.toCustomerResponse(customer);
    }

    public String login(LoginRequest request) {
        Customer customer = getCustomer(request.email());
        if(!encryptionService.validates(request.password(), customer.getPassword())) {
            return "Wrong Password or Email";
        }

        return jwtHelper.generateToken(request.email());
    }

    public String deleteCustomer(String email) {
        Customer customer = getCustomer(email);
        customerRepo.delete(customer);
        return "Customer Deleted Successfully";
    }

    public String updateCustomerDetails(String email, CustomerUpdateRequest request) {
        Customer customer = getCustomer(email);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setAddress(request.getAddress());
        customer.setCity(request.getCity());
        customer.setPincode(request.getPincode());
        customerRepo.save(customer);
        return "Customer Details Updated Successfully";
    }


}
