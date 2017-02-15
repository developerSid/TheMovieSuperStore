package com.github.movies.db.processor;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.loader.processor.LoadMovieProcessor;
import com.github.movies.db.loader.services.TheMovieDBService;
import com.github.movies.db.service.MovieService;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * Created by developerSid on 1/28/17.
 */
public class UnitTestLoadMovieProcessor
{
   private TheMovieDBService theMovieDBService;
   private LoadMovieProcessor loadMovieProcessor;

   @Before
   public void before()
   {
      theMovieDBService = Mockito.mock(TheMovieDBService.class);
      loadMovieProcessor = new LoadMovieProcessor(theMovieDBService);
   }

   @Test
   public void testMovieLoaded_Successful()
   {
      Movie movie = new Movie();

      Mockito.when(theMovieDBService.loadMovie(1)).thenReturn(Optional.of(movie));

      Optional<Movie> result = loadMovieProcessor.apply(1);

      Assertions.assertThat(result)
         .isPresent()
      ;

      InOrder inOrder = Mockito.inOrder(theMovieDBService);
      inOrder.verify(theMovieDBService, Mockito.calls(1)).loadMovie(1);
   }

   @Test
   public void testMovieLoaded_Failure()
   {
      Mockito.when(theMovieDBService.loadMovie(1)).thenReturn(Optional.empty());

      Optional<Movie> result = loadMovieProcessor.apply(1);

      Assertions.assertThat(result)
         .isNotPresent()
      ;

      InOrder inOrder = Mockito.inOrder(theMovieDBService);

      inOrder.verify(theMovieDBService, Mockito.calls(1)).loadMovie(1);
      inOrder.verifyNoMoreInteractions();
   }
}
