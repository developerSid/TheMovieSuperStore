package com.github.movies.db.service;

import com.github.movies.TestConfiguration;
import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import com.google.common.collect.Streams;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

/**
 * Created by developerSid on 2/1/17.
 *
 * Functional test of the database functionality for the {@link JpaMovieService}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class FunctionalTestJpaMovieService
{
   @Autowired private TestEntityManager entityManager;
   @Autowired private JpaMovieService jpaMovieService;
   @Autowired private JdbcTemplate jdbc;

   @Test
   public void testFindById()
   {
      Movie persisted = entityManager.persist(
         new Movie(
            "test title",
            "test descritpion",
            1,
            LocalDate.of(1999, Month.FEBRUARY, 22)
         )
      );

      Optional<Movie> found = jpaMovieService.findMovie(persisted.getId());

      Assertions.assertThat(found).isPresent();
   }

   @Test
   public void testSave()
   {
      Movie toSave = new Movie("test title save", "test description save", 2, LocalDate.of(2000, Month.APRIL, 12));

      Movie saved = jpaMovieService.saveMovie(toSave);

      Assertions.assertThat(saved).isNotNull();

      Movie entityManagerSaved = entityManager.find(Movie.class, saved.getId());

      Assertions.assertThat(saved).isEqualTo(entityManagerSaved);
   }

   @Test
   public void testFindByTitle()
   {
      Movie movie1 = entityManager.persist(
         new Movie(
            "test title",
            "test description",
            1,
            LocalDate.of(1999, Month.FEBRUARY, 22)
         )
      );
      Movie movie2 = entityManager.persist(
         new Movie(
            "test title 2",
            "test description 2",
            1,
            LocalDate.of(2000, Month.APRIL, 12)
         )
      );
      entityManager.persist(
         new Movie(
            "movie 3",
            "movie 3 description 3",
            1,
            LocalDate.of(2001, Month.AUGUST, 12)
         )
      );

      PageRequest page = new PageRequest(0, 3);
      List<Movie> movies = jpaMovieService.findMovie("test title", page);

      Assertions.assertThat(movies)
         .hasSize(2)
         .containsExactly(movie1, movie2)
      ;
   }

   @Test
   public void testSavingGenres()
   {
      Movie movieOne = jpaMovieService.saveMovie(
         new Movie(
            "test title",
            "test description",
            LocalDate.of(1999, Month.FEBRUARY, 22),
            1,
            Arrays.asList(
               new Genre(
                  "Test Genre 1",
                  21
               ),
               new Genre(
                  "Test Genre 2",
                  22
               )
            )
         )
      );
      Movie movieTwo = jpaMovieService.saveMovie(
         new Movie(
            "test title 2",
            "test description2 ",
            LocalDate.of(2000, Month.APRIL, 12),
            2,
            Arrays.asList(
               new Genre(
                  "Test Genre 1",
                  21
               ),
               new Genre(
                  "Test Genre 3",
                  23
               )
            )
         )
      );
      Movie movieThree = jpaMovieService.saveMovie(
         new Movie(
            "movie 3",
            "movie 3 description 3",
            LocalDate.of(2001, Month.AUGUST, 12),
            3,
            Collections.singletonList(
               new Genre(
                  "Test Genre 1",
                  21
               )
            )
         )
      );

      List<Genre> genres = Stream.of(movieOne, movieTwo, movieThree)
         .flatMap(m -> m.getGenres().stream())
         .distinct()
         .collect(Collectors.toList())
      ;

      List<Genre> results = jdbc.query("SELECT id, name, the_movie_db_id FROM Genre", (rs, rowNum) ->
      {
         Genre toReturn = new Genre();

         toReturn.setId(rs.getLong("id"));
         toReturn.setName(rs.getString("name"));
         toReturn.setTheMovieDBid(rs.getInt("the_movie_db_id"));

         return toReturn;
      });


      Assertions.assertThat(results)
         .hasSize(3)
         .containsExactlyElementsOf(genres)
      ;
   }
}
