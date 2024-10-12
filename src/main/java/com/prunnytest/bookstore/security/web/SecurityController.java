package com.prunnytest.bookstore.security.web;


import com.prunnytest.bookstore.dtos.AuthenticationRequest;
import com.prunnytest.bookstore.dtos.AuthenticationResponse;
import com.prunnytest.bookstore.dtos.CustomerRequest;
import com.prunnytest.bookstore.dtos.CustomerResponse;
import com.prunnytest.bookstore.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("security")
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("login")
    public ResponseEntity<CustomerResponse> login(@RequestBody AuthenticationRequest authenticationRequest) throws NotFoundException {
        return ResponseEntity.ok(securityService.loginAccount(authenticationRequest));
    }

    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(securityService.registerAccount(customerRequest));
    }


}

