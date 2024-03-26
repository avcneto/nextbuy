package com.nextbuy.payments.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {

    private Long id;

    @JsonAlias("user_id")
    private Long userId;

    @JsonAlias("items_ids")
    private List<Item> itemsIds;

    private String status;

    private BigDecimal total;


}
