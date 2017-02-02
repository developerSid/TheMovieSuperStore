package com.github.movies.db.entity.converter;

import java.sql.Date;
import java.time.LocalDate;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by developerSid on 1/31/17.
 *
 * JPA converter for Java 8 Date time api {@link LocalDate} since JPA 2.1 does not support that type yet
 */
@Converter(autoApply = true)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date>
{
   @Override
   public Date convertToDatabaseColumn(LocalDate date)
   {
      return Date.valueOf(date);
   }
   @Override
   public LocalDate convertToEntityAttribute(Date date)
   {
      return date == null ? null : date.toLocalDate();
   }
}
