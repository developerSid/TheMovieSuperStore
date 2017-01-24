package com.github.movies.db.repository;

import com.github.movies.db.entity.Genre;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by developerSid on 1/22/17.
 *
 * Repository for {@link Genre}
 */
@Repository
public interface GenreRepository extends PagingAndSortingRepository<Genre, Long>
{
   Set<Genre> findByNameIn(Iterable<String> genres);
}
