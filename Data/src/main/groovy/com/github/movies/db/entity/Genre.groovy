package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

/**
 * Created by developerSid on 1/22/17.
 *
 * Entity container for the movie genre captured from The Movie DB
 */
@Entity
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "genre", indexes = @Index(columnList = "name"))
@JsonIgnoreProperties(ignoreUnknown = true)
class Genre
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id

   @Column(unique = true)
   String name

   @JsonProperty(value = "id")
   int theMovieDBid

   @ManyToMany
   List<Movie> movies;

   @JsonProperty(value = "id")
   void setTheMovieDBid(int theMovieDBid)
   {
      this.theMovieDBid=theMovieDBid
   }
}
