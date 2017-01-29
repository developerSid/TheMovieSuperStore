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
@Transactional //since movie contains a LOB, and Postgres stores LOBs across multiple storage subcomponents, we have to make anything that deals with Movie Transactional.
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
   public Optional<Movie> saveMovie(Movie movie)
   {
      List<Genre> savedGenres = genreService.save(movie.getGenres());

      movie.setGenres(savedGenres);

      return Optional.ofNullable(movieRepository.save(movie));
   }

   @Override
   public List<Movie> findMovie(String title, Pageable pageable)
   {
      return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
   }

   @Override
   public Optional<Movie> findMovie(Long id)
   {
      return Optional.ofNullable(movieRepository.findOne(id));
   }
}
