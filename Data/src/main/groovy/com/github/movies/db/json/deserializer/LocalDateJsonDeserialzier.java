package com.github.movies.db.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by developerSid on 1/31/17.
 *
 * Simple {@link JsonDeserializer} for parsing out the release date from The Movie DB's API
 */
public class LocalDateJsonDeserialzier extends JsonDeserializer<LocalDate>
{
   private static final DateTimeFormatter releaseDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

   @Override
   public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
   {
      JsonNode node = p.getCodec().readTree(p);

      return LocalDate.parse(node.asText(), releaseDateFormatter);
   }
}
