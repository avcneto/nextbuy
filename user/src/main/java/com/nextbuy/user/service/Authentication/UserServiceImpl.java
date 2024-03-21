package com.nextbuy.user.service.Authentication;

import com.nextbuy.user.respository.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return repository::findByEmail;
    }
}
