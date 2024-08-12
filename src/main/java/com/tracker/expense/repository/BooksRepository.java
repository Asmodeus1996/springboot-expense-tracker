package com.tracker.expense.repository;

import com.tracker.expense.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BooksRepository extends JpaRepository<Books, Integer> {

}
