package com.github.movies.db.service;

import com.github.movies.db.entity.Credit;

import java.util.Optional;

/**
 * Created by developerSid on 1/12/17.
 *
 * Describes the operations provided for saving and loading people
 */
public interface CreditService
{
   Optional<Credit> save(Credit credit);
}
