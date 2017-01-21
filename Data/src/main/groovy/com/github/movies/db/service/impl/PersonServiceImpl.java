package com.github.movies.db.service.impl;

import com.github.movies.db.entity.Person;
import com.github.movies.db.repository.PersonRepository;
import com.github.movies.db.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developerSid on 1/12/17.
 *
 * Provides the JPA based Person Service
 */
@Service
public class PersonServiceImpl implements PersonService
{
   private final PersonRepository personRepository;

   public PersonServiceImpl(@Autowired PersonRepository personRepository)
   {
      this.personRepository = personRepository;
   }

   @Override
   public void save(Person person)
   {
      personRepository.save(person);
   }
}
