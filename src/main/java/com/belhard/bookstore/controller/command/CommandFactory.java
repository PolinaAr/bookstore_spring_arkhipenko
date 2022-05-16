package com.belhard.bookstore.controller.command;

import com.belhard.bookstore.ContextController;
import com.belhard.bookstore.controller.command.impl.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static class Holder {
        private static final CommandFactory instance = new CommandFactory();
    }

    private CommandFactory() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ContextController.class);
        System.out.println("-----------------create context------------------");
        UserCommand userCommand = context.getBean("userCommand", UserCommand.class);
        BookCommand bookCommand = context.getBean("bookCommand", BookCommand.class);
    }

    public static CommandFactory getInstance() {
        return Holder.instance;
    }

    private static final Map<String, Command> map = new HashMap<>();

    static {map.put("book", new BookCommand());
        map.put("books", new BooksCommand());
        map.put("user", new UserCommand());
        map.put("users", new UsersCommand());
        map.put("error", new ErrorCommand());
    }


    public Command getCommand(String action) {
        Command command = map.get(action);
        if (command == null) {
            return map.get("error");
        }
        return command;
    }
}
