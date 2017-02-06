package com.github.movies.db.entity.copy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import org.hibernate.collection.internal.AbstractPersistentCollection;
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
      Kryo kryo = new Kryo()
      {
         @Override
         public Serializer getDefaultSerializer(Class type)
         {
            if(AbstractPersistentCollection.class.isAssignableFrom(type))
            {
               return new FieldSerializer(this, type);
            }

            return super.getDefaultSerializer(type);
         }
      };

      return kryo;
   });

   @Override
   public <T> T copy(T t)
   {
      Kryo kryo = kryos.get();

      return kryo.copy(t);
   }
}
