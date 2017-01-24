package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.GenreRepository;
import com.github.movies.db.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developerSid on 1/12/17.
 *
 * Standard JPA based Movie Service
 */
@Service
public class JpaMovieService implements MovieService
{
   private final MovieRepository movieRepository;
   private final GenreRepository genreRepository;

   public JpaMovieService(@Autowired MovieRepository movieRepository, GenreRepository genreRepository)
   {
      this.movieRepository = movieRepository;
      this.genreRepository = genreRepository;
   }

   @Override @Transactional
   public Movie saveMovie(Movie movie)
   {
      Set<Genre> genres = new TreeSet<>(Comparator.comparing(Genre::getName)); //need them filtered by name not hash code
      genres.addAll(movie.getGenres());

      Set<Genre> existingGenres = genreRepository.findByNameIn(genres.stream().map(Genre::getName).collect(Collectors.toSet()));
      genres.removeAll(existingGenres);
      genreRepository.save(genres);

      movie.setGenres(genreRepository.findByNameIn(movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())));

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
