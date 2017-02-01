package com.github.movies.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by developerSid on 1/30/17.
 *
 * Entry point for the Spring Data REST application
 */
@SpringBootApplication
public class Application
{
   public static void main(String[] args)
   {
      SpringApplication.run(Application.class, args);
   }
}
