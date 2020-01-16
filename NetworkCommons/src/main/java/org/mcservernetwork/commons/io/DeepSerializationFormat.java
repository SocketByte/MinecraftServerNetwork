package org.mcservernetwork.commons.io;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Test class for deep serialization of non-serializable classes based on getters/setters
 * *heavily experimental and currently not used*
 */
public class DeepSerializationFormat {

    public static <T> WrappedObject<T> wrap(Class<T> clazz, Object nonSerializable) {
        WrappedObject<T> object = new WrappedObject<>(clazz);
        object.scan(nonSerializable);
        return object;
    }

    public static class WrappedObject<T> implements Serializable {
        private final Class<T> clazz;
        private final Class<?> superClazz;

        public WrappedObject(Class<T> clazz) {
            this.clazz = clazz;
            this.superClazz = this.clazz.getSuperclass();
        }

        private final Map<String, Object> getterValues = new HashMap<>();
        private final Map<String, String> getterSetterRelations = new HashMap<>();

        private void scan(Object instance) {
            try {
                for (PropertyDescriptor propertyDescriptor :
                        Introspector.getBeanInfo(this.clazz).getPropertyDescriptors()){

                    if (propertyDescriptor.getName().equals("class"))
                        continue;

                    Method getter = propertyDescriptor.getReadMethod();
                    Method setter = propertyDescriptor.getWriteMethod();

                    if (getter != null) {
                        String getterName = getter.getName();
                        Object invoked = getter.invoke(instance);

                        if (!(invoked instanceof Serializable)) {
                            WrappedObject<?> wrapped = new WrappedObject<>(invoked.getClass());
                            wrapped.scan(invoked);
                            this.getterValues.put(getterName, wrapped);
                        } else this.getterValues.put(getterName, invoked);
                        if (setter != null) {
                            String setterName = setter.getName();
                            this.getterSetterRelations.put(getterName, setterName);
                        }
                    }
                }
            } catch (IntrospectionException e) {
                throw new RuntimeException("An error occurred whilst scanning the wrapped object", e);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("An error occurred whilst invoking the getter", e);
            }
        }

        @SuppressWarnings("unchecked")
        public void unwrap(T instance) {
            for (Map.Entry<String, String> entry : this.getterSetterRelations.entrySet()) {
                Method getter;
                Method setter;
                try {
                    getter = this.clazz.getDeclaredMethod(entry.getKey());
                    setter = this.clazz.getDeclaredMethod(entry.getValue(), getter.getReturnType());
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Unknown getter and/or setter", e);
                }

                Object invoked = this.getterValues.get(getter.getName());
                if (!(invoked instanceof WrappedObject)) {
                    try {
                        setter.invoke(instance, invoked);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Could not invoke setter", e);
                    }
                    continue;
                }
                try {
                    WrappedObject<Object> wrapped = (WrappedObject<Object>) invoked;
                    Object original = getter.invoke(instance);
                    wrapped.unwrap(original);
                    setter.invoke(instance, original);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException("Could not invoke setter (wrapped getter)", e);
                }
            }
        }

        @Override
        public String toString() {
            return "WrappedObject{" +
                    "clazz=" + clazz +
                    ", superClazz=" + superClazz +
                    ", getterValues=" + getterValues +
                    ", getterSetterRelations=" + getterSetterRelations +
                    '}';
        }
    }

}
