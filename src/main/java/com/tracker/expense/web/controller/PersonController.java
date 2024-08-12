package com.tracker.expense.web.controller;

import com.tracker.expense.service.PersonService;
import com.tracker.expense.web.dto.AuthRequest;
import com.tracker.expense.web.dto.AuthResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        personService.register(authRequest);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(personService.login(authRequest), HttpStatus.CREATED);
    }

    @GetMapping("/verifyAccount/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable("token") String token){
        boolean isVerified = personService.verifyAccount(token);
        if(isVerified) {
            return new ResponseEntity<>("Congratulations...! Account has been verified." , HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Oops.. ! Account not Verified" , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<AuthRequest> findUserName(@PathVariable("userName") String userName) {
        return new ResponseEntity<>(personService.findByUserName(userName), HttpStatus.OK);

    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<AuthRequest>> findAllUsers() {
        return new ResponseEntity<>(personService.findAllUsers(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<AuthRequest> updateUser(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(personService.updateUser(authRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable("userName") String userName) {
        String status = personService.deleteUser(userName) ? "Success" : "Error";
        return new ResponseEntity<> (status, HttpStatus.OK);
    }

}
