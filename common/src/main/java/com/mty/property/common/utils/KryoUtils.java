package com.mty.property.common.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.KryoDataInput;
import com.esotericsoftware.kryo.io.Output;
import com.mty.property.common.support.kryo.KryoFactory;

import java.io.ByteArrayInputStream;
import java.lang.ref.WeakReference;

/**
 * @author mty
 * @since 2022/08/29 11:10
 **/
public class KryoUtils {
    private static int INIT_BUFFER_SIZE = 512;
    private static KryoFactory kryoFactory = ServiceLoaderUtils.loadByOrder(KryoFactory.class);

    private static ThreadLocal<Object[]> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = kryoFactory.createKryo();
        Output output = new Output(INIT_BUFFER_SIZE, -1);
        WeakReference<Output> ref = new WeakReference<>(output);
        return new Object[]{kryo, ref};
    });

    public static Kryo getKryo() {
        Object[] kryoAndOutput = kryoThreadLocal.get();
        return (Kryo) kryoAndOutput[0];
    }

    public static byte[] serialize(Object object) {
        try {
            Object[] kryoAndOutput = kryoThreadLocal.get();
            Kryo kryo = (Kryo) kryoAndOutput[0];
            WeakReference<Output> ref = (WeakReference<Output>) kryoAndOutput[1];
            Output output = ref.get();
            if (output == null) {
                output = new Output(INIT_BUFFER_SIZE, 1);
                kryoAndOutput[1] = new WeakReference<>(output);
            }
            try {
                kryo.writeClassAndObject(output, object);
                return output.toBytes();
            } finally {
                output.close();
            }
        } catch (KryoException e) {
            throw new RuntimeException("kryo serialeze error", e);
        }
    }

    public static Object deserialize(byte[] buffer) {
        if (buffer == null || buffer.length == 0) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        Input input = new Input(in);
        Kryo kryo = (Kryo) kryoThreadLocal.get()[0];
        kryo.setClassLoader(Thread.currentThread().getContextClassLoader());
        return kryo.readClassAndObject(input);
    }

}
