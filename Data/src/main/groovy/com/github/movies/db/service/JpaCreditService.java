package com.github.movies.db.service;

import com.github.movies.db.entity.Credit;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by developerSid on 1/12/17.
 *
 * Provides the JPA based Credit Service
 */
@Service
public class JpaCreditService implements CreditService
{
   private final CreditRepository creditRepository;

   public JpaCreditService(@Autowired CreditRepository creditRepository)
   {
      this.creditRepository=creditRepository;
   }

   @Override
   public Credit save(Credit credit)
   {
      return creditRepository.save(credit);
   }

   @Override
   public Movie saveAll(Movie movie)
   {
      return null;
   }
}
