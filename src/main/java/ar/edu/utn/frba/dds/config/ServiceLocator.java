package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoIdealController;
import ar.edu.utn.frba.dds.controllers.usuario.UsuarioController;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.puntoIdeal.PuntoIdealService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
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
        if (componentName.equals(ColaboradorController.class.getName())) {
            ColaboradorController instance = new ColaboradorController(
                instanceOf(UsuarioService.class),
                instanceOf(ColaboradorService.class));
            instances.put(componentName, instance);
        }
        if (componentName.equals(ColaboradorService.class.getName())) {
            ColaboradorService instance = new ColaboradorService(
                instanceOf(ColaboradorRepository.class)
            );
            instances.put(componentName, instance);
        }
        if (componentName.equals(ColaboradorRepository.class.getName())) {
            ColaboradorRepository instance = new ColaboradorRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(UsuarioService.class.getName())) {
            UsuarioService instance = new UsuarioService(
                instanceOf(UsuarioRepository.class)
            );
            instances.put(componentName, instance);
        }
        if (componentName.equals(UsuarioRepository.class.getName())) {
            UsuarioRepository instance = new UsuarioRepository();
            instances.put(componentName, instance);
        }


        return (T) instances.get(componentName);
    }

}
