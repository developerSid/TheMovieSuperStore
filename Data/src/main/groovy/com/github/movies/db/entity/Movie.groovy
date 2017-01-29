package com.github.movies.db.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.*
import javax.validation.constraints.Size
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
class Movie implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id

   @CreatedDate
   LocalDateTime created

   @LastModifiedDate
   LocalDateTime updated

   @Size(min = 2, max = 150) //TODO build up validation configuration
   String title

   @Lob
   @JsonProperty(value = "overview")
   String description

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

   @PrePersist
   private void onCreate() {
      created = LocalDateTime.now()
      updated = created
   }

   @PreUpdate
   private void onUpdate() {
      updated = LocalDateTime.now()
   }
}
