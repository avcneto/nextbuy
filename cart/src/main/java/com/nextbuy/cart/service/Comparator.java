package com.nextbuy.cart.service;

import com.nextbuy.cart.domain.Item;

import java.util.List;

public record Comparator(
        List<Item> registeredItems,
        List<Item> newItems
) {
}
