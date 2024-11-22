package com.ankit.Yummy_Project.mapper;

import com.ankit.Yummy_Project.dto.CustomerRequest;
import com.ankit.Yummy_Project.dto.CustomerResponse;
import com.ankit.Yummy_Project.entity.Customer;
import org.springframework.stereotype.Service;


@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .address(request.address())
                .city(request.city())
                .pincode(request.pincode())
                .build();
    }

    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getAddress(), customer.getCity(), customer.getPincode());
    }

}
