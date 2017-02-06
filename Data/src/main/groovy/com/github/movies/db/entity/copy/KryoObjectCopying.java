package com.github.movies.db.entity.copy;

import com.esotericsoftware.kryo.Kryo;
import org.springframework.stereotype.Service;

/**
 * Created by developerSid on 2/5/17.
 *
 * Implementation of the {@link ObjectCopying} that uses Kryo
 */
@Service("kryoObjectCopying")
public class KryoObjectCopying implements ObjectCopying
{
   private static final ThreadLocal<Kryo> kryos = ThreadLocal.withInitial(() ->
   {
      Kryo kryo = new Kryo();

      return kryo;
   });

   @Override
   public <T> T copy(T t)
   {
      Kryo kryo = kryos.get();

      return kryo.copy(t);
   }
}
