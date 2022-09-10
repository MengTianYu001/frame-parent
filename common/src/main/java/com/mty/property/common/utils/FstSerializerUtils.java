package com.mty.property.common.utils;


import org.nustaq.serialization.FSTConfiguration;

/**
 * @author mty
 * @date 2022/08/29 11:04
 **/
public class FstSerializerUtils {
    private static final FSTConfiguration FST_CONFIGURATION = FSTConfiguration.createDefaultConfiguration();

    public static byte[] serialize(Object obj) {
        return FST_CONFIGURATION.asByteArray(obj);
    }

    public static Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return FST_CONFIGURATION.asObject(bytes);
        } catch (Exception e) {
            throw new RuntimeException("fst deserialize error", e);
        }
    }

    public static void register(Class cls) {
        FST_CONFIGURATION.registerClass(cls);
    }
}
