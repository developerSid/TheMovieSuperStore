package com.github.movies.db.service;

import com.github.movies.TestConfiguration;
import com.github.movies.db.entity.Genre;
import com.github.movies.db.entity.Storable;
import com.github.movies.db.repository.GenreRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by developerSid on 2/8/17.
 *
 * Functional tests of the {@link JpaGenreService}
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class FunctionalTestJpaGenreService
{
   @Autowired JpaGenreService jpaGenreService;
   @Autowired GenreRepository genreRepository;

   @Test
   public void testSavingGenres()
   {
      List<Genre> savedGenres = jpaGenreService.saveAll(
         Arrays.asList(
            new Genre("scary", 1),
            new Genre("scifi", 2)
         )
      );

      Assertions.assertThat(savedGenres).hasSize(2);
      Assertions.assertThat(savedGenres.get(0)).hasNoNullFieldsOrPropertiesExcept("movies");
      Assertions.assertThat(savedGenres.get(0).getId()).isGreaterThan(0);
      Assertions.assertThat(savedGenres.get(1).getId()).isGreaterThan(savedGenres.get(0).getId());
   }

   @Test
   public void testSavingGenresSomeExistSomeDoNot()
   {
      jpaGenreService.saveAll(
         Arrays.asList(
            new Genre("scary", 1),
            new Genre("scifi", 2)
         )
      );

      List<Genre> savedGenres = jpaGenreService.saveAll(
         Arrays.asList(
            new Genre("scary", 1),
            new Genre("scifi", 2),
            new Genre("Action", 3)
         )
      );

      List<Genre> repoGenres = genreRepository.findAll(new PageRequest(0, 50)).getContent().stream().sorted(Comparator.comparing(Storable::getId)).collect(Collectors.toList());
      savedGenres = savedGenres.stream().sorted(Comparator.comparing(Storable::getId)).collect(Collectors.toList());

      Assertions.assertThat(repoGenres)
         .hasSize(3)
         .containsExactly(savedGenres.toArray(new Genre[3]))
      ;
   }
}
