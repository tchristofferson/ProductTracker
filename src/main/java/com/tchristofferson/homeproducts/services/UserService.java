package com.tchristofferson.homeproducts.services;

import com.tchristofferson.homeproducts.repos.UserRepository;
import com.tchristofferson.homeproducts.security.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public AppUser getUser(String email) {
        return userRepository.findByEmail(email);
    }

}
