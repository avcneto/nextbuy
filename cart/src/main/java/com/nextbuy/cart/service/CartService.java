package com.nextbuy.cart.service;

import com.nextbuy.cart.domain.Cart;
import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.exception.NotFoundException;
import com.nextbuy.cart.repository.CartRepository;
import com.nextbuy.cart.util.Pagination;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static com.nextbuy.cart.util.Constant.CART_ALREADY_REGISTERED;
import static com.nextbuy.cart.util.Constant.CART_NOT_FOUND;
import static java.lang.String.format;

@Service
public class CartService {
    CartRepository cartRepository;

    public Pagination<Cart> getAllItems(Integer limit, Integer offset) {
        var pageRequest = PageRequest.of(offset, limit);
        var cartPagination = cartRepository.findAll(pageRequest);
        return new Pagination<>(cartPagination);
    }

    public CartService(CartRepository cartRepository) { this.cartRepository = cartRepository; }

    public Cart getCartById(long id) {
        var cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new NotFoundException(CART_NOT_FOUND));
    }

    public Cart createCart(CartDTO cartDTO) {
        // CRIADO JUNTO AO USUÁRIO
//        cartDTO.validPrice();
        cartRepository
                .findById(cartDTO.get)
                .ifPresent(it -> {
                    throw new ResourceAlreadyExistsException(ITEM_ALREADY_REGISTERED);
                });

        return itemRepository.save(new Item(itemDTO));
    }

    public Cart updateCart(CartDTO cartDTO){
        // INSERIR / REMOVER ITEM DO CARRINHO
    }

    public Cart clearCart(CartDTO cartDTO){
        // LIMPA CARRINHO
    }

}
