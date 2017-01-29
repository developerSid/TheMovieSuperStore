package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.ManyToMany
import javax.persistence.Table

/**
 * Created by developerSid on 1/22/17.
 *
 * Entity container for the movie genre captured from The Movie DB
 */
@Entity
@Sortable(excludes = "movies")
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "genre", indexes = @Index(columnList = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
class Genre extends Storable
{
   @Column(unique = true)
   String name

   @JsonProperty(value = "id")
   int theMovieDBid

   @ManyToMany(mappedBy = "genres")
   List<Movie> movies

   @JsonProperty(value = "id")
   void setTheMovieDBid(int theMovieDBid)
   {
      this.theMovieDBid=theMovieDBid
   }
}
