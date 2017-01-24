package com.github.movies.db.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import javax.persistence.*

/**
 * Created by developerSid on 1/12/17.
 *
 * Models a person can be anyone involved with a movie
 */
@Entity
@EqualsAndHashCode
@ToString(includeNames = true, includeFields = true)
@Table(name = "person")
class Person
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
