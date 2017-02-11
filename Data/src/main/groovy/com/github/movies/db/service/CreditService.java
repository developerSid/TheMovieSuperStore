package com.github.movies.db.service;

import com.github.movies.db.entity.Credit;
import java.util.Collection;
import java.util.List;

/**
 * Created by developerSid on 1/12/17.
 *
 * Describes the operations provided for saving and loading people
 */
public interface CreditService
{
   Credit save(Credit credit);
   List<Credit> saveAll(Collection<Credit> credits);
}
