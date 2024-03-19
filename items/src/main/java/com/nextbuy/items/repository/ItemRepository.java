package com.nextbuy.items.repository;

import com.nextbuy.items.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
    Page<Item> findByIdOrName(Long id, String name, Pageable pageable);
}
