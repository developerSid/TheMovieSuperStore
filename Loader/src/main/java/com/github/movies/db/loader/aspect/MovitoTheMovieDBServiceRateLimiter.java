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
 * Created by developerSid on 1/20/17.around {@link com.github.movies.db.loader.services.MovitoTheMovieDBService} to limit
 * how much the MovieDB API is hit
 *
 * Wrapper
 */
@Aspect
@Component
public class MovitoTheMovieDBServiceRateLimiter
{
   private static final Logger logger = LoggerFactory.getLogger(MovitoTheMovieDBServiceRateLimiter.class);
   private RateLimiter rateLimiter;

   public MovitoTheMovieDBServiceRateLimiter()
   {
      this.rateLimiter = RateLimiter.create(8);
   }

   @Around("execution(public * com.github.movies.db.loader.services.MovitoTheMovieDBService.*(..))")
   public Object limit(ProceedingJoinPoint joinPoint) throws Throwable
   {
      logger.debug("Loading {} from The Movie DB using {}", Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining()), joinPoint.getSignature().getName());
      rateLimiter.acquire();

      return joinPoint.proceed();
   }
}
