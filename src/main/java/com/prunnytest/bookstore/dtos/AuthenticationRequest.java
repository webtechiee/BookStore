package com.prunnytest.bookstore.dtos;

import lombok.Builder;

@Builder
public record AuthenticationRequest( //info need for the user to login
    String email,
    String password)
{}

