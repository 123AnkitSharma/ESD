package com.ankit.demo.service;

import com.ankit.demo.dto.CustomerRequest;
import com.ankit.demo.dto.CustomerResponse;
import com.ankit.demo.entity.Customer;
import com.ankit.demo.mapper.CustomerMapper;
import com.ankit.demo.repo.CustomerRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        repo.save(customer);
        return "Created";
    }

}
