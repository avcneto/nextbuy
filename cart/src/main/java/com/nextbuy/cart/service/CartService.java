package com.nextbuy.cart.service;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.domain.Item;
import com.nextbuy.cart.domain.Status;
import com.nextbuy.cart.dto.AddItemDTO;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.dto.ItemDTO;
import com.nextbuy.cart.dto.ItemPaginationDTO;
import com.nextbuy.cart.exception.BadRequestException;
import com.nextbuy.cart.exception.NotFoundException;
import com.nextbuy.cart.gateway.ItemGateway;
import com.nextbuy.cart.gateway.UserGateway;
import com.nextbuy.cart.repository.CartRepository;
import com.nextbuy.cart.util.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@Service
public class CartService {
    private static final String INVALID_USER = "invalid user";
    private static final String INVALID_ITEM = "invalid item";
    private static final String THERE_IS_ALREADY_PENDING_CART = "There is already a pending cart";
    private static final String THERE_IS_NO_PENDING_CART = "There is no pending cart to add item";
    private static final String CART_NOT_FOUND = "cart not found";
    private static final String ITEM_NOT_FOUND = "Item not found";

    CartRepository cartRepository;
    UserGateway userGateway;
    ItemGateway itemGateway;

    public CartService(CartRepository cartRepository, UserGateway userGateway, ItemGateway itemGateway) {
        this.cartRepository = cartRepository;
        this.userGateway = userGateway;
        this.itemGateway = itemGateway;
    }

    public Pagination<Cart> getAllItems(Integer limit, Integer offset) {
        var pageRequest = PageRequest.of(offset, limit);
        var cartPagination = cartRepository.findAll(pageRequest);
        return new Pagination<>(cartPagination);
    }

    public Cart getCartById(long id) {
        var cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new NotFoundException(CART_NOT_FOUND));
    }

    public Cart createCart(CartDTO cartDTO) {
        BigDecimal total = BigDecimal.ZERO;
        validateUser(cartDTO);
        var items = itemGateway.getItems(cartDTO.itemsIds()
                .stream()
                .map(Item::getId)
                .toList()
        );

        validateItem(cartDTO, items);

        var existingCart = isExistingCart(cartDTO.userId());

        if (existingCart) {
            throw new BadRequestException(THERE_IS_ALREADY_PENDING_CART);
        }

        for (Item item : cartDTO.itemsIds()) {
            var quantity = item.getQuantity();
            var price = items.results().stream().filter(it -> it.id().equals(item.getId())).findFirst().orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND)).price();

            total = total.add(price.multiply(new BigDecimal(quantity)));
        }


        var newCart = new Cart(cartDTO);
        newCart.setTotal(total);

        return cartRepository.save(newCart);
    }

    @Transactional
    public Cart addItemToCart(AddItemDTO addItemDTO) {
        if (!isExistingCart(addItemDTO.userId())) {
            throw new BadRequestException(THERE_IS_NO_PENDING_CART);
        }

        var cart = cartRepository.findByUserIdAndStatus(addItemDTO.userId(), Status.PENDING);
        var price = itemGateway.getItems(List.of(addItemDTO.id()))
                .results()
                .stream()
                .findFirst()
                .map(ItemDTO::price)
                .orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));

        cart.setItemsIds(List.of(new Item(addItemDTO.id(), addItemDTO.quantity())));
        cart.setTotal(cart.getTotal().add(price.multiply(new BigDecimal(addItemDTO.quantity()))));

        return cartRepository.save(cart);
    }

    public void removeItemFromCart(Long cartId, String itemId) {
        var cart = cartRepository.findById(cartId);
        var itemPrice = itemGateway.getItems(List.of(itemId))
                .results().stream()
                .map(ItemDTO::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        cart.ifPresent(it -> {
            var itemResult = it.getItemsIds().stream().filter(item -> item.getId().equals(itemId)).findFirst().orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
            it.getItemsIds().removeIf(item -> item.getId().equals(itemId));

            it.setTotal(it.getTotal().subtract(itemPrice.multiply(new BigDecimal(itemResult.getQuantity()))));
            cartRepository.save(it);
        });

    }

    private void validateUser(CartDTO cartDTO) {
        var hasUser = userGateway.hasUser(cartDTO.userId());

        if (hasUser) {
            throw new BadRequestException(INVALID_USER);
        }
    }

    private void validateItem(CartDTO cartDTO, ItemPaginationDTO items) {
        List<String> itemIdsFromCartDTO = cartDTO.itemsIds().stream().map(Item::getId).toList();
        List<String> itemIdsFromResults = items.results().stream().map(ItemDTO::id).toList();

        boolean hasAllItems = new HashSet<>(itemIdsFromResults).containsAll(itemIdsFromCartDTO);

        if (!hasAllItems) {
            throw new BadRequestException(INVALID_ITEM);
        }
    }

    private boolean isExistingCart(Long userId) {
        return cartRepository.existsByUserIdAndStatus(userId, Status.PENDING);
    }

    public Cart checkout(Long id) {
        var cart = cartRepository.findById(id).orElseThrow(() -> new BadRequestException(CART_NOT_FOUND));
        cart.setStatus(Status.FINISHED);

        return cartRepository.save(cart);
    }
}