package com.github.movies.db.service;

import com.github.movies.db.entity.Credit;
import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.CreditRepository;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
      this.creditRepository = creditRepository;
   }

   @Override
   public Credit save(Credit credit)
   {
      return creditRepository.save(credit);
   }

   @Override
   public List<Credit> saveAll(Collection<Credit> credits)
   {
      List<Credit> foundCredits = creditRepository.findByTheMovieDBidInOrderByNameAsc(credits.stream().map(Credit::getTheMovieDBid).collect(Collectors.toList()));
      Collection<Credit> toSave = credits;

      if(!foundCredits.isEmpty())
      {
         toSave = new ArrayList<>();

         for(Credit genre : credits)
         {
            if(Collections.binarySearch(foundCredits, genre, Comparator.comparing(Credit::getName)) < 0)
            {
               toSave.add(genre);
            }
         }
      }

      if(!toSave.isEmpty())
      {
         return Streams.concat(
            Streams.stream(creditRepository.save(toSave)),
            foundCredits.stream()
         ).collect(Collectors.toList());
      }
      else
      {
         return foundCredits;
      }
   }
}
