package com.nextbuy.cart.dto;

import java.util.List;

public record ItemPaginationDTO(
        List<ItemDTO> results,
        Integer limit,
        Integer offset,
        Integer total
) {
}
