package com.tracker.expense.service;

import com.tracker.expense.constants.ExpenseConstants;
import com.tracker.expense.model.EmailNotification;
import com.tracker.expense.model.Person;
import com.tracker.expense.model.ValidationToken;
import com.tracker.expense.repository.PersonRepository;
import com.tracker.expense.repository.ValidationTokenRepository;
import com.tracker.expense.security.JwtProvider;
import com.tracker.expense.web.dto.AuthRequest;
import com.tracker.expense.web.dto.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationTokenRepository validationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;


    public void register(AuthRequest authRequest) {
        Person person = dtoToMap(authRequest, false, new Person());
        person.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        personRepository.save(person);
        String token = getAuthToken(person);
        log.info("token is " +token);
        mailService.sendMail(new EmailNotification(person.getEmail(),
                ExpenseConstants.ACTIVATION_MAIL_SUBJECT_LINE,
                "Hello "+person.getName() + ", " +
                        "\nPlease click on the below link to activate your account \n "
                        + "http://localhost:9000/api/auth/verifyAccount/" + token));

    }

    private String getAuthToken(Person person) {
        String autToken = UUID.randomUUID().toString();
        ValidationToken validationToken = new ValidationToken();
        validationToken.setToken(autToken);
        validationToken.setPerson(person);

        validationTokenRepository.save(validationToken);
        return autToken;

    }


    public boolean verifyAccount(String token) {

        Optional<ValidationToken> validationToken = validationTokenRepository.findByToken(token);
        validationToken.orElseThrow(( ) -> new RuntimeException("Invalid token"));
        fetchPersonAndEnable(validationToken.get());
        return true;
    }

    private void fetchPersonAndEnable(ValidationToken validationToken) {

        String userName = validationToken.getPerson().getUserName();
        Person person = personRepository.findByUserName(userName).orElseThrow(( ) -> new RuntimeException("Person Not Found"));
        person.setEnabled(true);
        personRepository.save(person);
    }

    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUserName(), authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new AuthResponse(token, authRequest.getUserName());

    }

    @Transactional(readOnly = true)
    public AuthRequest findByUserName(String userName) {
        Person person = personRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User Not Found!!"));
        return mapToDto(person);
    }

    public List<AuthRequest> findAllUsers() {
        return personRepository
                .findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public AuthRequest updateUser(AuthRequest authRequest) {
        Person person = personRepository.findByUserName(authRequest.getUserName())
                .orElseThrow(() -> new RuntimeException("User Not Found!!"));
            
        Person updatedPerson = dtoToMap(authRequest, true, person);
        updatedPerson.setPersonId(person.getPersonId());
        if(authRequest.getPassword() != null) {
            person.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        } else {
            updatedPerson.setPassword(person.getPassword());
        }
        return mapToDto(personRepository.save(updatedPerson));
    }

    public boolean deleteUser(String userName) {
        Person person = personRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User Not Found!!"));
        validationTokenRepository.deleteByPerson(person);
        personRepository.deleteByUserName(person.getUserName());
        return true;
    }

    private Person dtoToMap(AuthRequest authRequest, boolean isEnabled, Person person) {
        person.setUserName(authRequest.getUserName());
        person.setProfession(authRequest.getProfession());
        person.setName(authRequest.getName());
        person.setEmail(authRequest.getEmail());
        person.setBalance(authRequest.getBalance());
        person.setEnabled(isEnabled);
        return person;
    }

    private AuthRequest mapToDto(Person person) {
        AuthRequest userDetails = new AuthRequest();
        userDetails.setUserName(person.getUserName());
        userDetails.setName(person.getName());
        userDetails.setProfession(person.getProfession());
        userDetails.setEmail(person.getEmail());
        return userDetails;
    }


}
