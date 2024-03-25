package com.nextbuy.items.domain;

import com.nextbuy.items.dto.ItemDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Item {
    @Id
    private String id = UUID.randomUUID().toString();
    private String name;
    private BigDecimal price;
    private Integer quantity = 0;

    public Item(ItemDTO itemDTO){
        this.name = itemDTO.name();
        this.price = itemDTO.price();
        this.quantity = itemDTO.quantity();
    }
}