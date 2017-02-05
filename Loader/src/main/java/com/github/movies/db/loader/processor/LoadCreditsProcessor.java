package com.github.movies.db.loader.processor;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.entity.Credit;
import com.github.movies.db.loader.services.TheMovieDBService;
import com.github.movies.db.service.CreditService;
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
public class LoadCreditsProcessor implements Function<Movie, Movie>
{
   private final CreditService creditService;
   private final TheMovieDBService theMovieDBService;

   @Autowired
   public LoadCreditsProcessor(CreditService creditService, TheMovieDBService theMovieDBService)
   {
      this.creditService=creditService;
      this.theMovieDBService = theMovieDBService;
   }

   @Override
   public Movie apply(Movie movie)
   {
      List<Credit> directors = theMovieDBService.loadCredits(movie);

      return movie;
   }
}
