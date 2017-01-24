package com.github.movies.db

import com.github.movies.db.entity.Movie
import com.github.movies.db.loader.processor.LoadDirectorsProcessor
import com.github.movies.db.loader.processor.LoadMovieProcessor
import com.github.movies.db.service.GenreService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Created by developerSid on 1/11/17.
 *
 * Primary Entry point for the application
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
class Application
{
   private static final Logger logger = LoggerFactory.getLogger(Application)

   static void main(String[] args)
   {
      ConfigurableApplicationContext ac = SpringApplication.run(Application.class, args)

      try
      {
         LoadMovieProcessor loadMovieEventConsumer = ac.getBean(LoadMovieProcessor)
         LoadDirectorsProcessor loadDirectorsProcessor = ac.getBean(LoadDirectorsProcessor)
         GenreService genreService = ac.getBean(GenreService)

         [330459, 603, 10249, 9942, 154, 272, 137106, 11528, 284052, 1726].each {
            Movie movie = loadMovieEventConsumer.apply(it)
            println movie
            //movie = loadDirectorsProcessor.apply(movie)
         }

         genreService.loadAll(new PageRequest(0, 10)).each {println it}

      }
      catch(e)
      {
         logger.error("Error occurred during operation", e)
      }
      finally
      {
         ac.close()
      }
   }
}
