package com.prunnytest.bookstore.dtos;

import lombok.Builder;

@Builder
public record AuthenticationResponse (
    String username,
    String token
){}