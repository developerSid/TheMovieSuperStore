package com.github.movies.db.entity.converter;

import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by developerSid on 1/31/17.
 *
 * JPA converter for Java 8 Date time api {@link LocalDate} since JPA 2.1 does not support that type yet
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, java.sql.Date>
{
   @Override
   public java.sql.Date convertToDatabaseColumn(LocalDate date)
   {
      return java.sql.Date.valueOf(date);
   }
   @Override
   public LocalDate convertToEntityAttribute(java.sql.Date date)
   {
      return date == null ? null : date.toLocalDate();
   }
}
