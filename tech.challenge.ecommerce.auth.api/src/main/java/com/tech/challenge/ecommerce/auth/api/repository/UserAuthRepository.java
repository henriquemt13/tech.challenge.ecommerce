package com.tech.challenge.ecommerce.auth.api.repository;

import com.tech.challenge.ecommerce.auth.api.domain.model.UserAuth;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserAuthRepository extends CrudRepository<UserAuth, Long> {

    Optional<UserAuth> findByUsername(String username);
}
