package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by developerSid on 1/26/17.
 *
 * Unit test that tests the functionality of the {@link JpaMovieService}
 */
public class UnitTestJpaMovieService
{
   private GenreService genreService;
   private MovieRepository movieRepository;
   private JpaMovieService movieService;

   @Before
   public void before()
   {
      genreService = Mockito.mock(GenreService.class);
      movieRepository = Mockito.mock(MovieRepository.class);
      movieService = new JpaMovieService(movieRepository, genreService);
   }

   @Test
   public void testFindMovieByMovieId_Found()
   {
      Movie movie = new Movie();
      movie.setId(1L);
      movie.setTitle("Movie Title");
      movie.setDescription("Movie");

      Mockito.when(movieRepository.findOne(1L)).thenReturn(movie);

      Optional<Movie> result = movieService.findMovie(1L);

      Assertions.assertThat(result)
         .isPresent()
         .containsSame(movie)
      ;

      Mockito.verify(movieRepository).findOne(1L);
   }

   @Test
   public void testFindMovieById_NotFound()
   {
      Mockito.when(movieRepository.findOne(1L)).thenReturn(null);

      Optional<Movie> result = movieService.findMovie(1L);

      Assertions.assertThat(result)
         .isNotPresent()
      ;

      Mockito.verify(movieRepository).findOne(1L);
   }

   @Test
   public void testSave_Successful()
   {
      Genre genreOne = new Genre();
      Genre genreTwo = new Genre();
      List<Genre> genres = Arrays.asList(genreOne, genreTwo);
      Movie movie = new Movie();
      movie.setTitle("Movie Title");
      movie.setDescription("Movie");
      movie.setGenres(genres);

      Movie saved = new Movie();
      saved.setId(1L);
      saved.setTitle("Movie Title");
      saved.setDescription("Movie");
      saved.setGenres(genres);
      saved.setCreated(LocalDateTime.of(1955, 11, 5, 7, 22));
      saved.setUpdated(LocalDateTime.of(1985, 10, 26, 7, 22));

      Mockito.when(movieRepository.save(movie)).thenReturn(saved);

      Movie result = movieService.saveMovie(movie);

      Assertions.assertThat(result).isEqualTo(saved);
      Assertions.assertThat(result.getId()).isEqualTo(1L);

      Mockito.verify(genreService).saveAll(genres);

      InOrder inOrder = Mockito.inOrder(genreService, movieRepository);
      inOrder.verify(genreService, Mockito.calls(1)).saveAll(genres);
      inOrder.verify(movieRepository, Mockito.calls(1)).save(movie);
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void findMovieByTitle()
   {
      Pageable pageable = new PageRequest(0, 10);
      Genre genreOne = new Genre();
      Genre genreTwo = new Genre();
      List<Genre> genres = Arrays.asList(genreOne, genreTwo);
      Movie saved = new Movie();
      saved.setId(1L);
      saved.setTitle("Movie Title");
      saved.setDescription("Movie");
      saved.setGenres(genres);
      saved.setCreated(LocalDateTime.of(1955, 11, 5, 7, 22));
      saved.setUpdated(LocalDateTime.of(1985, 10, 26, 7, 22));

      Mockito.when(movieRepository.findByTitleContainingIgnoreCase("Movie Title", pageable)).thenReturn(Collections.singletonList(saved));

      List<Movie> result = movieService.findMovie("Movie Title", pageable);

      Assertions.assertThat(result)
         .containsOnly(saved)
      ;

      InOrder inOrder = Mockito.inOrder(genreService, movieRepository);

      inOrder.verify(genreService, Mockito.never()).saveAll(genres);
      inOrder.verify(movieRepository, Mockito.calls(1)).findByTitleContainingIgnoreCase("Movie Title", pageable);
      inOrder.verifyNoMoreInteractions();
   }
}
