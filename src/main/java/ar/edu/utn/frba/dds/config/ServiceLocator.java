package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.colaboraciones.DistribucionViandasController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DonacionDineroController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DonacionViandaController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.HacerseCargoHeladeraController;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoIdealController;
import ar.edu.utn.frba.dds.controllers.incidente.AlertaController;
import ar.edu.utn.frba.dds.controllers.incidente.FallaTecnicaController;
import ar.edu.utn.frba.dds.controllers.personaVulnerable.PersonaVulnerableController;
import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.models.repositories.vianda.ViandaRepository;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.personaVulnerable.PersonaVulnerableService;
import ar.edu.utn.frba.dds.services.puntoIdeal.PuntoIdealService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
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

        if (componentName.equals(SessionController.class.getName())) {
            SessionController instance = new SessionController(
                    instanceOf(UsuarioService.class)
            );
            instances.put(componentName, instance);
        }

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

        if (componentName.equals(AlertaController.class.getName())) {
            AlertaController instance = new AlertaController(
                    instanceOf(IncidenteService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(FallaTecnicaController.class.getName())) {
            FallaTecnicaController instance = new FallaTecnicaController(
                    instanceOf(IncidenteService.class),
                    instanceOf(HeladeraService.class),
                    instanceOf(ColaboradorService.class),
                    instanceOf(UsuarioService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(IncidenteService.class.getName())) {
            IncidenteService instance = new IncidenteService(
                    instanceOf(IncidenteRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(IncidenteRepository.class.getName())) {
            IncidenteRepository instance = new IncidenteRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorController.class.getName())) {
            ColaboradorService colaboradorService = instanceOf(ColaboradorService.class);
            UsuarioService usuarioService = instanceOf(UsuarioService.class);
            ColaboradorController instance = new ColaboradorController(usuarioService, colaboradorService);
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

        if (componentName.equals(PuntoIdealController.class.getName())) {
            PuntoIdealController instance = new PuntoIdealController();
            instances.put(componentName, instance);
        }

        if (componentName.equals(PuntoIdealService.class.getName())) {
            PuntoIdealService instance = new PuntoIdealService();
            instances.put(componentName, instance);
        }

        if (componentName.equals(UsuarioRepository.class.getName())) {
            UsuarioRepository instance = new UsuarioRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(PersonaVulnerableController.class.getName())) {
            PersonaVulnerableController instance = new PersonaVulnerableController(
                    instanceOf(PersonaVulnerableService.class),
                    instanceOf(RepartoDeTarjetaService.class),
                    instanceOf(TarjetaPersonaVulnerableService.class),
                    instanceOf(ColaboradorService.class),
                    instanceOf(UsuarioService.class));
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

        if (componentName.equals(PersonaVulnerableService.class.getName())) {
            PersonaVulnerableService instance = new PersonaVulnerableService(
                    instanceOf(PersonaVulnerableRepository.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(PersonaVulnerableRepository.class.getName())) {
            PersonaVulnerableRepository instance = new PersonaVulnerableRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(RepartoDeTarjetaService.class.getName())) {
            RepartoDeTarjetaService instance = new RepartoDeTarjetaService(
                    instanceOf(RepartoDeTarjetasRepository.class),
                    instanceOf(PersonaVulnerableRepository.class),
                    instanceOf(TarjetaPersonaVulnerableRepository.class),
                    instanceOf(ColaboradorRepository.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(RepartoDeTarjetasRepository.class.getName())) {
            RepartoDeTarjetasRepository instance = new RepartoDeTarjetasRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(TarjetaPersonaVulnerableRepository.class.getName())) {
            TarjetaPersonaVulnerableRepository instance = new TarjetaPersonaVulnerableRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(TarjetaPersonaVulnerableService.class.getName())) {
            TarjetaPersonaVulnerableService instance = new TarjetaPersonaVulnerableService(
                    instanceOf(TarjetaPersonaVulnerableRepository.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(DistribucionViandasController.class.getName())) {
            DistribucionViandasController instance = new DistribucionViandasController(
                    instanceOf(DistribucionViandasRepository.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class),
                    instanceOf(HeladeraService.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(DistribucionViandasRepository.class.getName())) {
            DistribucionViandasRepository instance = new DistribucionViandasRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(HacerseCargoHeladeraController.class.getName())) {
            HacerseCargoHeladeraController instance = new HacerseCargoHeladeraController(
                    instanceOf(HacerseCargoHeladeraRepository.class),
                    instanceOf(HeladeraService.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(HacerseCargoHeladeraRepository.class.getName())) {
            HacerseCargoHeladeraRepository instance = new HacerseCargoHeladeraRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionDineroController.class.getName())) {
            DonacionDineroController instance = new DonacionDineroController(
                    instanceOf(DonacionDineroService.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionDineroService.class.getName())) {
            DonacionDineroService instance = new DonacionDineroService(
                    instanceOf(DonacionDineroRepository.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionDineroRepository.class.getName())) {
            DonacionDineroRepository instance = new DonacionDineroRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionViandaController.class.getName())) {
            DonacionViandaController instance = new DonacionViandaController(
                    instanceOf(DonacionViandaRepository.class),
                    instanceOf(ViandaRepository.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionViandaRepository.class.getName())) {
            DonacionViandaRepository instance = new DonacionViandaRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ViandaRepository.class.getName())) {
            ViandaRepository instance = new ViandaRepository();
            instances.put(componentName, instance);
        }

        return (T) instances.get(componentName);
    }

}
