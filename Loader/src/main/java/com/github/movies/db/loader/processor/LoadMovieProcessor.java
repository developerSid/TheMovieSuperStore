package com.github.movies.db.loader.processor;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.loader.services.TheMovieDBService;
import com.github.movies.db.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

/**
 * Created by developerSid on 1/20/17.
 *
 * Handles loading movies based on a request to load the movie
 */
@Component
public class LoadMovieProcessor implements Function<Integer, Optional<Movie>>
{
   private final TheMovieDBService theMovieDBService;
   private final MovieService movieService;

   @Autowired
   public LoadMovieProcessor(TheMovieDBService theMovieDBService, MovieService movieService)
   {
      this.theMovieDBService = theMovieDBService;
      this.movieService = movieService;
   }

   @Override
   public Optional<Movie> apply(Integer movieId)
   {
      Optional<Movie> movie = theMovieDBService.loadMovie(movieId).map(movieService::saveMovie);

      //movie.ifPresent();

      return movie;
   }
}
