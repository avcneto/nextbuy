package com.nextbuy.items.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParams {
    private final static Integer DEFAULT_LIMIT = 10;
    private final static Integer DEFAULT_OFFSET = 0;
    private final static boolean ALL = false;

    private List<String> ids;
    private String name;
    private Integer limit = DEFAULT_LIMIT;
    private Integer offset = DEFAULT_OFFSET;

    public boolean hasParams(){
        return Stream.of(ids, name)
                .anyMatch(Objects::nonNull);
    }
}
