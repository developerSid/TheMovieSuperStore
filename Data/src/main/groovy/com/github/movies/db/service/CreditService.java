package com.github.movies.db.service;

import com.github.movies.db.entity.Credit;

import com.github.movies.db.entity.Movie;

/**
 * Created by developerSid on 1/12/17.
 *
 * Describes the operations provided for saving and loading people
 */
public interface CreditService
{
   Credit save(Credit credit);
   Movie saveAll(Movie movie);
}
