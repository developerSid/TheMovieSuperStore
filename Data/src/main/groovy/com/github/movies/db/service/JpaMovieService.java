package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by developerSid on 1/12/17.
 *
 * Standard JPA based Movie Service
 */
@Service
public class JpaMovieService implements MovieService
{
   private final MovieRepository movieRepository;
   private final GenreService genreService;

   @Autowired
   public JpaMovieService(MovieRepository movieRepository, GenreService genreService)
   {
      this.movieRepository = movieRepository;
      this.genreService = genreService;
   }

   @Override
   @Transactional
   public Movie saveMovie(Movie movie)
   {
      List<Genre> savedGenres = genreService.save(movie.getGenres());

      /*movie.setGenres(Collections.emptyList());
      movie = movieRepository.save(movie);*/
      movie.setGenres(savedGenres);

      return movieRepository.save(movie);
   }

   @Override @Transactional
   public List<Movie> findMovie(String title, Pageable pageable)
   {
      return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
   }

   @Override @Transactional
   public Movie findMovie(Long id)
   {
      return movieRepository.findOne(id);
   }
}
