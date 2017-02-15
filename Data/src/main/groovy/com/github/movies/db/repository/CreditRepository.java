package com.github.movies.db.repository;

import com.github.movies.db.entity.Credit;
import com.github.movies.db.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by developerSid on 1/12/17.
 *
 * The {@link Credit} repository
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>
{
   List<Credit> findByTheMovieDBidInOrderByNameAsc(Iterable<Integer> theMovieDBids);
}
