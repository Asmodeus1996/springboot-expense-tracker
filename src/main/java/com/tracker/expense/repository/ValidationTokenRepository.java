package com.tracker.expense.repository;

import com.tracker.expense.model.Person;
import com.tracker.expense.model.ValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Repository
@Transactional
public interface ValidationTokenRepository extends JpaRepository<ValidationToken, Long> {

    Optional<ValidationToken> findByToken(String token);
    void deleteByPerson(Person person);
}
