package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import org.springframework.data.domain.Pageable;

/**
 * Created by developerSid on 1/22/17.
 */
public interface GenreService
{
   Iterable<Genre> loadAll(Pageable pageable);
}
