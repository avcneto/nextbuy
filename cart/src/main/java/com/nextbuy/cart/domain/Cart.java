package com.nextbuy.cart.domain;

import com.nextbuy.cart.dto.CartDTO;
import com.nextbuy.cart.dto.ItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID DO CARRINHO
    private List<ItemDTO> = new ArrayList; // Lista de Itens
    private long itemId; // ID DO ITEM
    private long userId; // ID DO USUÁRIO
    private Integer itemQuantity; // QNT DO ITEM
    private BigDecimal total; // TOTAL DO CARRINHO
    private String paymentMethod; // MÉTODO PGTO

    public Cart(CartDTO cartDTO) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.total = total;
        this.paymentMethod = paymentMethod;
    }
}
