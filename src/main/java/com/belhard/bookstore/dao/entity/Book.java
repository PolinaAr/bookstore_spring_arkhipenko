package com.belhard.bookstore.dao.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private int pages;
    private Cover cover;
    private BigDecimal price;

    public enum Cover {
        SOFT, HARD, GIFT,
    }

    public Book() {
    }

    public Book(String isbn, String title, String author, int pages, Cover cover, BigDecimal price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.cover = cover;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages
                && Objects.equals(isbn, book.isbn)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && cover == book.cover
                && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, author, pages, cover, price);
    }

    @Override
    public String toString() {
        return "\nBook{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", cover=" + cover +
                ", price=" + price +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
