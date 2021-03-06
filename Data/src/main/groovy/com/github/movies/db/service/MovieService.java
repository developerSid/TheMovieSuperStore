package com.github.movies.db.service;

import com.github.movies.db.entity.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

/**
 * Created by developerSid on 1/12/17.
 *
 * Defines how to access movies in the system.
 */

public interface MovieService
{
   Movie saveMovie(Movie movie);
   List<Movie> findMovie(String title, Pageable pageable);
   Optional<Movie> findMovie(Long id);
   Optional<Movie> loadMovieGraph(Long id);
   List<Movie> findByDirectorName(String directorName);
}
