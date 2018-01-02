package me.javaroad.openapi.wechat.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.commons.lang3.ArrayUtils;

public class ReflectUtils {

    public static <T> Class<T> findParameterizedType(Class<?> clazz, int index) {
        Type parameterizedType = clazz.getGenericSuperclass();
        if (!(parameterizedType instanceof ParameterizedType)) {
            parameterizedType = clazz.getSuperclass().getGenericSuperclass();
        }
        if (!(parameterizedType instanceof  ParameterizedType)) {
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) parameterizedType).getActualTypeArguments();
        if (ArrayUtils.isEmpty(actualTypeArguments)) {
            return null;
        }
        return (Class<T>) actualTypeArguments[index];
    }
}
