package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

/**
 * Created by developerSid on 1/11/17.
 *
 * Represents a movie
 */
@Entity
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "movie", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
class Movie implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id

   String title

   @Lob
   @JsonProperty(value = "overview")
   String description

   @JsonProperty(value = "id")
   int theMovieDBid

   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinTable(name = "movie_genre")
   Set<Genre> genres

   Movie()
   {

   }
   Movie(String title, String description, int theMovieDBid)
   {
      this.title = title
      this.description = description
      this.theMovieDBid = theMovieDBid
   }

   @JsonProperty(value = "id")
   void setTheMovieDBid(int theMovieDBid)
   {
      this.theMovieDBid=theMovieDBid
   }
}
