package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoIdealController;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.puntoIdeal.PuntoIdealService;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<String, Object> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (instances.containsKey(componentName))
            return (T) instances.get(componentName);

        if (componentName.equals(HeladeraController.class.getName())) {
            HeladeraController instance = new HeladeraController(
                    instanceOf(HeladeraService.class),
                    instanceOf(PuntoIdealService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(HeladeraService.class.getName())) {
            HeladeraService instance = new HeladeraService(
                    instanceOf(HeladeraRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(HeladeraRepository.class.getName())) {
            HeladeraRepository instance = new HeladeraRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(PuntoIdealController.class.getName())) {
            PuntoIdealController instance = new PuntoIdealController();
            instances.put(componentName, instance);
        }

        if (componentName.equals(PuntoIdealService.class.getName())) {
            PuntoIdealService instance = new PuntoIdealService();
            instances.put(componentName, instance);
        }

        return (T) instances.get(componentName);
    }

}
