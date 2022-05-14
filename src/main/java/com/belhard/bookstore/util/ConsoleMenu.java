package com.belhard.bookstore.util;

import com.belhard.bookstore.exceptions.BookException;
import com.belhard.bookstore.exceptions.ReadException;
import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.BookService;
import com.belhard.bookstore.service.BookServiceImpl;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.UserServiceImpl;

import java.util.Scanner;

public class ConsoleMenu {

    private static final BookService BOOK_SERVICE = new BookServiceImpl();
    private static final UserService USER_SERVICE = new UserServiceImpl();
    private static final String CONDITION_BOOK = "Enter \"all\" if you want to see list of all books." +
            "\nEnter \"get id\" (instead of {id}, the user specifies the id of the required book) " +
            "if you want to see more information about this book." +
            "\nEnter \"add\" if you want add book. " +
            "\nEnter \"delete id\" if you want to delete this book." +
            "\nEnter \"exit\" if you want to close this app";
    private static final String CONDITION_USER = "Enter \"all\" if you want to see list of all users." +
            "\nEnter \"get id\" (instead of {id}, the user specifies the id of the required user) " +
            "if you want to see more information about this user." +
            "\nEnter \"add\" if you want add user. " +
            "\nEnter \"delete id\" if you want to delete this user." +
            "\nEnter \"exit\" if you want to close this app";

    public static void bookMenu(Scanner sc) {
        while (true) {
            System.out.println(CONDITION_BOOK);
            String[] keyWords = sc.nextLine().split(" ");
            switch (keyWords[0]) {
                case "all":
                    try {
                        System.out.println(BOOK_SERVICE.getAllBooks());
                        break;
                    } catch (BookException e) {
                        System.out.println("There are no books.");
                        break;
                    }
                case "get":
                    try {
                        System.out.println("Book with id = " + keyWords[1] + " is:" +
                                BOOK_SERVICE.getBookById(Long.valueOf(keyWords[1])));
                        break;
                    } catch (BookException e) {
                        System.out.println("There is no book with id = " + keyWords[1]);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You should enter id.");
                        break;
                    }
                case "add":
                    try {
                        BookDto bookDto = ReaderForBook.readerForCreateBook(sc);
                        System.out.println("Created book:\n" + BOOK_SERVICE.createBook(bookDto));
                        break;
                    } catch (BookException e) {
                        System.out.println("The book has not been created. " +
                                "Perhaps this book is already in the database. " +
                                "Please check this book and try again.");
                        break;
                    } catch (ReadException e) {
                        System.out.println("It was your last attempt.");
                        break;
                    }
                case "delete":
                    try {
                        BOOK_SERVICE.deleteBook(Long.valueOf(keyWords[1]));
                        System.out.println("The book with id = " + keyWords[1] + " deleted.");
                        break;
                    } catch (BookException e) {
                        System.out.println("The book has not been deleted. Most likely there is no book with id = "
                                + keyWords[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You should enter id.");
                    }
                case "exit":
                    return;
                default:
                    System.out.println("Illegal input. Please try aging.");
                    break;
            }
        }
    }

    public static void userMenu(Scanner sc) {
        while (true) {
            System.out.println(CONDITION_USER);
            String[] command = sc.nextLine().split(" ");
            switch (command[0]) {
                case "all":
                    try {
                        System.out.println(USER_SERVICE.getAllUsers());
                        break;
                    } catch (UserException e){
                        System.out.println("There are no users.");
                        break;
                    }
                case "get":
                    try {
                        System.out.println("The user with id = " + command[1] + " is:"
                                + USER_SERVICE.getUserById(Long.valueOf(command[1])));
                        break;
                    } catch (UserException e) {
                        System.out.println("There is no user with id = " + command[1]);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You should enter id.");
                        break;
                    }
                case "add":
                    try {
                        UserDto newUser = ReaderForUser.readerForCreateUser(sc);
                        System.out.println("Created user: " + USER_SERVICE.createUser(newUser));
                        break;
                    } catch (UserException e) {
                        System.out.println("The user has not been created. " +
                                "Perhaps this user is already in the database. " +
                                "Please check this user and try again.");
                        break;
                    } catch (ReadException e) {
                        System.out.println("It was you last attempt.");
                        break;
                    }
                case "delete":
                    try {
                        USER_SERVICE.deleteUser(Long.valueOf(command[1]));
                        System.out.println("The user with id = " + command[1] + " was deleted.");
                        break;
                    } catch (UserException e) {
                        System.out.println("The user has not been deleted. Most likely there is no user with id = "
                                + command[1]);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("You should enter id.");
                        break;
                    }
                case "exit":
                    return;
                default:
                    System.out.println("Illegal input. You should choose correct command.");
                    break;
            }
        }
    }
}
