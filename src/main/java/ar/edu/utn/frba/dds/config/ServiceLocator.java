package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
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
            HeladeraService heladeraService = instanceOf(HeladeraService.class);
            HeladeraController instance = new HeladeraController(heladeraService);
            instances.put(componentName, instance);
        }

        if (componentName.equals(HeladeraService.class.getName())) {
            HeladeraRepository heladeraRepository = instanceOf(HeladeraRepository.class);
            HeladeraService instance = new HeladeraService(heladeraRepository);
            instances.put(componentName, instance);
        }

        if (componentName.equals(HeladeraRepository.class.getName())) {
            HeladeraRepository instance = new HeladeraRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorController.class.getName())) {
            ColaboradorService colaboradorService = instanceOf(ColaboradorService.class);
            ColaboradorController instance = new ColaboradorController(colaboradorService);
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorService.class.getName())) {
            ColaboradorRepository colaboradorRepository = instanceOf(ColaboradorRepository.class);
            ColaboradorService instance = new ColaboradorService(colaboradorRepository);
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorRepository.class.getName())) {
            ColaboradorRepository instance = new ColaboradorRepository();
            instances.put(componentName, instance);
        }

        return (T) instances.get(componentName);
    }

}
