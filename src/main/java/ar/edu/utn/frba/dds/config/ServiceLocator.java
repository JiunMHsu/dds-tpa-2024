package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<String, Object> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (!instances.containsKey(componentName)) {
            if(componentName.equals(HeladeraController.class.getName())) {
                HeladeraController instance = new HeladeraController(instanceOf(HeladeraRepository.class));
                instances.put(componentName, instance);
            }
            else if (componentName.equals(HeladeraRepository.class.getName())) {
                HeladeraRepository instance = new HeladeraRepository();
                instances.put(componentName, instance);
            }
        }

        return (T) instances.get(componentName);
    }
}
