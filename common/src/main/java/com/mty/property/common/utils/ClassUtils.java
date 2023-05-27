package com.mty.property.common.utils;

import aj.org.objectweb.asm.ClassReader;
import aj.org.objectweb.asm.ClassVisitor;
import aj.org.objectweb.asm.ClassWriter;
import aj.org.objectweb.asm.MethodVisitor;
import aj.org.objectweb.asm.Opcodes;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author mty
 * @since 2022/08/26 16:13
 **/
@UtilityClass
public class ClassUtils {
    private final Map<Pair<Class, Method>, String> configKeyCache = new ConcurrentHashMap<>();

    public static String configKey(Class targetType, Method method) {
        return configKeyCache.computeIfAbsent(Pair.of(targetType, method), classMethodPair -> {
            StringBuilder sb = new StringBuilder();
            Class type = classMethodPair.getKey();
            Method m = classMethodPair.getValue();
            sb.append(type.getSimpleName());
            sb.append('#').append(m.getName()).append('(');
            for (Type param : m.getGenericParameterTypes()) {
                param = TypeUtils.resolve(type, type, param);
                sb.append(TypeUtils.getRawType(param).getSimpleName()).append(',');
            }
            if (m.getParameterTypes().length > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            return sb.append(')').toString();
        });
    }

    private final ClassLoader CLASS_LOADER = org.springframework.util.ClassUtils.getDefaultClassLoader();

    Method defineclassByBytesMethod;

    static {
        try {
            defineclassByBytesMethod = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
            defineclassByBytesMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public static void defineClassByBytes(byte[] bytes) {
        defineclassByBytesMethod.invoke(CLASS_LOADER, bytes, 0, bytes.length);
    }

    @SneakyThrows
    public static void changeMethod(String className, String methodName, String methodDesc, Consumer<MethodVisitor> consumer) {
        InputStream stream = CLASS_LOADER.getResourceAsStream(className.replaceAll("\\.", "/") + ".class");
        if (stream == null) {
            throw new ClassNotFoundException(className);
        }
        ClassReader classReader = new ClassReader(stream);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor classAdapter = new ClassVisitor(Opcodes.ASM7, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
                if (!name.equals(methodName) || !desc.equals(methodDesc)) {
                    return methodVisitor;
                }
                return new MethodVisitor(Opcodes.ASM7, methodVisitor) {
                    @Override
                    public void visitCode() {
                        consumer.accept(mv);
                    }
                };

            }

        };
        classReader.accept(classAdapter, ClassReader.EXPAND_FRAMES);
        byte[] btyes = classWriter.toByteArray();
        defineClassByBytes(btyes);
    }
}
