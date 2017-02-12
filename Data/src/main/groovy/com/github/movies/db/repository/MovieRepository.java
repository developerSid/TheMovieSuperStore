package com.github.movies.db.repository;

import com.github.movies.db.entity.Movie;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by developerSid on 1/11/17.
 *
 * The {@link Movie} repository
 */
@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long>
{
   List<Movie> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

   @EntityGraph(value = "graph.movies.complete", type = EntityGraphType.LOAD)
   Optional<Movie> findMovieById(Long id);

   @Query(value =
        "select m "
      + "from Movie m "
      + "   join m.directors c "
      + "where c.name = ?1")
   List<Movie> findAllMoviesByDirector(String directorName);
}
