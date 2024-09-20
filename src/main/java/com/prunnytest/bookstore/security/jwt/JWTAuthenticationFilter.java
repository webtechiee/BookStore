package com.prunnytest.bookstore.security.jwt;


import com.prunnytest.bookstore.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
private final JWTService jwtService;
private final UserDetailsServiceImpl userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //get the authorization header from the http request

        if (authHeader == null ||!authHeader.startsWith("Bearer ")){ //checking the token format. "bearer <token>" is the format
            filterChain.doFilter(request,response);
            return;
        }
        String jwt = authHeader.substring(7); //extracts the token by removing the "bearer "
        String email = jwtService.extractUsername(jwt); //extracts username
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){ //if email is verified and the user is not yet authenticated, load the user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtService.isValid(jwt, userDetails)){       //if the token is valid, an authenticated user object is created and the authorities is included
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);    //the authenticated user is saved in the securitycontextholder
            }
        }
        filterChain.doFilter(request, response);    //pass the request along

    }
}

