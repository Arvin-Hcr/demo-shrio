package com.hcr.shiro.model;

import java.io.Serializable;
import java.util.Objects;

public class Book implements Serializable {
    private Long id;

    private String bookName;

    private String bookPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice == null ? null : bookPrice.trim();
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", bookPrice='" + bookPrice + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) &&
                Objects.equals(getBookName(), book.getBookName()) &&
                Objects.equals(getBookPrice(), book.getBookPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBookName(), getBookPrice());
    }
}