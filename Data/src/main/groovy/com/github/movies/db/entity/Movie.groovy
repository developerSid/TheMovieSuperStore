package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.movies.db.json.deserializer.MovieReleaseDateDeserializer
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.Lob
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.validation.constraints.Size
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Created by developerSid on 1/11/17.
 *
 * Represents a movie
 */
@Entity
@Sortable(excludes = "genres")
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "movie", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
class Movie extends Storable
{
   @Size(min = 2, max = 150) //TODO build up validation configuration
   String title

   @Lob
   @JsonProperty(value = "overview")
   String description

   @Column
   @JsonDeserialize(using = MovieReleaseDateDeserializer.class)
   @JsonProperty(value = "release_date")
   LocalDate releaseDate

   @JsonProperty(value = "id")
   int theMovieDBid

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_genre",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
   List<Genre> genres

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
