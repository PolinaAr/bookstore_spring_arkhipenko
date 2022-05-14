package com.belhard.bookstore.controller.command;

import com.belhard.bookstore.controller.command.impl.BookCommand;
import com.belhard.bookstore.controller.command.impl.BooksCommand;
import com.belhard.bookstore.controller.command.impl.ErrorCommand;
import com.belhard.bookstore.controller.command.impl.UserCommand;
import com.belhard.bookstore.controller.command.impl.UsersCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static class Holder {
        private static final CommandFactory instance = new CommandFactory();
    }

    private CommandFactory() {
    }

    public static CommandFactory getInstance() {
        return Holder.instance;
    }

    private static Map<String, Command> map = new HashMap<>();

    static {
        map.put("book", new BookCommand());
        map.put("books", new BooksCommand());
        map.put("user", new UserCommand());
        map.put("users", new UsersCommand());
        map.put("error", new ErrorCommand());
    }


    public Command getCommand(String action) {
        Command command = map.get(action);
        if (command == null){
            return map.get("error");
        }
        return command;
    }
}
