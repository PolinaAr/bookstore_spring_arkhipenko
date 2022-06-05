package com.belhard.bookstore.util;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ParamReader {

    public int readPage(Map<String, Object> params) {
        int page = 0;
        if (params.containsKey("page")) {
            page = Integer.parseInt(params.get("page").toString());
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

}
