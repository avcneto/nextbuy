package com.nextbuy.items.service;

import com.nextbuy.items.domain.Item;
import com.nextbuy.items.domain.SearchParams;
import com.nextbuy.items.dto.ItemDTO;
import com.nextbuy.items.dto.UpdateItemDTO;
import com.nextbuy.items.exception.FailedDependencyException;
import com.nextbuy.items.exception.NotFoundException;
import com.nextbuy.items.exception.ResourceAlreadyExistsException;
import com.nextbuy.items.repository.ItemRepository;
import com.nextbuy.items.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Optional;

import static com.nextbuy.items.util.Constant.ITEM_ALREADY_REGISTERED;
import static com.nextbuy.items.util.Constant.ITEM_NOT_FOUND;
import static com.nextbuy.items.util.Validators.isNullOrEmptyOrBlank;

@Slf4j
@Service
public class ItemService {

    private static final String INACCESSIBLE_FIELDS = "item update has inaccessible fields";
    private static final String NON_MATCHING = "non-matching fields between ItemDTO and Item";
    private static final String FAILED_DEPENDENCY_DATABASE = "Error retrieving data from database";

    ItemRepository itemRepository;

    public Pagination<Item> getAllItems(Integer limit, Integer offset) {
        var pageRequest = PageRequest.of(offset, limit);
        var itemsPagination = itemRepository.findAll(pageRequest);
        return new Pagination<>(itemsPagination);
    }

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private Optional<Item> findByName(String name) {
        try {
            return itemRepository.findByName(name);
        } catch (Exception ex) {
            log.error(FAILED_DEPENDENCY_DATABASE, ex);
            throw new FailedDependencyException(FAILED_DEPENDENCY_DATABASE, ex);
        }
    }

    public Item createItem(ItemDTO itemDTO) {
        itemDTO.validPrice();
        itemRepository
                .findByName(itemDTO.name())
                .ifPresent(it -> {
                    throw new ResourceAlreadyExistsException(ITEM_ALREADY_REGISTERED);
                });

        return itemRepository.save(new Item(itemDTO));
    }

    public Pagination<Item> findItemByParam(SearchParams searchParams) {
        var pageRequest = PageRequest.of(searchParams.getOffset(), searchParams.getLimit());

        if (!searchParams.hasParams()) {
            return new Pagination<>(itemRepository.findAll(pageRequest));
        }

        var itemPagination = itemRepository.findByIdOrName(searchParams.getId(), searchParams.getName(), pageRequest);

        return new Pagination<>(itemPagination);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Item updateItemById(Long id, UpdateItemDTO updateItemDTO) {
        var item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException(ITEM_NOT_FOUND));
        updateItemByUpdateItemDTO(updateItemDTO, item);
        return itemRepository.save(item);
    }

    private void updateItemByUpdateItemDTO(final UpdateItemDTO updateItemDTO, Item item) {
        Field[] fields = UpdateItemDTO.class.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(updateItemDTO);
            } catch (IllegalAccessException ex) {
                throw new FailedDependencyException(INACCESSIBLE_FIELDS, ex);
            }

            if (!isNullOrEmptyOrBlank(value)) {
                Field correspondingField;

                try {
                    correspondingField = Item.class.getDeclaredField(field.getName());
                    correspondingField.setAccessible(true);

                    correspondingField.set(item, value);

                } catch (NoSuchFieldException ex) {
                    throw new FailedDependencyException(NON_MATCHING, ex);

                } catch (IllegalAccessException ex) {
                    throw new FailedDependencyException(INACCESSIBLE_FIELDS, ex);
                }
            }
        }
    }
}
