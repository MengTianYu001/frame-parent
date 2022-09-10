package com.mty.property.common.support.kryo;

import com.esotericsoftware.kryo.Kryo;

public interface KryoFactory {
    Kryo createKryo();
}
