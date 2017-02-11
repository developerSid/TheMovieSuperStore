package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by developerSid on 1/22/17.
 */
public interface GenreService
{
   List<Genre> saveAll(Collection<Genre> genres);
   Page<Genre> loadAll(Pageable pageable);
}
