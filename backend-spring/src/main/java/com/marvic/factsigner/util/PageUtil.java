package com.marvic.factsigner.util;

import com.marvic.factsigner.payload.PageResponse;
import com.marvic.factsigner.payload.sistema.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PageUtil {

    private static Sort.Direction getSortDirection(String direction) {
        return "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

    }

    /**
     * ?page=1&size=5&sort=published,desc&sort=title,as
     */
    public static Pageable pagingAndSort(int page, int size, String[] sort) {
        List<Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }
    
    public static <T, R> PageResponse<R> mapPage(Page<T> page, Function<T, R> mapToDTO) {
        PageResponse<R> response = new PageResponse<>();
        List<R> content = page.getContent().stream().map(mapToDTO).collect(Collectors.toList());
        response.setContent(content);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }
    
}
