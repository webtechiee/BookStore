package com.prunnytest.bookstore.dtos;

import com.prunnytest.bookstore.model.Role;
import lombok.Builder;

@Builder
public record CustomerResponse( //response returned after user is logged in
        String token,
        String email,
        String name,
        String password,
        Role role

){}
