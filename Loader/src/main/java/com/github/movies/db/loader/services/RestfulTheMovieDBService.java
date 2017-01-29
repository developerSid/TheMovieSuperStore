package com.github.movies.db.loader.services;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by developerSid on 1/20/17.
 *
 * Maps {@link TheMovieDBService} calls to the Movito API wrapper
 */
@Service
public class RestfulTheMovieDBService implements TheMovieDBService
{
   private final String apiKey;
   private final RestTemplate restTemplate;

   @Autowired
   public RestfulTheMovieDBService(@Value("${tmdb.api.key}") String apiKey, RestTemplate restTemplate)
   {
      this.apiKey = apiKey;
      this.restTemplate = restTemplate;
   }

   @Override
   public Optional<Movie> loadMovie(int id)
   {
      return Optional.ofNullable(
         restTemplate.getForObject(
            "https://api.themoviedb.org/3/movie/{movie_id}?api_key={api_key}&language=en-US",
            Movie.class,
            id,
            apiKey
         )
      );
   }

   @Override
   public List<Person> loadDirectors(Movie movie)
   {
      return Collections.emptyList();
   }
}
