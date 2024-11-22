package com.ankit.Yummy_Project.controller;

import com.ankit.Yummy_Project.dto.CustomerRequest;
import com.ankit.Yummy_Project.dto.CustomerResponse;
import com.ankit.Yummy_Project.dto.CustomerUpdateRequest;
import com.ankit.Yummy_Project.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{email}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("email") String email) {
        return ResponseEntity.ok(customerService.retrieveCustomer(email));
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String email) {
        return ResponseEntity.ok(customerService.deleteCustomer(email));
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateCustomer(@PathVariable String email, @RequestBody CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomerDetails(email, request));
    }
}
