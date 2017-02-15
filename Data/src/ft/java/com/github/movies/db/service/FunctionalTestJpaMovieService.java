package com.github.movies.db.service;

import com.github.movies.TestConfiguration;
import com.github.movies.db.entity.Credit;
import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
   @Autowired private MovieRepository movieRepository;
   @Autowired private JpaMovieService jpaMovieService;
   @Autowired private JdbcTemplate jdbc;

   @Test
   public void testFindById()
   {
      Movie persisted = movieRepository.save(
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
      Movie toSave = new Movie("test title saveAll", "test description saveAll", 2, LocalDate.of(2000, Month.APRIL, 12));

      Movie saved = jpaMovieService.saveMovie(toSave);

      Assertions.assertThat(saved).isNotNull();

      Movie entityManagerSaved = movieRepository.findOne(saved.getId());

      Assertions.assertThat(saved).isEqualTo(entityManagerSaved);
   }

   @Test
   public void testFindByTitle()
   {
      Movie movie1 = movieRepository.save(
         new Movie(
            "test title",
            "test description",
            1,
            LocalDate.of(1999, Month.FEBRUARY, 22)
         )
      );
      Movie movie2 = movieRepository.save(
         new Movie(
            "test title 2",
            "test description 2",
            1,
            LocalDate.of(2000, Month.APRIL, 12)
         )
      );
      movieRepository.save(
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
            ),
            Collections.emptyList()
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
            ),
            Collections.emptyList()
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
            ),
            Collections.emptyList()
         )
      );

      List<Genre> genres = Stream.of(movieOne, movieTwo, movieThree)
         .flatMap(m -> m.getGenres().stream())
         .distinct()
         .collect(Collectors.toList())
      ;

      List<Genre> results = jdbc.query("SELECT id, name, tmdb_id FROM Genre", (rs, rowNum) ->
      {
         Genre toReturn = new Genre();

         toReturn.setId(rs.getLong("id"));
         toReturn.setName(rs.getString("name"));
         toReturn.setTheMovieDBid(rs.getInt("tmdb_id"));

         return toReturn;
      });


      Assertions.assertThat(results)
         .hasSize(3)
         .containsExactlyElementsOf(genres)
      ;
   }

   @Test
   public void testDirectorQuery()
   {
      Credit directorOne = new Credit("director prime", 1, "Director", "JDKJFOIJOIJKJFIJE88988080");
      Credit directorTwo = new Credit("director subprime", 1, "Director", "IEUFJIEFJOIJFE998U9898U44F");


      Stream.of(
         new Movie(
            "test title",
            "test description",
            LocalDate.of(1999, Month.FEBRUARY, 22),
            1,
            Collections.emptyList(),
            Collections.singletonList(directorOne)
         ),
         new Movie(
            "test title 2",
            "test description 2",
            LocalDate.of(2000, Month.APRIL, 12),
            1,
            Collections.emptyList(),
            Collections.singletonList(directorTwo)
         ),
         new Movie(
            "movie 3",
            "movie 3 description 3",
            LocalDate.of(2001, Month.AUGUST, 12),
            1,
            Collections.emptyList(),
            Collections.singletonList(directorOne)
         )
      ).forEach(jpaMovieService::saveMovie);

      List<Movie> movies = jpaMovieService.findByDirectorName("director prime");

      Assertions.assertThat(movies).hasSize(2);
      Assertions.assertThat(movies.get(0)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(movies.get(0).getTitle()).isEqualTo("test title");
      Assertions.assertThat(movies.get(1)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(movies.get(1).getTitle()).isEqualTo("movie 3");
   }
}
