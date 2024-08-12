package com.tracker.expense.service;


import com.tracker.expense.model.Person;
import com.tracker.expense.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.apache.catalina.realm.UserDatabaseRealm.getRoles;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository ;
    @Override
    public UserDetails loadUserByUsername(String userName){

      Optional<Person> personOptional = personRepository.findByUserName(userName);
      Person person = personOptional.orElseThrow(() -> new RuntimeException("Person Not Found"));
      return new User(person.getUserName() , person.getPassword() , person.isEnabled() ,  true , true , true , getRoles("user")) ;


    }

    private Collection<? extends GrantedAuthority> getRoles(String role) {

        return Collections.singletonList(new SimpleGrantedAuthority(role));

    }


}
