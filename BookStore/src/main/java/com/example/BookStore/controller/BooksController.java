package com.example.BookStore.controller;

import com.example.BookStore.domain.BookDTO;
import com.example.BookStore.entity.Book;
import com.example.BookStore.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class BooksController {
    @Autowired
    private BooksService booksService;

    @GetMapping("/books")
    public String getAllBooks(Model model){
       return getBooks(1,"name","asc",model);
    }

    @GetMapping("/books/page/{pageNo}")
    public String getBooks(@PathVariable("pageNo") int pageNo,
                           @RequestParam(defaultValue = "name") String sortBy,
                           @RequestParam(defaultValue = "asc")String sortDirection, Model model){
        int pageSize = 6;
        int startSerial = calculateStartSerialForPage(pageNo);
        model.addAttribute("startSerial", startSerial);
        Page<Book> bookPage = booksService.getBooks(pageNo,pageSize,sortBy,sortDirection);
        List<Book> bookList = bookPage.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", bookPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("books",bookList);

        return "listOfBooks";
    }

    private int calculateStartSerialForPage(int pageNo) {
        int pageSize = 6;
        return (pageNo - 1) * pageSize;
    }

    @GetMapping("/book/details/{id}")
    public String bookDetails(@PathVariable("id") Long id, Model model){
        model.addAttribute("book",booksService.getBookDetails(id));
        String imageUrl = "https://ds.rokomari.store/rokomari110/ProductNew20190903/260X372/Tomar_Jonno_Dariye_Chilam_Bole-Sadat_Hossain_-2465e-368730.jpg";
        model.addAttribute("imageUrl", imageUrl);
        return "book-details";
    }
    @GetMapping("/book/add-form")
    public String viewForm(Model model){
        model.addAttribute("book", new BookDTO());
        return "add-form";
    }
    @PostMapping("/book/create")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO, Model model,
                          RedirectAttributes redirectAttributes){
        model.addAttribute("book", bookDTO);
        redirectAttributes.addFlashAttribute("successMessage","New Book Added Successfully!");
        booksService.createBook(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/book-update-id/{id}")
    public String viewUpdateBook(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", booksService.updateBook(id));
        return "update-form";
    }
    @PostMapping("/book/updateBook/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") BookDTO bookDTO,
                                Model model, RedirectAttributes redirectAttributes){
        model.addAttribute("book", booksService.updateBookById(id,bookDTO));
        redirectAttributes.addFlashAttribute("successMessage","Book Update Successfully!");
        return "redirect:/books";
    }

    @GetMapping("/book/deleteById/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){
        booksService.deleteBookById(id);
        redirectAttributes.addFlashAttribute("successMessage","Book Deleted Successfully!");
        return "redirect:/books";
    }

}
