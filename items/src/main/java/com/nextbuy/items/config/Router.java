package com.nextbuy.items.config;

import org.springframework.http.HttpMethod;

public record Router(
        String path,
        HttpMethod method
) {
}
