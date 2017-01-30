package com.github.movies.db.service;

import com.github.movies.db.entity.Person;

import java.util.Optional;

/**
 * Created by developerSid on 1/12/17.
 *
 * Describes the operations provided for saving and loading people
 */
public interface PersonService
{
   Optional<Person> save(Person person);
}
