package com.github.movies.db.loader.services;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.entity.Person;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Created by developerSid on 1/20/17.
 *
 * Maps {@link TheMovieDBService} calls to the Movito API wrapper
 */
@Service
public class MovitoTheMovieDBService implements TheMovieDBService
{
   private final TmdbApi tmdbApi;

   public MovitoTheMovieDBService(@Value("${tmdb.api.key}") String apiKey)
   {
      this.tmdbApi = new TmdbApi(apiKey);
   }

   @Override
   public Movie loadMovie(int id)
   {
      MovieDb movie = tmdbApi.getMovies().getMovie(id, "en");
      Movie toReturn = new Movie(movie.getTitle(), movie.getOverview(), movie.getId());

      return toReturn;
   }

   @Override
   public List<Person> loadDirectors(Movie movie)
   {
      tmdbApi.getMovies().getCredits(movie.getTheMovieDBid()).getAll().forEach(i -> System.out.printf("credit id: %s credit name: %s\n", i.getCreditId(), i.getName()));
      tmdbApi.getPeople().getPersonCredits(1).getCrew().stream().forEach(i -> i.);

      return Collections.emptyList();
   }
}
