package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.Size

/**
 * Created by developerSid on 1/12/17.
 *
 * Models a person can be anyone involved with a movie
 */
@Entity
@Sortable
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "credit", indexes = @Index(columnList = "name"))
class Credit extends Storable
{
   @Size(min = 2, max = 150)
   String name

   @Column(name = "tmdb_id")
   @JsonProperty(value = "id")
   int theMovieDBid

   @Size(min = 3, max = 150)
   String job

   @Size(min = 10, max = 100)
   String creditId

   Credit()
   {

   }

   Credit(String name, int theMovieDBid, String job, String creditId)
   {
      this.name = name
      this.theMovieDBid = theMovieDBid
      this.job = job
      this.creditId = creditId
   }
}
