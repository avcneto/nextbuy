package com.nextbuy.cart.domain;

import com.nextbuy.cart.dto.CartDTO;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long userId;
  @ElementCollection
  private List<Item> itemsIds = emptyList();
  @Enumerated(EnumType.STRING)
  private Status status = Status.PENDING;
  private BigDecimal total;

  public Cart(CartDTO cartDTO) {
    this.userId = cartDTO.userId();
    this.itemsIds = cartDTO.itemsIds().isEmpty() ? emptyList() : cartDTO.itemsIds();
  }

  public void setItemsIds(List<Item> itemsIds) {
    this.itemsIds.addAll(itemsIds);
  }
}
