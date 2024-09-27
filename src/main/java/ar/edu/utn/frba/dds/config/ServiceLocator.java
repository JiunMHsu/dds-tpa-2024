package ar.edu.utn.frba.dds.config;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static Map<String, Object> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String className = componentClass.getName();

        // TODO

        return (T) instances.get(className);
    }
}
