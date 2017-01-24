package com.github.movies.db.repository;

import com.github.movies.db.entity.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by developerSid on 1/12/17.
 *
 * The {@link Person} repository
 */
@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>
{
}
