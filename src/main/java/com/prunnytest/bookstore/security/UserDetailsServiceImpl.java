package com.prunnytest.bookstore.security;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepo customerRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return customerRepo.findByEmail(email).orElseThrow(
                    () -> new NotFoundException("This Email isn't recognised", 404)
            );
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}