package com.prunnytest.bookstore.dtos;

import lombok.Builder;

@Builder
public record CustomerRequest( //minimum info required to register an acct
        String name,
        String username,
        String password,
        String email,
        String role
){}
