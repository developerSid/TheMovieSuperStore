package com.github.movies.db.loader.services;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.entity.Person;

import java.util.List;
import java.util.Optional;

/**
 * Created by developerSid on 1/20/17.
 *
 * Defines access to the https://www.themoviedb.org API
 */
public interface TheMovieDBService
{
   Optional<Movie> loadMovie(int id);
   List<Person> loadDirectors(Movie movie);
}
