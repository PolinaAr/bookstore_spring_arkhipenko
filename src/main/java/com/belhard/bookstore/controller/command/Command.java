package com.belhard.bookstore.controller.command;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    String execute(HttpServletRequest req);

}
