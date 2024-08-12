package com.tracker.expense.service;

import com.tracker.expense.model.EmailNotification;
import com.tracker.expense.model.Person;
import com.tracker.expense.model.ValidationToken;
import com.tracker.expense.repository.PersonRepository;
import com.tracker.expense.repository.ValidationTokenRepository;
import com.tracker.expense.security.JwtProvider;
import com.tracker.expense.web.dto.AuthRequest;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Mock
    private ValidationTokenRepository validationTokenRepository;
    @Mock
    private MailService mailService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider jwtProvider;

    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService(personRepository,passwordEncoder,validationTokenRepository,mailService,authenticationManager,jwtProvider);
        when(passwordEncoder.encode(any())).thenReturn("pwd");
        when(personRepository.findByUserName("aa")).thenReturn(Optional.of(new Person(1L, "aa", "aa", "aa", "aa@aa.aa", "it", false,500)));
    }

    @Test
    void register() {
        Person person = new Person(2L,"bb","bb","bb","bb@bb.bb","it",false, 5000);
        ValidationToken validationToken = new ValidationToken(1L,"1111212", Instant.now(), person);
        EmailNotification emailNotification = new EmailNotification(person.getEmail(),"Expense Tracker Account Registration...Please Activate your account",any());

        when(personRepository.save(person)).thenReturn(person);
        when(validationTokenRepository.save(validationToken)).thenReturn(validationToken);
        doNothing().when(mailService).sendMail(emailNotification);
        personService.register(new AuthRequest("bb","bb","aa@bb.cc","it","aa",500.0));
        assertNotNull(person.getPersonId());
    }

    @Test
    void verifyAccount() {
        Person person = new Person(2L,"bb","bb","bb","bb@bb.bb","it",false,500.0);
        when(validationTokenRepository.findByToken("token")).thenReturn(Optional.of(new ValidationToken(1L, "111", Instant.now(),person)));
        when(personRepository.findByUserName(person.getUserName())).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        boolean flag = personService.verifyAccount("token");
        assert(flag);
    }

    @Test
    void login() {
    }

    @Test
    void findByUserName() {
        AuthRequest authRequest = personService.findByUserName("aa");
        assertEquals("aa@aa.aa", authRequest.getEmail());
    }

    @Test
    void findAllUsers() {
        when(personRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Person(1L, "aa", "aa", "aa", "aa@aa.aa", "it", false,500.0),
                        new Person(2L, "bb", "bb", "bb", "bb@bb.bb", "it", false,500.0)));

        List<AuthRequest> list = personService.findAllUsers();
        assertEquals(2, list.size());
    }

    @Test
    void updateUserWithNullPassword() {
        Person person = new Person(2L,"bb","bb","bb","bb@bb.bb","it",false,500.0);
        AuthRequest authRequest = new AuthRequest("bb",null,"aa@bb.cc","it","aa",500.0);
        when(personRepository.findByUserName("bb")).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        AuthRequest updated = personService.updateUser(authRequest);
        assertEquals(updated.getEmail(), "aa@bb.cc");
    }
    @Test
    void updateUserWithPassword() {
        Person person = new Person(2L,"bb","bb","bb","bb@bb.bb","it",false,500.0);
        AuthRequest authRequest = new AuthRequest("bb","cc","aa@bb.cc","it","aa",500.0);
        when(personRepository.findByUserName("bb")).thenReturn(Optional.of(person));
        when(personRepository.save(person)).thenReturn(person);
        AuthRequest updated = personService.updateUser(authRequest);
        assertEquals(updated.getEmail(), "aa@bb.cc");
    }

    @Test
    void deleteUser() {
        Person person = new Person(2L,"bb","bb","bb","bb@bb.bb","it",false,500.0);
        when(personRepository.findByUserName("bb")).thenReturn(Optional.of(person));
        doNothing().when(validationTokenRepository).deleteByPerson(person);
        doNothing().when(personRepository).deleteByUserName(person.getUserName());
        boolean flag = personService.deleteUser("bb");
        assert(flag);
    }
}