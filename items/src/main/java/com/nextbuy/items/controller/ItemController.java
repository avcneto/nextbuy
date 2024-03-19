package com.nextbuy.items.controller;

import com.nextbuy.items.domain.Item;
import com.nextbuy.items.domain.SearchParams;
import com.nextbuy.items.dto.ItemDTO;
import com.nextbuy.items.dto.UpdateItemDTO;
import com.nextbuy.items.service.ItemService;
import com.nextbuy.items.util.Pagination;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.lang.String.format;

@RestController
@RequestMapping(path = "/item")
public record ItemController(
        ItemService itemService
) {

    private static final String ITEM_ID_PATH = "/address/%s";
    private static final String ID = "id";
    private static final String ALL = "all";
    private static final String TEN = "10";
    private static final String ZERO = "0";

    @GetMapping(path = ALL)
    public ResponseEntity<Pagination<Item>> getAllItems(
            @RequestParam(defaultValue = TEN) Integer limit,
            @RequestParam(defaultValue = ZERO) Integer offset) {
        var item = itemService.getAllItems(limit, offset);
        return ResponseEntity.ok(item);
    }
    @GetMapping()
    public ResponseEntity<Pagination<Item>> findItemByParam(@ModelAttribute SearchParams searchParams){
        var item = itemService.findItemByParam(searchParams);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody @Valid ItemDTO itemDTO) {
        var item = itemService.createItem(itemDTO);
        return ResponseEntity.created(URI.create(format(ITEM_ID_PATH, item.getId()))).body(item);
    }

    @DeleteMapping( params = ID)
    public ResponseEntity<Void> deleteItem(Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(params = ID)
    public ResponseEntity<Item> updateItemById(Long id , @RequestBody UpdateItemDTO updateItemDTO) {
        var item = itemService.updateItemById(id, updateItemDTO);
        return ResponseEntity.ok(item);
    }
}
