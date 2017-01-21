package com.github.movies.db.loader.processor;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.entity.Person;
import com.github.movies.db.loader.services.TheMovieDBService;
import com.github.movies.db.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * Created by developerSid on 1/20/17.
 *
 * Loads directors associated with a movie and saves them to the the storage
 */
@Component
public class LoadDirectorsProcessor implements Function<Movie, Movie>
{
   private final PersonService personService;
   private final TheMovieDBService theMovieDBService;

   @Autowired
   public LoadDirectorsProcessor(PersonService personService, TheMovieDBService theMovieDBService)
   {
      this.personService = personService;
      this.theMovieDBService = theMovieDBService;
   }

   @Override
   public Movie apply(Movie movie)
   {
      List<Person> directors = theMovieDBService.loadDirectors(movie);

      return movie;
   }
}
