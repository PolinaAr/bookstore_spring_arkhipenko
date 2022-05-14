package com.belhard.bookstore.util;

import com.belhard.bookstore.exceptions.ReadException;
import com.belhard.bookstore.exceptions.UserException;
import com.belhard.bookstore.service.BookDto;
import com.belhard.bookstore.service.UserDto;
import com.belhard.bookstore.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

public class ReaderForUser {

    private static final Logger logger = LogManager.getLogger(ReaderForUser.class);
    private static Calendar calendar = Calendar.getInstance();

    public static UserDto readerForCreateUser(Scanner sc) {
        UserDto userDto = new UserDto();
        System.out.println("Enter user name.");
        for (int i = 0; i < 3; i++) {
            String name = sc.nextLine();
            if (!name.isBlank()) {
                userDto.setName(name);
                break;
            } else {
                System.out.println("Illegal input. You should enter name");
                if (i == 2) {
                    logger.error("Illegal input of name for 3 times");
                    throw new ReadException("It was last attempt of entering name");
                }
            }
        }
        System.out.println("Enter user last name.");
        for (int i = 0; i < 3; i++) {
            String lastName = sc.nextLine();
            if (!lastName.isBlank()) {
                userDto.setLastName(lastName);
                break;
            } else {
                System.out.println("Illegal input. You should enter last name");
                if (i == 2) {
                    logger.error("Illegal input of last name for 3 times");
                    throw new ReadException("It was last attempt of entering last name.");
                }
            }
        }
        System.out.println("Enter user role: admin/manager/customer");
        for (int i = 0; i < 3; i++) {
            String role = sc.nextLine().toUpperCase();
            if (role.equals("ADMIN") || role.equals("MANAGER") || role.equals("CUSTOMER")) {
                userDto.setRole(UserDto.Role.valueOf(role));
                break;
            } else {
                System.out.println("Illegal input: " + role + ". You should enter correct role");
                if (i == 2) {
                    logger.error("Illegal input of role for 3 times");
                    throw new ReadException("It was last attempt of entering role.");
                }
            }
        }
        System.out.println("Enter user email");
        for (int i = 0; i < 3; i++) {
            String email = sc.nextLine();
            if (email.matches("\\w+@[a-z]+\\.[a-z]+")) {
                userDto.setEmail(email);
                break;
            } else {
                System.out.println("Illegal input: " + email + ". You should enter correct email");
                if (i == 2) {
                    logger.error("Illegal input of email for 3 times.");
                    throw new ReadException("It was last attempt of entering email");
                }
            }
        }
        System.out.println("Enter user password.");
        for (int i = 0; i < 3; i++) {
            String password = sc.nextLine();
            if (!password.isBlank()) {
                userDto.setPassword(password);
                break;
            } else {
                System.out.println("Illegal input. You should enter password");
                if (i == 2) {
                    logger.error("Illegal input of password for 3 times");
                    throw new ReadException("It was last attempt of entering password.");
                }
            }
        }
        System.out.println("Enter birthday.");
        System.out.println("Enter year of birthday");
        int year = 0;
        for (int i = 0; i < 3; i++) {
            if (sc.hasNextInt()){
                year = sc.nextInt();
                if (calendar.get(Calendar.YEAR) - year > 100 || calendar.get(Calendar.YEAR) - year < 10) {
                    System.out.println("Illegal input of year of birthday: " + year + ". You should enter correct year");
                    if (i == 2) {
                        logger.error("Illegal input of year of birthday for 3 times");
                        throw new ReadException("It was last attempt of entering birthday.");
                    }
                } else {
                    break;
                }
            } else {
                String resetScanner = sc.nextLine();
                System.out.println("Illegal input: " + ". You should enter correct year.");
                if (i == 2) {
                    logger.error("Illegal input of year for 3 times");
                    throw new ReadException("It was last attempt of entering year");
                }
            }
        }
        System.out.println("Enter month of birthday");
        int month = 0;
        for (int i = 0; i < 3; i++) {
            if (sc.hasNextInt()){
                month = sc.nextInt();
                if (month < 0 || month > 12) {
                    System.out.println("Illegal input of month of birthday: " + month + ". You should enter correct month");
                    if (i == 2) {
                        logger.error("Illegal input of month of birthday for 3 times");
                        throw new ReadException("It was last attempt of entering year of birthday.");
                    }
                } else {
                    break;
                }
            } else {
                String resetScanner = sc.nextLine();
                System.out.println("Illegal input: " + resetScanner + ". You should enter correct month.");
                if (i == 2) {
                    logger.error("Illegal input of month for 3 times");
                    throw new ReadException("It was last attempt of entering month of birthday");
                }
            }
        }
        System.out.println("Enter day of birthday");
        int day = 0;
        for (int i = 0; i < 3; i++) {
            if (sc.hasNextInt()){
                day = sc.nextInt();
                if (day < 0 || day > 31) {
                    System.out.println("Illegal input of day of birthday: " + day + ". You should enter correct day");
                    if (i == 2) {
                        logger.error("Illegal input of day of birthday for 3 times");
                        throw new ReadException("It was last attempt of entering day of birthday.");
                    }
                } else {
                    sc.nextLine();
                    break;
                }
            } else {
                String resetScanner = sc.nextLine();
                System.out.println("Illegal input: " + resetScanner + ". You should enter correct day.");
                if (i == 2) {
                    logger.error("Illegal input of day for 3 times");
                    throw new ReadException("It was last attempt of entering day");
                }
            }
        }
        userDto.setBirthday(LocalDate.of(year, month, day));
        return userDto;
    }


}
