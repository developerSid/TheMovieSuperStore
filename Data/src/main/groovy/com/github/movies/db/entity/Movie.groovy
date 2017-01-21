package com.github.movies.db.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Lob
import javax.persistence.Table

/**
 * Created by developerSid on 1/11/17.
 *
 * Represents a movie
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "movie", indexes = @Index(columnList = "title"))
class Movie implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   long id

   String title

   @Lob
   String description

   Movie()
   {

   }
   Movie(String title, String description)
   {
      this.title = title
      this.description = description
   }
}
