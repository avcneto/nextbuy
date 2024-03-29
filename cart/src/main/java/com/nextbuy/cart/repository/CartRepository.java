package com.nextbuy.cart.repository;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


  boolean existsByUserIdAndStatus(Long userId, Status status);

  Cart findByUserIdAndStatus(Long id, Status status);

  void deleteByUserIdAndId(Long userId, Long id);
}
