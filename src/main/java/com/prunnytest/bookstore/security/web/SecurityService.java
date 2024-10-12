package com.prunnytest.bookstore.security.web;

import com.prunnytest.bookstore.dtos.AuthenticationRequest;
import com.prunnytest.bookstore.dtos.AuthenticationResponse;
import com.prunnytest.bookstore.dtos.CustomerRequest;
import com.prunnytest.bookstore.dtos.CustomerResponse;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Customer;
import com.prunnytest.bookstore.model.Role;
import com.prunnytest.bookstore.repository.CustomerRepo;
import com.prunnytest.bookstore.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SecurityService {
    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public CustomerResponse loginAccount(AuthenticationRequest authenticationRequest) throws NotFoundException {
        Customer customer = customerRepo.findByEmail(authenticationRequest.email())
                        .orElseThrow(
                                () -> new NotFoundException("User not found", 404)
                        );

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.email(), authenticationRequest.password()
                )
        );
        String token = jwtService.generateToken(customer);
        return CustomerResponse.builder()
                .token(token)
                .email(customer.getEmail())
                .name(customer.getName())
                .role(customer.getRole())
                .build();
    }



    public AuthenticationResponse registerAccount(CustomerRequest customerRequest) {
        if (customerRepo.findByEmail(customerRequest.email()).isPresent()) {
            return new AuthenticationResponse(customerRequest.email(), "already exists");
        }
        Customer customer = Customer
                .builder()
                .name(customerRequest.name())
                .email(customerRequest.email())
                .password(passwordEncoder.encode(customerRequest.password()))
                .role(Role.valueOf(customerRequest.role()))
                .build();
        customerRepo.save(customer);

        String token = jwtService.generateToken(customer);
        return AuthenticationResponse
                .builder()
                .username(customer.getUsername())
                .token(token)
                .build();

    }

}

