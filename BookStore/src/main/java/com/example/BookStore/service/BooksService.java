package com.example.BookStore.service;

import com.example.BookStore.domain.BookDTO;
import com.example.BookStore.entity.Book;
import com.example.BookStore.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BooksService {
    @Autowired
    private BooksRepository booksRepository;

    public Page<Book> getBooks(int pageNo, int pageSize, String sortBy, String sortDirection) {
       Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable paging = PageRequest.of(pageNo - 1,pageSize,sort);
        return booksRepository.findAll(paging);

    }

    public void createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setAuthor(bookDTO.getAuthor());
        book.setCategory(bookDTO.getCategory());
        book.setPrice(bookDTO.getPrice());
        book.setPages(bookDTO.getPages());
        book.setEdition(bookDTO.getEdition());
        book.setPublication(bookDTO.getPublication());
        book.setISBN(bookDTO.getISBN());
        Book b = booksRepository.save(book);
        BookDTO.bookEntityToDTO(b);
    }

    public Book updateBook(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        Book newBook = null;
        if(book.isPresent()){
            newBook = book.get();
            booksRepository.save(newBook);
        }
        return newBook;
    }

    public BookDTO updateBookById(Long id, BookDTO bookDTO) {
        Optional<Book> book = booksRepository.findById(id);
        Book presentBook = null;
        if(book.isPresent()){
            presentBook = book.get();
            presentBook.setId(id);
            presentBook.setName(bookDTO.getName());
            presentBook.setAuthor(bookDTO.getAuthor());
            presentBook.setCategory(bookDTO.getCategory());
            presentBook.setPrice(bookDTO.getPrice());
            presentBook.setPages(bookDTO.getPages());
            presentBook.setEdition(bookDTO.getEdition());
            presentBook.setPublication(bookDTO.getPublication());
            presentBook.setISBN(bookDTO.getISBN());
            booksRepository.save(presentBook);
        }
        return BookDTO.bookEntityToDTO(presentBook);
    }

    public void deleteBookById(Long id) {
        booksRepository.deleteById(id);
    }

    public BookDTO getBookDetails(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        Book b = book.get();
        return BookDTO.bookEntityToDTO(b);
    }
}
