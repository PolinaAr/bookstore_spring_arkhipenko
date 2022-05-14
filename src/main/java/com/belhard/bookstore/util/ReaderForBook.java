package com.belhard.bookstore.util;

import com.belhard.bookstore.exceptions.ReadException;
import com.belhard.bookstore.service.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Scanner;

public class ReaderForBook {

    private static final Logger logger = LogManager.getLogger(ReaderForBook.class);

    public static BookDto readerForCreateBook(Scanner sc) {
        BookDto bookDto = new BookDto();
        System.out.println("Enter isbn in format nnn-n-nn-nnnnnn-n:");
        for (int i = 0; i < 3; i++) {
            String isbn = sc.nextLine();
            if (isbn.matches("\\d{3}-\\d-\\d{2}-\\d{6}-\\d")) {
                bookDto.setIsbn(isbn);
                break;
            } else {
                System.out.println("Illegal input: " + isbn + ". You should enter correct isbn");
                if (i == 2) {
                    logger.error("Illegal input of isbn for 3 times.");
                    throw new ReadException("It was last attempt of entering isbn");
                }
            }
        }
        System.out.println("Enter title:");
        for (int i = 0; i < 3; i++) {
            String title = sc.nextLine();
            if (!title.isBlank()) {
                bookDto.setTitle(title);
                break;
            } else {
                System.out.println("Illegal input. You should enter title");
                if (i == 2) {
                    logger.error("Illegal input of title for 3 times");
                    throw new ReadException("It was last attempt of entering title");
                }
            }
        }
        System.out.println("Enter author:");
        for (int i = 0; i < 3; i++) {
            String author = sc.nextLine();
            if (!author.isBlank()) {
                bookDto.setAuthor(author);
                break;
            } else {
                System.out.println("Illegal input. You should enter author.");
                if (i == 2) {
                    logger.error("Illegal input of author for 3 times");
                    throw new ReadException("It was last attempt of entering author");
                }
            }
        }
        System.out.println("Enter type of cover (soft/hard/gift):");
        for (int i = 0; i < 3; i++) {
            String cover = sc.nextLine().toUpperCase();
            if (cover.equals("SOFT") || cover.equals("HARD") || cover.equals("GIFT")) {
                bookDto.setCover(BookDto.Cover.valueOf(cover));
                break;
            } else {
                System.out.println("Illegal input: " + cover + ". You should enter correct cover");
                if (i == 2) {
                    logger.error("Illegal input of cover for 3 times");
                    throw new ReadException("It was last attempt of entering author.");
                }
            }
        }
        System.out.println("Enter number of pages:");
        for (int i = 0; i < 3; i++) {
            if (sc.hasNextInt()) {
                int pages = sc.nextInt();
                if (pages > 0) {
                    bookDto.setPages(pages);
                    break;
                } else {
                    System.out.println("Illegal input: " + pages + ". You should enter correct number of pages");
                    if (i == 2) {
                        logger.error("Illegal input of pages for 3 times");
                        throw new ReadException("It was last attempt of entering pages.");
                    }
                }
            } else {
                String resetScanner = sc.nextLine();
                System.out.println("Illegal input: " + resetScanner + ". You should enter correct number of pages");
                if (i == 2) {
                    logger.error("Illegal input of pages for 3 times");
                    throw new ReadException("It was last attempt of entering pages.");
                }
            }
        }
        System.out.println("Enter price:");
        for (int i = 0; i < 3; i++) {
            if (sc.hasNextBigDecimal()) {
                BigDecimal price = sc.nextBigDecimal();
                String reset = sc.nextLine();
                if (price.compareTo(BigDecimal.ZERO) > 0) {
                    bookDto.setPrice(price);
                    break;
                } else {
                    System.out.println("Illegal input: " + price + ". You should enter correct price.");
                    if (i == 2) {
                        logger.error("Illegal input of price for 3 times");
                        throw new ReadException("It was last attempt of entering price.");
                    }
                }
            } else {
                String resetScanner = sc.nextLine();
                System.out.println("Illegal input: " + resetScanner + ". You should enter correct price.");
                if (i == 2) {
                    logger.error("Illegal input of price for 3 times");
                    throw new ReadException("It was last attempt of entering price");
                }
            }
        }

        return bookDto;
    }
}
