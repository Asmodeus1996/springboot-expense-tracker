package com.tracker.expense.web.controller;

import com.tracker.expense.model.Books;
import com.tracker.expense.model.BuyerInfo;
import com.tracker.expense.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @Autowired
    @GetMapping("/allbooks")
    public ResponseEntity<List<Books>> getAllBooks() {

        List<Books> allBooks = bookService.getAllBooks();
        return new ResponseEntity<List<Books>>(allBooks, HttpStatus.OK);

    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody Books book) {

        try {
            bookService.addBook(book);
        } catch (Exception e) {
            log.error("Exception in adding book with Book Id : {}", book.getId());
        }
        return new ResponseEntity<>("Book added with Book Id : " + book.getId(), HttpStatus.OK);
    }


    @PostMapping("/buyBook")
    public ResponseEntity<String> buyBook(@RequestBody BuyerInfo buyerInfo ) {
            String msg =  null ;
        try{
           msg = bookService.buyBook(buyerInfo);

        }catch(Exception e){
         return new ResponseEntity<String>("Insufficient balance to buy book " , HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<String>(msg , HttpStatus.OK);
    }


}