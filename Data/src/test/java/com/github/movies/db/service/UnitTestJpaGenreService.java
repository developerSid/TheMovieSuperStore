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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

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

      List<Genre> result = genreService.saveAll(genres);

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

      Mockito.when(genreRepository.findByNameInOrderByNameAsc(Arrays.asList("one", "two"))).thenReturn(Collections.singletonList(savedGenreTwo));
      Mockito.when(genreRepository.save(Collections.singletonList(genreOne))).thenReturn(Collections.singletonList(savedGenreOne));

      List<Genre> result = genreService.saveAll(genres);

      Assertions.assertThat(result)
         .isNotEmpty()
         .isSorted()
         .containsOnly(savedGenreOne, savedGenreTwo)
      ;

      Mockito.verify(genreRepository).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));
      Mockito.verify(genreRepository).save(Collections.singletonList(genreOne));

      InOrder inOrder = Mockito.inOrder(genreRepository);

      inOrder.verify(genreRepository, Mockito.calls(1)).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));
      inOrder.verify(genreRepository, Mockito.calls(1)).save(Collections.singletonList(genreOne));
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void testSaveOnAllExistingAlready()
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

      Mockito.when(genreRepository.findByNameInOrderByNameAsc(Arrays.asList("one", "two"))).thenReturn(Arrays.asList(savedGenreOne, savedGenreTwo));

      List<Genre> result = genreService.saveAll(genres);

      Assertions.assertThat(result)
         .isNotEmpty()
         .isSorted()
         .containsOnly(savedGenreOne, savedGenreTwo)
      ;

      Mockito.verify(genreRepository).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));

      InOrder inOrder = Mockito.inOrder(genreRepository);

      inOrder.verify(genreRepository, Mockito.calls(1)).findByNameInOrderByNameAsc(Arrays.asList("one", "two"));
      inOrder.verify(genreRepository, Mockito.never()).save(Mockito.anyCollection());
      inOrder.verifyNoMoreInteractions();
   }

   @Test
   public void findAll()
   {
      Genre foundGenreOne = new Genre();
      foundGenreOne.setId(1L);
      foundGenreOne.setName("one");
      Genre foundGenreTwo = new Genre();
      foundGenreTwo.setId(2L);
      foundGenreTwo.setName("two");
      List<Genre> foundGenres = Arrays.asList(foundGenreOne, foundGenreTwo);
      PageRequest pageRequest = new PageRequest(0, 10);

      Mockito.when(genreRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(foundGenres));

      Page<Genre> result = genreService.loadAll(pageRequest);

      Assertions.assertThat(result)
         .isNotEmpty()
         .containsExactly(foundGenreOne, foundGenreTwo)
      ;

      Mockito.verify(genreRepository).findAll(pageRequest);

      InOrder inOrder = Mockito.inOrder(genreRepository);

      inOrder.verify(genreRepository).findAll(pageRequest);
      inOrder.verifyNoMoreInteractions();
   }
}
