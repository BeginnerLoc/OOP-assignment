package game_engine;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static final Map<Class<?>, Object> services = new HashMap<>();

    public static <T> void register(Class<T> clazz, T instance) {
        services.put(clazz, instance);
    }


    public static <T> T get(Class<T> clazz) {
        return clazz.cast(services.get(clazz));
    }


    public static void clear() {
        services.clear();
    }
}
