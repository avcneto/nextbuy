package com.nextbuy.user.respository.user;

import com.nextbuy.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByCpfOrId(String cpf, Long id);

  Optional<User> findByCpfOrEmail(String cpf, String email);

  UserDetails findByEmail(String email);
}
