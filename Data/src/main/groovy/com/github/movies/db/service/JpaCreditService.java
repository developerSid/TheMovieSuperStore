package com.github.movies.db.service;

import com.github.movies.db.entity.Credit;
import com.github.movies.db.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
   public Optional<Credit> save(Credit credit)
   {
      return Optional.ofNullable(creditRepository.save(credit));
   }
}
