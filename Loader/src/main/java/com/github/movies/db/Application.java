package com.github.movies.db;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.loader.processor.LoadMovieProcessor;
import com.github.movies.db.service.JpaMovieService;
import java.util.Optional;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by developerSid on 1/11/17.
 *
 * Primary Entry point for the application
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class Application
{
   private static final Logger logger = LoggerFactory.getLogger(Application.class);

   public static void main(String[] args)
   {
      SLF4JBridgeHandler.install();

      try(ConfigurableApplicationContext ac = SpringApplication.run(Application.class, args))
      {
         final LoadMovieProcessor loadMovieEventConsumer = ac.getBean(LoadMovieProcessor.class);
         final JpaMovieService jpaMovieService = ac.getBean(JpaMovieService.class);

         logger.info("Loading movies");
         IntStream.of(330459, 603, 10249, 9942, 154, 272, 137106, 11528, 284052, 1726).forEach(loadMovieEventConsumer::apply);
         logger.info("Loaded all movies");

         Optional<Movie> one = jpaMovieService.findMovie(1L);

         one.ifPresent(movie -> {
            movie.getGenres().forEach(genre -> {
               System.out.printf("name: %s", genre.getName());
            });
         });
      }
      catch(Exception e)
      {
         logger.error("Error occurred during operation", e);
      }

   }
}
