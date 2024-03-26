package com.nextbuy.cart.service;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.domain.Item;
import com.nextbuy.cart.domain.Status;
import com.nextbuy.cart.dto.AddItemDTO;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.dto.ItemDTO;
import com.nextbuy.cart.exception.BadRequestException;
import com.nextbuy.cart.exception.NotFoundException;
import com.nextbuy.cart.gateway.ItemGateway;
import com.nextbuy.cart.gateway.UserGateway;
import com.nextbuy.cart.repository.CartRepository;
import com.nextbuy.cart.util.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.nextbuy.cart.util.Constant.CART_NOT_FOUND;

@Service
public class CartService {
  private static final String INVALID_USER = "invalid user";
  private static final String INVALID_ITEM = "invalid item";
  private static final String THERE_IS_ALREADY_PENDING_CART = "There is already a pending cart";
  private static final String THERE_IS_NO_PENDING_CART = "There is no pending cart to add item";

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
    validateUser(cartDTO);
    validateItem(cartDTO);

    var existingCart = isExistingCart(cartDTO.userId());

    if (existingCart) {
      throw new BadRequestException(THERE_IS_ALREADY_PENDING_CART);
    }

    return cartRepository.save(new Cart(cartDTO));
  }

  @Transactional
  public Cart addItemToCart(AddItemDTO addItemDTO) {
    if (!isExistingCart(addItemDTO.userId())) {
      throw new BadRequestException(THERE_IS_NO_PENDING_CART);
    }

    var cart = cartRepository.findByUserIdAndStatus(addItemDTO.userId(), Status.PENDING);
    cart.setItemsIds(List.of(new Item(addItemDTO.id(), addItemDTO.quantity())));

    return cartRepository.save(cart);
  }

  public void removeItemFromCart(Long cartId, String itemId) {
    var cart = cartRepository.findById(cartId);

    cart.ifPresent(it -> {
      it.getItemsIds().removeIf(item -> item.getId().equals(itemId));
      cartRepository.save(it);
    });

  }

  private void validateUser(CartDTO cartDTO) {
    var hasUser = userGateway.hasUser(cartDTO.userId());

    if (hasUser) {
      throw new BadRequestException(INVALID_USER);
    }
  }

  private void validateItem(CartDTO cartDTO) {
    var items = itemGateway.getItems(cartDTO.itemsIds()
            .stream()
            .map(Item::getId)
            .toList()
    );

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

}
