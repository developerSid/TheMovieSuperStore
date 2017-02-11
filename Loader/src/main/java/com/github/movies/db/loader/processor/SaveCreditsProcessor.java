package com.github.movies.db.loader.processor;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.service.CreditService;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developerSid on 2/10/17.
 *
 * Saves credits from a movie
 */
@Service
public class SaveCreditsProcessor implements Function<Movie, Movie>
{
   private final CreditService creditService;

   @Autowired
   public SaveCreditsProcessor(CreditService creditService)
   {
      this.creditService = creditService;
   }

   @Override
   public Movie apply(Movie movie)
   {
      creditService.saveAll(movie.getCast());
      creditService.saveAll(movie.getDirectors());

      return movie;
   }
}
