package com.github.movies.db.service.impl;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import com.github.movies.db.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by developerSid on 1/12/17.
 *
 * Standard JPA based Movie Service
 */
@Service
public class MovieServiceImpl implements MovieService
{
   private final MovieRepository movieRepository;

   public MovieServiceImpl(@Autowired MovieRepository movieRepository)
   {
      this.movieRepository = movieRepository;
   }

   @Override @Transactional
   public void saveMovie(Movie movie)
   {
      movieRepository.save(movie);
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
