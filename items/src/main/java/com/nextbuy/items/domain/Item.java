package com.nextbuy.items.domain;

import com.nextbuy.items.dto.ItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "tb_item")
@SequenceGenerator(name = "item_sequence", initialValue = 1)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "item_sequence")
    private Long id;
    private String name;
    private BigDecimal price;

    public Item(ItemDTO itemDTO){
        this.name = itemDTO.name();
        this.price = itemDTO.price();
    }
}