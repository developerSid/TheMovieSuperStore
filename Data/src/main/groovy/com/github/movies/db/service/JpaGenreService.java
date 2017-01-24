package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.repository.GenreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
   public Iterable<Genre> loadAll(Pageable pageable)
   {
      return genreRepository.findAll(pageable);
   }
}
