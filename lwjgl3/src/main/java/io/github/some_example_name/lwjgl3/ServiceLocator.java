package io.github.some_example_name.lwjgl3;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    /** Register a service in the DI container */
    public static <T> void register(Class<T> clazz, T instance) {
        services.put(clazz, instance);
    }

    /** Retrieve a service */
    public static <T> T get(Class<T> clazz) {
        return clazz.cast(services.get(clazz));
    }

    /** Clear all registered services */
    public static void clear() {
        services.clear();
    }
}
