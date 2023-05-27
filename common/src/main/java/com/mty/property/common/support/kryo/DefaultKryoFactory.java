package com.mty.property.common.support.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

/**
 * @author mty
 * @since 2022/09/02 09:29
 **/
public class DefaultKryoFactory implements KryoFactory {
    @Override
    public Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
        kryo.setReferences(false);
        return null;
    }
}
