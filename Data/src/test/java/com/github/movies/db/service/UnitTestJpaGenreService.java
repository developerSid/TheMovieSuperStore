package com.github.movies.db.service;

import com.github.movies.db.entity.Genre;
import com.github.movies.db.repository.GenreRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * Created by developerSid on 1/26/17.
 *
 * Basic Unit tests for the {@link JpaGenreService}
 */
public class UnitTestJpaGenreService
{
   private GenreRepository genreRepository;
   private JpaGenreService genreService;

   @Before
   public void before()
   {
      genreRepository = Mockito.mock(GenreRepository.class);
      genreService = new JpaGenreService(genreRepository);
   }

   @Test
   public void testSaveNonExisting()
   {
      Genre genreOne = new Genre();
      genreOne.setName("one");
      Genre genreTwo = new Genre();
      genreTwo.setName("two");
      List<Genre> genres = Arrays.asList(genreOne, genreTwo);

      Genre savedGenreOne = new Genre();
      savedGenreOne.setId(1L);
      savedGenreOne.setName("one");
      Genre savedGenreTwo = new Genre();
      savedGenreTwo.setId(2L);
      savedGenreTwo.setName("two");
      List<Genre> savedGenres = Arrays.asList(savedGenreOne, savedGenreTwo);

      Mockito.when(genreRepository.findByNameInOrderByNameAsc(Arrays.asList("one", "two"))).thenReturn(Collections.emptyList());
      Mockito.when(genreRepository.save(genres)).thenReturn(savedGenres);

      List<Genre> result = genreService.save(genres);

      Assertions.assertThat(result)
         .isNotEmpty()
         .isSorted()
         .containsOnly(savedGenreOne, savedGenreTwo)
      ;

      InOrder inOrder = Mockito.inOrder(genreRepository);

      inOrder.verify(genreRepository, Mockito.calls(1)).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));
      inOrder.verify(genreRepository, Mockito.calls(1)).save(genres);
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void testSaveOnSomeExisting()
   {
      Genre genreOne = new Genre();
      genreOne.setName("one");
      Genre genreTwo = new Genre();
      genreTwo.setName("two");
      List<Genre> genres = Arrays.asList(genreOne, genreTwo);

      Genre savedGenreOne = new Genre();
      savedGenreOne.setId(1L);
      savedGenreOne.setName("one");
      Genre savedGenreTwo = new Genre();
      savedGenreTwo.setId(2L);
      savedGenreTwo.setName("two");
      List<Genre> savedGenres = Arrays.asList(savedGenreOne, savedGenreTwo);

      Mockito.when(genreRepository.findByNameInOrderByNameAsc(Arrays.asList("one", "two"))).thenReturn(Collections.singletonList(savedGenreTwo));
      Mockito.when(genreRepository.save(Collections.singletonList(genreOne))).thenReturn(Collections.singletonList(savedGenreOne));

      List<Genre> result = genreService.save(genres);

      Assertions.assertThat(result)
         .isNotEmpty()
         .isSorted()
         .containsOnly(savedGenreOne, savedGenreTwo)
      ;

      Mockito.verify(genreRepository).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));
      Mockito.verify(genreRepository).save(Collections.singletonList(genreOne));

      InOrder inOrder = Mockito.inOrder(genreRepository);

      inOrder.verify(genreRepository, Mockito.calls(1)).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));
      inOrder.verify(genreRepository, Mockito.calls(1)).save(Collections.singleton(genreOne));
      inOrder.verifyNoMoreInteractions();
   }
}
