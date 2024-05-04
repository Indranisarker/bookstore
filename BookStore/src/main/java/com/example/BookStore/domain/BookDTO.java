package com.example.BookStore.domain;

import com.example.BookStore.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String name;
    private String author;
    private String category;
    private double price;
    private int pages;
    private int edition;
    private String publication;
    private int ISBN;

    public static BookDTO bookEntityToDTO(Book book){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(book.getName());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setCategory(book.getCategory());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setPages(book.getPages());
        bookDTO.setEdition(book.getEdition());
        bookDTO.setPublication(book.getPublication());
        bookDTO.setISBN(book.getISBN());
        return bookDTO;
    }
}
