package com.nextbuy.items.repository;

import com.nextbuy.items.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> findByName(String name);
    Page<Item> findByIdOrName(String id, String name, Pageable pageable);
}
