package com.belhard.bookstore.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isbn", length = 17, unique = true, nullable = false)
    private String isbn;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "author", length = 100, nullable = false)
    private String author;

    @Column(name = "pages")
    private int pages;

    @Column(name = "price", columnDefinition = "decimal default 0.0", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "cover_id")
    private Cover cover;

    public enum Cover {
        SOFT, HARD, GIFT
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pages == book.pages && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(price, book.price) && cover == book.cover;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, pages, price, cover);
    }

    @Override
    public String toString() {
        return "\nBook{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", price=" + price +
                ", cover=" + cover +
                '}';
    }
}
