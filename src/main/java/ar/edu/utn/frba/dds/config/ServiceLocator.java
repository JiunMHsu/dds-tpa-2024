package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.repositories.heladera.AperturaHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.IHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.ISolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.RetiroDeViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.SolicitudDeAperturaRepository;
import ar.edu.utn.frba.dds.utils.IBrokerMessageHandler;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
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

    @SuppressWarnings("unchecked")
    public static ICrudViewsHandler getCrudViewsHandler(String handlerName) {
        ICrudViewsHandler handler = null;

        if (instances.containsKey(handlerName)) {
            handler = (ICrudViewsHandler) instances.get(handlerName);
        }

        if (handlerName.equals("HeladeraController")) {
            handler = new HeladeraController(
                    ServiceLocator.getHeladeraRepository(),
                    ServiceLocator.getSolicitudDeAperturaRepository(),
                    ServiceLocator.getCrudRepository("RetiroDeViandaRepository"),
                    ServiceLocator.getCrudRepository("AperturaHeladeraRepository")
            );
        }

        // ...

        if (handler == null) {
            throw new IllegalArgumentException("No handler found with name " + handlerName);
        }

        instances.put(handlerName, handler);
        return handler;
    }

    @SuppressWarnings("unchecked")
    public static IBrokerMessageHandler getBrokerMessageHandler() {
        String handlerName = "HeladeraController";

        if (instances.containsKey(handlerName)) {
            return (IBrokerMessageHandler) instances.get(handlerName);
        }

        IBrokerMessageHandler handler = new HeladeraController(
                ServiceLocator.getHeladeraRepository(),
                ServiceLocator.getSolicitudDeAperturaRepository(),
                ServiceLocator.getCrudRepository("RetiroDeViandaRepository"),
                ServiceLocator.getCrudRepository("AperturaHeladeraRepository")
        );
        instances.put(handlerName, handler);
        return handler;
    }

    public static ICrudRepository getCrudRepository(String repositoryName) {
        ICrudRepository repository = null;

        if (instances.containsKey(repositoryName)) {
            repository = (ICrudRepository) instances.get(repositoryName);
        }

        if (repositoryName.equals("AperturaHeladeraRepository")) {
            repository = new AperturaHeladeraRepository();
        }

        if (repositoryName.equals("RetiroDeViandaRepository")) {
            repository = new RetiroDeViandaRepository();
        }

        if (repositoryName.equals("SolicitudDeAperturaRepository")) {
            repository = new SolicitudDeAperturaRepository();
        }

        // ...

        if (repository == null)
            throw new IllegalArgumentException("No repository found with name " + repositoryName);

        instances.put(repositoryName, repository);
        return repository;
    }

    public static IHeladeraRepository getHeladeraRepository() {
        String repositoryName = "HeladeraRepository";

        if (instances.containsKey(repositoryName)) {
            return (IHeladeraRepository) instances.get(repositoryName);
        }

        IHeladeraRepository repository = new HeladeraRepository();
        instances.put(repositoryName, repository);
        return repository;
    }

    public static ISolicitudDeAperturaRepository getSolicitudDeAperturaRepository() {
        String repositoryName = "SolicitudDeAperturaRepository";

        if (instances.containsKey(repositoryName)) {
            return (ISolicitudDeAperturaRepository) instances.get(repositoryName);
        }

        ISolicitudDeAperturaRepository repository = new SolicitudDeAperturaRepository();
        instances.put(repositoryName, repository);
        return repository;
    }
}
