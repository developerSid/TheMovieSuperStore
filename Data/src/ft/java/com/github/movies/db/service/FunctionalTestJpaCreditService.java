package com.github.movies.db.service;

import com.github.movies.TestConfiguration;
import com.github.movies.db.entity.Credit;
import com.github.movies.db.entity.Storable;
import com.github.movies.db.repository.CreditRepository;
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
 * Created by developerSid on 2/11/17.
 *
 * Tests the functionality of the {@link JpaCreditService} using an in memory database and Spring's test harness
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = TestConfiguration.class)
public class FunctionalTestJpaCreditService
{
   @Autowired private JpaCreditService jpaCreditService;
   @Autowired private CreditRepository creditRepository;

   @Test
   public void testSavingCredits()
   {
      List<Credit> savedCredits = jpaCreditService.saveAll(
         Arrays.asList(
            new Credit("William Shatner", 1, "Captain James T Kirk", "FG56dk789IEIJOFIJEOIJ909"),
            new Credit("Leonard Nimoy", 2, "Command Spock", "RT5ikjilKAOFIJOFIJOEAFIEJOIJ"),
            new Credit("DeForest Kelley", 3, "Dr Leonard McCoy", "UKIOOLD489283AFJIAFJIELIJFEJF"),
            new Credit("Nicholas Meyer", 4, "Director", "ASDFG890999JFIEJFOIJEOIJ909")
         )
      );

      Assertions.assertThat(savedCredits).hasSize(4);
      Assertions.assertThat(savedCredits.get(0)).hasNoNullFieldsOrProperties();
      Assertions.assertThat(savedCredits.get(0).getId()).isGreaterThan(0);
      Assertions.assertThat(savedCredits.get(1).getId()).isGreaterThan(savedCredits.get(0).getId());
      Assertions.assertThat(savedCredits.get(2).getId()).isGreaterThan(savedCredits.get(1).getId());
      Assertions.assertThat(savedCredits.get(3).getId()).isGreaterThan(savedCredits.get(2).getId());
   }

   @Test
   public void testSavingCreditsSomeExistSomeDoNot()
   {
      jpaCreditService.saveAll(
         Arrays.asList(
            new Credit("William Shatner", 1, "Captain James T Kirk", "FG56dk789DKDKFJIEIFJOIAIJ"),
            new Credit("Leonard Nimoy", 2, "Command Spock", "RT5ikjilKJIEIJFIE883009IDJFJF")
         )
      );

      List<Credit> savedCredits = jpaCreditService.saveAll(
         Arrays.asList(
            new Credit("William Shatner", 1, "Captain James T Kirk", "FG56dk789JDLAIFLIJEI99EJF"),
            new Credit("Leonard Nimoy", 2, "Command Spock", "RT5ikjilKLAIJFOEIJFELIJ899"),
            new Credit("DeForest Kelley", 3, "Dr Leonard McCoy", "UKIOOLD489283JFALIFEIJFIENIGJIJIE90")
         )
      );

      List<Credit> repoCredits = creditRepository.findAll(new PageRequest(0, 50)).getContent().stream().sorted(Comparator.comparing(Storable::getId)).collect(Collectors.toList());
      savedCredits = savedCredits.stream().sorted(Comparator.comparing(Storable::getId)).collect(Collectors.toList());

      Assertions.assertThat(repoCredits)
         .hasSize(3)
         .containsExactly(savedCredits.toArray(new Credit[3]))
      ;
      Assertions.assertThat(savedCredits.get(0).getId()).isGreaterThan(0);
      Assertions.assertThat(savedCredits.get(1).getId()).isGreaterThan(0);
      Assertions.assertThat(savedCredits.get(2).getId()).isGreaterThan(0);
   }
}
