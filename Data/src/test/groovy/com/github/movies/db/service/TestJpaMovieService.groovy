package com.github.movies.db.service

import com.github.movies.db.entity.Movie
import com.github.movies.db.repository.MovieRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import spock.lang.Specification

/**
 * Created by developerSid on 1/22/17.
 */
class TestJpaMovieService extends Specification
{
   def 'test find movie by movie ID' ()
   {
      setup:
         MovieRepository movieRepository = Mock()
         MovieService movieService = new JpaMovieService(movieRepository)
         Movie resultMovie = new Movie('test title', 'test description', 1)
      when:
         Movie result = movieService.findMovie(1)
      then:
         1 * movieRepository.findOne(1) >> resultMovie
         result == new Movie('test title', 'test description', 1)
         result.is(resultMovie)
   }

   def 'test movie not found by ID' ()
   {
      setup:
         MovieRepository movieRepository = Mock()
         MovieService movieService = new JpaMovieService(movieRepository)
      when:
         Movie result = movieService.findMovie(1)
      then:
         1 * movieRepository.findOne(_) >> null
         result == null
   }

   def 'test successful save' ()
   {
      setup:
         MovieRepository movieRepository = Mock()
         MovieService movieService = new JpaMovieService(movieRepository)
         Movie saveMovie = new Movie(
            title: 'test title',
            description: 'test description'
         )
      when:
         Movie result = movieService.saveMovie(saveMovie)
      then:
         1 * movieRepository.save(saveMovie) >> new Movie('test title', 'test description', 1)
         !result.is(saveMovie)
         result == new Movie('test title', 'test description', 1)
   }

   def 'test find movie by title' ()
   {
      setup:
         MovieRepository movieRepository = Mock()
         MovieService movieService = new JpaMovieService(movieRepository)
         Pageable pageable = new PageRequest(0, 10)
      when:
         def results = movieService.findMovie('title', pageable)
      then:
         1 * movieRepository.findByTitleContainingIgnoreCase('title', pageable) >> [
            new Movie(
               title: 'title 1',
               description: 'title 1 description',
               id: 2
            ),
            new Movie(
               title: 'title 3',
               description: 'title 3 description',
               id: 5
            )
         ]
         results == [
            new Movie(
               title: 'title 1',
               description: 'title 1 description',
               id: 2
            ),
            new Movie(
               title: 'title 3',
               description: 'title 3 description',
               id: 5
            )
         ]
   }
}
