package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.github.movies.db.json.deserializer.LocalDateJsonDeserialzier
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
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.Table
import javax.validation.constraints.Size
import java.time.LocalDate

/**
 * Created by developerSid on 1/11/17.
 *
 * Represents a movie
 */
@Entity
@Sortable(excludes = ["genres", "directors", "cast"])
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "movie", indexes = @Index(columnList = "title"))
@JsonIgnoreProperties(ignoreUnknown = true)
@NamedEntityGraph(
   name = "graph.movies.complete",
   attributeNodes = @NamedAttributeNode(value = "genres", subgraph = "genres")
)
class Movie extends Storable
{
   @Size(min = 2, max = 150) //TODO build up validation configuration
   String title

   @Lob
   @JsonProperty(value = "overview")
   String description

   @Column
   @JsonDeserialize(using = LocalDateJsonDeserialzier.class)
   @JsonProperty(value = "release_date")
   LocalDate releaseDate

   @Column(name = "tmdb_id")
   @JsonProperty(value = "id")
   int theMovieDBid

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_genre",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id")
   )
   List<Genre> genres = []

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_director",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "id")
   )
   List<Credit> directors = []

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "movie_cast",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "cast_id", referencedColumnName = "id")
   )
   List<Credit> cast = []

   Movie()
   {

   }

   Movie(String title, String description, int theMovieDBid, LocalDate releaseDate)
   {
      this(title, description, releaseDate, theMovieDBid, [], [])
   }

   Movie(String title, String description, LocalDate releaseDate, int theMovieDBid, List<Genre> genres, List<Credit> directors)
   {
      this.title = title
      this.description = description
      this.releaseDate = releaseDate
      this.theMovieDBid = theMovieDBid
      this.genres = genres
      this.directors = directors
   }

   @JsonProperty(value = "id")
   void setTheMovieDBid(int theMovieDBid)
   {
      this.theMovieDBid=theMovieDBid
   }
}
