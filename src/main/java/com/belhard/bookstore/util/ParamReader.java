package com.belhard.bookstore.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParamReader {

    public Pageable getPageable(Map<String, Object> params) {
        int page = readPage(params);
        String direction = readDirection(params);
        String sortColumn = readSortColumn(params);
        int items = readQuantityOfItems(params);
        return PageRequest.of(page, items, Sort.Direction.valueOf(direction), sortColumn);
    }

    private int readPage(Map<String, Object> params) {
        int page = 0;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page").toString()) - 1;
        }
        return page;
    }

    private String readDirection(Map<String, Object> params) {
        String direction = "ASC";
        if (params.containsKey("direction")) {
            direction = params.get("direction").toString();
        }
        return direction;
    }

    private String readSortColumn(Map<String, Object> params) {
        String sortColumn = "id";
        if (params.containsKey("sortColumn")) {
            sortColumn = params.get("sortColumn").toString();
        }
        return sortColumn;
    }

    private int readQuantityOfItems(Map<String, Object> params) {
        int items = 10;
        if(params.containsKey("items")) {
            items = Integer.parseInt(params.get("items").toString());
        }
        return items;
    }

}
