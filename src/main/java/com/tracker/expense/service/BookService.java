package com.tracker.expense.service;

import com.tracker.expense.model.Books;
import com.tracker.expense.model.BuyerInfo;
import com.tracker.expense.model.Person;
import com.tracker.expense.repository.BooksRepository;
import com.tracker.expense.repository.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class BookService {

    @Autowired
    private final BooksRepository booksRepository;
    @Autowired
    private final PersonRepository personRepository;

    public List<Books> getAllBooks() {

        List<Books> allBooks = booksRepository.findAll();
        return allBooks;
    }

    public void addBook(Books book) {
        System.out.println("saving book");
        booksRepository.save(book);
        System.out.println("book saved");

    }

    public String buyBook(BuyerInfo buyerInfo) {
        Person person = personRepository.findByUserName(buyerInfo.getUserName()).get();
        Books book;

        if (person.getBalance() >= buyerInfo.getCost()) {
            person.setBalance(person.getBalance() - buyerInfo.getCost());
            personRepository.save(person);
            book = booksRepository.findById(buyerInfo.getBookId()).get();
            System.out.println("Book :" + book);
            book.setBookOwner(buyerInfo.getUserName());
            addBook(book);



            return "Congratulations...! You have sucessfully bougth the book  with bookId  " + buyerInfo.getBookId() ;
        }

        return "Insufficient balance";

    }


}
