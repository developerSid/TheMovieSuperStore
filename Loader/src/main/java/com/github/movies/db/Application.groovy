package com.github.movies.db

import com.github.movies.db.entity.Movie
import com.github.movies.db.entity.Person
import com.github.movies.db.service.MovieService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * Created by developerSid on 1/11/17.
 *
 * Primary Entry point for the application
 */
@SpringBootApplication
@EnableTransactionManagement
class Application
{
   private static final Logger logger = LoggerFactory.getLogger(Application)

   static void main(String[] args)
   {
      ConfigurableApplicationContext ac = SpringApplication.run(Application.class, args)

      try
      {
         MovieService movieService = ac.getBean(MovieService)

         saveMovies(movieService)

         movieService.findMovie("Matrix", new PageRequest(0, 10, new Sort(new Sort.Order(Sort.Direction.ASC, "title").ignoreCase()))).each { movie ->
            println movie
         }

         System.out.printf("Movie: %s\n", movieService.findMovie(1L))
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

   private static void saveMovies(MovieService movieService)
   {
      movieService.saveMovie(new Movie("The Matrix", "Set in the 22nd century, The Matrix tells the story of a computer hacker who joins a group of underground insurgents fighting the vast and powerful computers who now rule the earth."));
      movieService.saveMovie(new Movie("The Matrix Reloaded", "Six months after the events depicted in The Matrix, Neo has proved to be a good omen for the free humans, as more and more humans are being freed from the matrix and brought to Zion, the one and only stronghold of the Resistance. Neo himself has discovered his superpowers including super speed, ability to see the codes of the things inside the matrix and a certain degree of pre-cognition. But a nasty piece of news hits the human resistance: 250,000 machine sentinels are digging to Zion and would reach them in 72 hours. As Zion prepares for the ultimate war, Neo, Morpheus and Trinity are advised by the Oracle to find the Keymaker who would help them reach the Source. Meanwhile Neo's recurrent dreams depicting Trinity's death have got him worried and as if it was not enough, Agent Smith has somehow escaped deletion, has become more powerful than before and has fixed Neo as his next target."));
      movieService.saveMovie(new Movie("Rogue One", "A rogue bad of resistance fighters unite for a mission to steal the Death Star plans and bring a new hope to the galaxy."));
   }
}
