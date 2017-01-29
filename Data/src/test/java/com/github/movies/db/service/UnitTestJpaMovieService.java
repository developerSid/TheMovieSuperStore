package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

      Movie result = movieService.findMovie(1L).get();

      Assertions.assertThat(result)
         .isNotNull()
         .isEqualToComparingFieldByField(movie)
         .isSameAs(movie)
      ;

      Mockito.verify(movieRepository).findOne(1L);
   }

   @Test
   public void testFindMovieById_NotFound()
   {
      Mockito.when(movieRepository.findOne(1L)).thenReturn(null);

      Optional<Movie> result = movieService.findMovie(1L);

      Assert.assertFalse(result.isPresent());
      Mockito.verify(movieRepository).findOne(1L);
   }

   @Test
   public void testSave_Successful()
   {
      Genre genreOne = new Genre();
      Genre genreTwo = new Genre();
      List<Genre> genres = Arrays.asList(genreOne, genreTwo);
      Movie movie = new Movie();
      movie.setId(1L);
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

      Optional<Movie> result = movieService.saveMovie(movie);

      Assertions.assertThat(result)
         .isPresent()
         .containsSame(saved)
         .hasValueSatisfying(s -> {
            Assertions.assertThat(s.getId()).isEqualTo(1L);
            Assertions.assertThat(s.getTitle()).isEqualTo("Movie Title");
            Assertions.assertThat(s.getDescription()).isEqualTo("Movie");
            Assertions.assertThat(s.getCreated()).isEqualTo(LocalDateTime.of(1955, 11, 5, 7, 22));
            Assertions.assertThat(s.getUpdated()).isEqualTo(LocalDateTime.of(1985, 10, 26, 7, 22));
         })
      ;

      Mockito.verify(genreService).save(genres);
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

      Mockito.verify(movieRepository).findByTitleContainingIgnoreCase("Movie Title", pageable);
   }
}
