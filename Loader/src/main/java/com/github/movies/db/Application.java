package com.github.movies.db;

import com.github.movies.db.entity.Movie;
import com.github.movies.db.loader.processor.LoadCreditsProcessor;
import com.github.movies.db.loader.processor.LoadMovieProcessor;
import com.github.movies.db.service.CreditService;
import com.github.movies.db.service.MovieService;
import java.util.Optional;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
         final MovieService movieService = ac.getBean(MovieService.class);
         final CreditService creditService = ac.getBean(CreditService.class);
         final LoadMovieProcessor loadMovieEventConsumer = ac.getBean(LoadMovieProcessor.class);
         final LoadCreditsProcessor loadCreditsProcessor = ac.getBean(LoadCreditsProcessor.class);

         logger.info("Loading movies");
         IntStream.of(330459, 603, 10249, 9942, 154, 272, 137106, 11528, 284052, 1726).boxed()
            .map(loadMovieEventConsumer)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(movieService::saveMovie)
            .map(loadCreditsProcessor)
            .map(creditService::saveAll)
            .map(movieService::saveMovie)
            .map(Movie::getTitle)
            .forEach(System.out::println)
         ;
         logger.info("Loaded all movies");
      }
      catch(Exception e)
      {
         logger.error("Error occurred during operation", e);
      }

   }
}
