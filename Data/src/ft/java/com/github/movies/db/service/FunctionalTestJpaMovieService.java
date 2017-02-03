package com.github.movies.db.service;

import com.github.movies.TestConfiguration;
import com.github.movies.db.entity.Movie;
import com.github.movies.db.repository.MovieRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
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
      Movie toSave = new Movie("test title save", "test descriptionsave", 2, LocalDate.of(2000, Month.APRIL, 12));

      Optional<Movie> saved = jpaMovieService.saveMovie(toSave);

      Assertions.assertThat(saved).isPresent();

      Movie entityManagerSaved = entityManager.find(Movie.class, saved.get().getId());

      Assertions.assertThat(saved).hasValue(entityManagerSaved);
   }

   @Test
   public void testFindByTitle()
   {
      Movie movie1 = entityManager.persist(
         new Movie(
            "test title",
            "test descritpion",
            1,
            LocalDate.of(1999, Month.FEBRUARY, 22)
         )
      );
      Movie movie2 = entityManager.persist(
         new Movie(
            "test title 2",
            "test descritpion 2",
            1,
            LocalDate.of(2000, Month.APRIL, 12)
         )
      );
      entityManager.persist(
         new Movie(
            "movie 3",
            "movie 3 descritpion 3",
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
      Assertions.fail("Finish");
      /*
      @Test testMovieAddingGenres()
   {
      Movie movie1 = jpaMovieService.saveMovie(
         new Movie(
            title: "test title",
            description: "test descritpion",
            theMovieDBid: 1,
            releaseDate: LocalDate.of(1999, Month.FEBRUARY, 22),
            genres: [
               new Genre(
                  name: "Test Genre 1",
                  theMovieDBid: 21
               ),
               new Genre(
                  name: "Test Genre 2",
                  theMovieDBid: 22
               )
            ]
         )
      )
      Movie movie2 = jpaMovieService.saveMovie(
         new Movie(
            title: "test title 2",
            description:  "test descritpion 2",
            theMovieDBid:  1,
            releaseDate:  LocalDate.of(2000, Month.APRIL, 12),
            genres: [
               new Genre(
                  name: "Test Genre 1",
                  theMovieDBid: 21
               ),
               new Genre(
                  name: "Test Genre 3",
                  theMovieDBid: 23
               )
            ]
         )
      )
      jpaMovieService.saveMovie(
         new Movie(
            title:  "movie 3",
            description:  "movie 3 descritpion 3",
            theMovieDBid:  1,
            releaseDate: LocalDate.of(2001, Month.AUGUST, 12),
            genres: [
               new Genre(
                  name: "Test Genre 1",
                  theMovieDBid: 21
               )
            ]
         )
      )

      def results = jdbc.query("SELECT id, name") { rs, row ->
         return [
            id: rs.getInt("id"),
            name: rs.getString("name")
         ]
      }

      Assertions.assertThat(results)
         .hasSize(3)
   }
       */
   }
}
