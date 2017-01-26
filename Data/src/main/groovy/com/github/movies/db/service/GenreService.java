package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * Created by developerSid on 1/22/17.
 */
public interface GenreService
{
   List<Genre> save(Collection<Genre> genres);
   Page<Genre> loadAll(Pageable pageable);
}
