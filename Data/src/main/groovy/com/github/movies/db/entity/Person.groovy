package com.github.movies.db.entity

import groovy.transform.EqualsAndHashCode
import groovy.transform.Sortable
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Table

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
class Person extends Storable
{
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
