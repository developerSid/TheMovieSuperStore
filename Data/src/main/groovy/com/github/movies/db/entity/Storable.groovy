package com.github.movies.db.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.*
import java.time.LocalDateTime

/**
 * Created by developerSid on 1/28/17.
 *
 * Contains the basic components of all the Entities described by the system.
 */
@MappedSuperclass
abstract class Storable implements Serializable
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id

   @CreatedDate
   LocalDateTime created

   @LastModifiedDate
   LocalDateTime updated

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