package com.github.movies.db.loader.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by developerSid on 1/21/17.
 */
@Component
public class ApplicationConfiguration
{
   @Bean
   public RestTemplate restTemplate(RestTemplateBuilder builder)
   {
      return builder.build();
   }
}
