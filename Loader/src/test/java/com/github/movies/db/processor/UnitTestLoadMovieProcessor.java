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
   private MovieService movieService;
   private LoadMovieProcessor loadMovieProcessor;

   @Before
   public void before()
   {
      theMovieDBService = Mockito.mock(TheMovieDBService.class);
      movieService = Mockito.mock(MovieService.class);
      loadMovieProcessor = new LoadMovieProcessor(theMovieDBService);
   }

   @Test
   public void testMovieLoaded_Successful()
   {
      Movie movie = new Movie();
      Movie saved = new Movie();
      saved.setId(1L);

      Mockito.when(theMovieDBService.loadMovie(1)).thenReturn(Optional.of(movie));
      Mockito.when(movieService.saveMovie(movie)).thenReturn(saved);

      Optional<Movie> result = loadMovieProcessor.apply(1);

      Assertions.assertThat(result)
         .isPresent()
         .containsSame(saved)
      ;

      InOrder inOrder = Mockito.inOrder(theMovieDBService, movieService);
      inOrder.verify(theMovieDBService, Mockito.calls(1)).loadMovie(1);
      inOrder.verify(movieService, Mockito.calls(1)).saveMovie(movie);
   }

   @Test
   public void testMovieLoaded_Failure()
   {
      Movie movie = new Movie();

      Mockito.when(theMovieDBService.loadMovie(1)).thenReturn(Optional.empty());

      Optional<Movie> result = loadMovieProcessor.apply(1);

      Assertions.assertThat(result)
         .isNotPresent()
      ;

      InOrder inOrder = Mockito.inOrder(theMovieDBService, movieService);

      inOrder.verify(theMovieDBService, Mockito.calls(1)).loadMovie(1);
      inOrder.verify(movieService, Mockito.never()).saveMovie(movie);
      inOrder.verifyNoMoreInteractions();
   }
}
