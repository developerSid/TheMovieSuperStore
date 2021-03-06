package com.github.movies.db.loader.processor;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.entity.Credit;
import com.github.movies.db.entity.copy.ObjectCopying;
import com.github.movies.db.loader.services.TheMovieDBService;
import com.github.movies.db.service.CreditService;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
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
   private final ObjectCopying objectCopying;

   @Autowired
   public LoadCreditsProcessor(CreditService creditService, TheMovieDBService theMovieDBService, ObjectCopying objectCopying)
   {
      this.creditService = creditService;
      this.theMovieDBService = theMovieDBService;
      this.objectCopying = objectCopying;
   }

   @Override
   public Movie apply(Movie movie)
   {
      List<Credit> credits = theMovieDBService.loadCredits(movie);

      movie = objectCopying.copy(movie);

      for(Credit credit: credits)
      {
         if(StringUtils.equalsIgnoreCase("director", credit.getJob().trim()))
         {
            movie.getDirectors().add(credit);
         }
         else if(StringUtils.length(credit.getJob()) > 3)
         {
            movie.getCast().add(credit);
         }
      }

      return movie;
   }
}
