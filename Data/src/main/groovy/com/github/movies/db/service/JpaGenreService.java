package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.repository.GenreRepository;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by developerSid on 1/22/17.
 *
 * A JPA database based implemenation of {@link GenreService}
 */
@Service
public class JpaGenreService implements GenreService
{
   private final GenreRepository genreRepository;

   @Autowired
   public JpaGenreService(GenreRepository genreRepository)
   {
      this.genreRepository = genreRepository;
   }

   @Override
   @Transactional
   public List<Genre> saveAll(Collection<Genre> genres)
   {
      List<Genre> foundGenres = genreRepository.findByNameInOrderByNameAsc(genres.stream().map(Genre::getName).collect(Collectors.toList()));
      Collection<Genre> toSave = genres;

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

      if(!toSave.isEmpty())
      {
         return Streams.concat(
            Streams.stream(genreRepository.save(toSave)),
            foundGenres.stream()
         ).collect(Collectors.toList());
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
