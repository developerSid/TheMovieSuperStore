package com.github.movies.db.loader.aspect;

import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by developerSid on 1/20/17.around {@link com.github.movies.db.loader.services.RestfulTheMovieDBService} to limit
 * how much the MovieDB API is hit
 *
 * Aspect wrapper around the {@link org.springframework.web.client.RestTemplate} to reduce the chances of hitting a free
 * API too hard
 */
@Aspect
@Component
public class TheMovieDBServiceRateLimiterAspect
{
   private static final Logger logger = LoggerFactory.getLogger(TheMovieDBServiceRateLimiterAspect.class);
   private RateLimiter rateLimiter;

   public TheMovieDBServiceRateLimiterAspect()
   {
      this.rateLimiter = RateLimiter.create(8);
   }

   @Around("execution(public * org.springframework.web.client.RestTemplate.*(..))")
   public Object limit(ProceedingJoinPoint joinPoint) throws Throwable
   {
      logger.debug("Loading {} from The Movie DB using {}", Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining()), joinPoint.getSignature().getName());
      rateLimiter.acquire();

      return joinPoint.proceed();
   }
}
