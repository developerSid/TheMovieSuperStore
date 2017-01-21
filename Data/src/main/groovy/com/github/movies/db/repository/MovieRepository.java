package com.github.movies.db.repository;

import com.github.movies.db.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by developerSid on 1/11/17.
 *
 * The movie repository
 */
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>
{
   List<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
