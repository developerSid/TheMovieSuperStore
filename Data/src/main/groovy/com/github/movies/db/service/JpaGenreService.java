package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.repository.GenreRepository;
import com.google.common.collect.Streams;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by developerSid on 1/22/17.
 */
@Service
public class JpaGenreService implements GenreService
{
   private final GenreRepository genreRepository;

   public JpaGenreService(GenreRepository genreRepository)
   {
      this.genreRepository = genreRepository;
   }

   @Override
   @Transactional
   public List<Genre> save(Collection<Genre> genres)
   {
      List<Genre> foundGenres = genreRepository.findByNameInOrderByNameAsc(genres.stream().map(Genre::getName).collect(Collectors.toSet()));
      Collection<Genre> toSave;

      if(!foundGenres.isEmpty())
      {
         toSave = new ArrayList<>();

         for(Genre genre : genres)
         {
            if(Collections.binarySearch(foundGenres, genre, Comparator.comparing(Genre::getName)) < 0)
            {
               toSave.add(genre);
            }
         }
      }
      else
      {
         toSave = genres;
      }

      if(!toSave.isEmpty())
      {
         return Streams.concat(Streams.stream(genreRepository.save(toSave)), foundGenres.stream()).collect(Collectors.toList());
      }
      else
      {
         return foundGenres;
      }
   }

   @Override
   public Page<Genre> loadAll(Pageable pageable)
   {
      return genreRepository.findAll(pageable);
   }
}
