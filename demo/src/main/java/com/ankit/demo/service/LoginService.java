package com.ankit.demo.service;


import com.ankit.demo.dto.LoginRequest;
import com.ankit.demo.entity.Customer;
import com.ankit.demo.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final CustomerRepo customerRepo;

    public String login(LoginRequest request) {
        Customer customer = customerRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));


        if (request.getPassword().equals(customer.getPassword())) {
            return "User logged in successfully.";
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }
}