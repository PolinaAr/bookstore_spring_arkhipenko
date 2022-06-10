package com.belhard.bookstore.util;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReaderUtil {

    public int readPage(Map<String, Object> params) {
        int page = 0;
        if (params.containsKey("page")) {
            page = (Integer.parseInt(params.get("page").toString()) - 1)*readQuantityOfItems(params);
        }
        return page;
    }

    public String readDirection(Map<String, Object> params) {
        String direction = "ASC";
        if (params.containsKey("direction") && params.get("direction").equals("DESC")) {
            direction = "DESC";
        }
        return direction;
    }

    public String readSortColumn(Map<String, Object> params) {
        String sortColumn = "id";
        if (params.containsKey("sortColumn")) {
            sortColumn = params.get("sortColumn").toString();
        }
        return sortColumn;
    }

    public int readQuantityOfItems(Map<String, Object> params) {
        int items = 10;
        if (params.containsKey("items")) {
            items = Integer.parseInt(params.get("items").toString());
        }
        return items;
    }
}
