package com.tchristofferson.homeproducts.repos;

import com.tchristofferson.homeproducts.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);

}
