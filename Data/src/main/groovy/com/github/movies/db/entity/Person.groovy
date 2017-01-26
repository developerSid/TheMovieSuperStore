package com.github.movies.db.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString
import org.apache.commons.lang3.builder.CompareToBuilder

import javax.persistence.*

/**
 * Created by developerSid on 1/12/17.
 *
 * Models a person can be anyone involved with a movie
 */
@Entity
@Sortable
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "person")
class Person implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id

   String title

   String firstName

   String middleName

   String lastName

   Person()
   {

   }

   Person(String title, String firstName, String middleName, String lastName)
   {
      this.title = title
      this.firstName = firstName
      this.middleName = middleName
      this.lastName = lastName
   }
}
