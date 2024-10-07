package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<String, Object> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String className = componentClass.getName();

        // TODO

        return (T) instances.get(className);
    }

    public static ICrudViewsHandler getCrudViewsHandler(String handlerName) {
        if (instances.containsKey(handlerName)) {
            return (ICrudViewsHandler) instances.get(handlerName);
        }

        if (handlerName.equals("HeladeraController")) {
            // Buscar los repos que
            return new HeladeraController(null, null, null, null);
        }


        return (ICrudViewsHandler) instances.get(handlerName);
    }
}
