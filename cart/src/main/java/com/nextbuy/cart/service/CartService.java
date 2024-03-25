package com.nextbuy.cart.service;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.domain.Items;
import com.nextbuy.cart.domain.Status;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.dto.ItemDTO;
import com.nextbuy.cart.exception.BadRequestException;
import com.nextbuy.cart.exception.NotFoundException;
import com.nextbuy.cart.gateway.ItemGateway;
import com.nextbuy.cart.gateway.UserGateway;
import com.nextbuy.cart.repository.CartRepository;
import com.nextbuy.cart.util.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nextbuy.cart.util.Constant.CART_NOT_FOUND;

@Service
public class CartService {
  private static final String INVALID_USER = "invalid user";
  private static final String INVALID_ITEM = "invalid item";
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

  public Cart createOrUpdateCart(CartDTO cartDTO) {
    validateUser(cartDTO);
    validateItem(cartDTO);

    return cartRepository.findByUserIdAndStatus(cartDTO.userId(), Status.PENDING)
            .map(cart -> {
              List<Items> newItemsList = new ArrayList<>();
              for (Items it : cart.getItemsIds()) {
                List<Items> matchingItemsDTO = cartDTO.itemsIds().stream()
                        .filter(itemDTO -> itemDTO.getId().equals(it.getId()))
                        .toList();
                for (Items itemDTO : matchingItemsDTO) {
                  Items updatedItem = new Items();
                  updatedItem.setId(it.getId());
                  updatedItem.setQuantity(it.getQuantity() + itemDTO.getQuantity());
                  newItemsList.add(updatedItem);
                }
                if (matchingItemsDTO.isEmpty()) {
                  Items newItem = new Items();
                  newItem.setId(it.getId());
                  newItem.setQuantity(it.getQuantity());
                  newItemsList.add(newItem);
                }
              }
              cart.setItemsIds(newItemsList);
              return cartRepository.save(cart);
            })
            .orElseGet(() -> cartRepository.save(new Cart(cartDTO)));
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
            .map(Items::getId)
            .toList()
    );

    List<String> itemIdsFromCartDTO = cartDTO.itemsIds().stream().map(Items::getId).toList();
    List<String> itemIdsFromResults = items.results().stream().map(ItemDTO::id).toList();

    boolean hasAllItems = new HashSet<>(itemIdsFromResults).containsAll(itemIdsFromCartDTO);

    if (!hasAllItems) {
        throw new BadRequestException(INVALID_ITEM);
    }
  }

  public void updateCart(CartDTO cartDTO) {
    // INSERIR / REMOVER ITEM DO CARRINHO
  }

  public void clearCart(CartDTO cartDTO) {
    // LIMPA CARRINHO
  }

}
