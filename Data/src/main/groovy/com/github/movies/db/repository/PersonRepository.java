package com.github.movies.db.repository;

import com.github.movies.db.entity.Person;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by developerSid on 1/12/17.
 */
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>
{
}
