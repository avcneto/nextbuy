package com.nextbuy.cart.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class Pagination<T> {
    private List<T> results;
    private Integer limit;
    private Long offset;
    private Long total;

    public Pagination(Page<T> page) {
        this.results = page.getContent();
        this.limit = page.getSize();
        this.offset = page.getPageable().getOffset();
        this.total = page.getTotalElements();
    }
}