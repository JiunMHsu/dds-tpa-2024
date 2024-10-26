package ar.edu.utn.frba.dds.config;

import ar.edu.utn.frba.dds.controllers.canjeDePuntos.CanjeDePuntosController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.ColaboracionController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DistribucionViandasController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DonacionDineroController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.DonacionViandaController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.HacerseCargoHeladeraController;
import ar.edu.utn.frba.dds.controllers.colaboraciones.OfertaProductosServiciosController;
import ar.edu.utn.frba.dds.controllers.colaborador.ColaboradorController;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.controllers.heladera.PuntoIdealController;
import ar.edu.utn.frba.dds.controllers.incidente.AlertaController;
import ar.edu.utn.frba.dds.controllers.incidente.FallaTecnicaController;
import ar.edu.utn.frba.dds.controllers.personaVulnerable.PersonaVulnerableController;
import ar.edu.utn.frba.dds.controllers.reporte.ReporteController;
import ar.edu.utn.frba.dds.controllers.session.SessionController;
import ar.edu.utn.frba.dds.controllers.tecnico.TecnicoController;
import ar.edu.utn.frba.dds.controllers.tecnico.VisitaTecnicoController;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.models.repositories.personaVulnerable.PersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.reporte.ReporteRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaPersonaVulnerableRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnico.VisitaTecnicoRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.models.repositories.vianda.ViandaRepository;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import ar.edu.utn.frba.dds.services.colaboraciones.ColaboracionService;
import ar.edu.utn.frba.dds.services.colaboraciones.DistribucionViandasService;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionDineroService;
import ar.edu.utn.frba.dds.services.colaboraciones.DonacionViandaService;
import ar.edu.utn.frba.dds.services.colaboraciones.HacerseCargoHeladeraService;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaboraciones.RepartoDeTarjetaService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.files.FileService;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import ar.edu.utn.frba.dds.services.incidente.IncidenteService;
import ar.edu.utn.frba.dds.services.personaVulnerable.PersonaVulnerableService;
import ar.edu.utn.frba.dds.services.puntoIdeal.PuntoIdealService;
import ar.edu.utn.frba.dds.services.reporte.ReporteService;
import ar.edu.utn.frba.dds.services.tarjeta.TarjetaPersonaVulnerableService;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.services.tecnico.VisitaTecnicoService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.RandomString;
import ar.edu.utn.frba.dds.utils.SafeMailSender;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {

    private static final Map<String, Object> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T instanceOf(Class<T> componentClass) {
        String componentName = componentClass.getName();

        if (instances.containsKey(componentName))
            return (T) instances.get(componentName);

        // =========================  UTILS =========================

        if (componentName.equals(RandomString.class.getName())) {
            RandomString instance = new RandomString();
            instances.put(componentName, instance);
        }

        if (componentName.equals(RegistroMovimiento.class.getName())) {
            RegistroMovimiento instance = RegistroMovimiento.getInstancia();
            instances.put(componentName, instance);
        }

        // ========================= CONTROLLERS =========================

        if (componentName.equals(SessionController.class.getName())) {
            SessionController instance = new SessionController(
                    instanceOf(UsuarioService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(HeladeraController.class.getName())) {
            HeladeraController instance = new HeladeraController(
                    instanceOf(HeladeraService.class),
                    instanceOf(PuntoIdealService.class),
                    instanceOf(IncidenteService.class));
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
                    instanceOf(FileService.class),
                    instanceOf(ColaboradorService.class),
                    instanceOf(UsuarioService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorController.class.getName())) {
            ColaboradorController instance = new ColaboradorController(
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class));
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

        if (componentName.equals(PuntoIdealController.class.getName())) {
            PuntoIdealController instance = new PuntoIdealController();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboracionController.class.getName())) {
            ColaboracionController instance = new ColaboracionController(
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class),
                    instanceOf(ColaboracionService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(DistribucionViandasController.class.getName())) {
            DistribucionViandasController instance = new DistribucionViandasController(
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class),
                    instanceOf(DistribucionViandasService.class),
                    instanceOf(HeladeraService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionDineroController.class.getName())) {
            DonacionDineroController instance = new DonacionDineroController(
                    instanceOf(DonacionDineroService.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionViandaController.class.getName())) {
            DonacionViandaController instance = new DonacionViandaController(
                    instanceOf(DonacionViandaService.class),
                    instanceOf(ViandaRepository.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(OfertaProductosServiciosController.class.getName())) {
            OfertaProductosServiciosController instance = new OfertaProductosServiciosController(
                    instanceOf(OfertaProductosServiciosService.class),
                    instanceOf(FileService.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(HacerseCargoHeladeraController.class.getName())) {
            HacerseCargoHeladeraController instance = new HacerseCargoHeladeraController(
                    instanceOf(HacerseCargoHeladeraService.class),
                    instanceOf(HeladeraService.class),
                    instanceOf(UsuarioService.class),
                    instanceOf(ColaboradorService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(TecnicoController.class.getName())) {
            TecnicoController instance = new TecnicoController(
                    instanceOf(TecnicoService.class),
                    instanceOf(UsuarioService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(VisitaTecnicoController.class.getName())) {
            VisitaTecnicoController instance = new VisitaTecnicoController(
                    instanceOf(VisitaTecnicoService.class),
                    instanceOf(TecnicoService.class),
                    instanceOf(HeladeraService.class),
                    instanceOf(UsuarioService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(ReporteController.class.getName())) {
            ReporteController instance = new ReporteController(
                    instanceOf(ReporteService.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(CanjeDePuntosController.class.getName())) {
            CanjeDePuntosController instance = new CanjeDePuntosController(
                    instanceOf(ColaboradorService.class),
                    instanceOf(CanjeDePuntosService.class),
                    instanceOf(OfertaProductosServiciosService.class));
            instances.put(componentName, instance);
        }

        // ========================= SERVICES =========================

        if (componentName.equals(FileService.class.getName())) {
            FileService instance = new FileService(instanceOf(RandomString.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(HeladeraService.class.getName())) {
            HeladeraService instance = new HeladeraService(
                    instanceOf(HeladeraRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(IncidenteService.class.getName())) {
            IncidenteService instance = new IncidenteService(
                    instanceOf(IncidenteRepository.class),
                    instanceOf(HeladeraRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorService.class.getName())) {
            ColaboradorService instance = new ColaboradorService(
                    instanceOf(ColaboradorRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(PuntoIdealService.class.getName())) {
            PuntoIdealService instance = new PuntoIdealService();
            instances.put(componentName, instance);
        }

        if (componentName.equals(UsuarioService.class.getName())) {
            UsuarioService instance = new UsuarioService(
                    instanceOf(UsuarioRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(PersonaVulnerableService.class.getName())) {
            PersonaVulnerableService instance = new PersonaVulnerableService(
                    instanceOf(PersonaVulnerableRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboracionService.class.getName())) {
            ColaboracionService instance = new ColaboracionService(
                    instanceOf(UsuarioRepository.class),
                    instanceOf(ColaboradorRepository.class),
                    instanceOf(DonacionViandaRepository.class),
                    instanceOf(DonacionDineroRepository.class),
                    instanceOf(DistribucionViandasRepository.class),
                    instanceOf(HacerseCargoHeladeraRepository.class),
                    instanceOf(OfertaDeProductosRepository.class),
                    instanceOf(RepartoDeTarjetasRepository.class),
                    new SafeMailSender(),
                    instanceOf(MensajeRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(RepartoDeTarjetaService.class.getName())) {
            RepartoDeTarjetaService instance = new RepartoDeTarjetaService(
                    instanceOf(RepartoDeTarjetasRepository.class),
                    instanceOf(PersonaVulnerableRepository.class),
                    instanceOf(TarjetaPersonaVulnerableRepository.class),
                    instanceOf(ColaboradorRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(DistribucionViandasService.class.getName())) {
            DistribucionViandasService instance = new DistribucionViandasService(
                    instanceOf(DistribucionViandasRepository.class),
                    instanceOf(HeladeraRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionDineroService.class.getName())) {
            DonacionDineroService instance = new DonacionDineroService(
                    instanceOf(DonacionDineroRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(OfertaProductosServiciosService.class.getName())) {
            OfertaProductosServiciosService instance = new OfertaProductosServiciosService(
                    instanceOf(OfertaDeProductosRepository.class)
            );
            instances.put(componentName, instance);
        }

        if (componentName.equals(TarjetaPersonaVulnerableService.class.getName())) {
            TarjetaPersonaVulnerableService instance = new TarjetaPersonaVulnerableService(
                    instanceOf(TarjetaPersonaVulnerableRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(TecnicoService.class.getName())) {
            TecnicoService instance = new TecnicoService(
                    instanceOf(TecnicoRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(VisitaTecnicoService.class.getName())) {
            VisitaTecnicoService instance = new VisitaTecnicoService(
                    instanceOf(VisitaTecnicoRepository.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(ReporteService.class.getName())) {
            ReporteService instance = ReporteService.de(
                    instanceOf(ReporteRepository.class),
                    instanceOf(DonacionViandaRepository.class),
                    instanceOf(IncidenteRepository.class),
                    instanceOf(RegistroMovimiento.class));
            instances.put(componentName, instance);
        }

        if (componentName.equals(CanjeDePuntosService.class.getName())) {
            CanjeDePuntosService instance = new CanjeDePuntosService(
                    instanceOf(DonacionDineroRepository.class),
                    instanceOf(DistribucionViandasRepository.class),
                    instanceOf(DonacionViandaRepository.class),
                    instanceOf(RepartoDeTarjetasRepository.class),
                    instanceOf(HacerseCargoHeladeraRepository.class),
                    instanceOf(CanjeDePuntosRepository.class));
            instances.put(componentName, instance);
        }

        // ========================= REPOSITORIES =========================

        if (componentName.equals(HeladeraRepository.class.getName())) {
            HeladeraRepository instance = new HeladeraRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(IncidenteRepository.class.getName())) {
            IncidenteRepository instance = new IncidenteRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ColaboradorRepository.class.getName())) {
            ColaboradorRepository instance = new ColaboradorRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(UsuarioRepository.class.getName())) {
            UsuarioRepository instance = new UsuarioRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(PersonaVulnerableRepository.class.getName())) {
            PersonaVulnerableRepository instance = new PersonaVulnerableRepository();
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

        if (componentName.equals(DistribucionViandasRepository.class.getName())) {
            DistribucionViandasRepository instance = new DistribucionViandasRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(HacerseCargoHeladeraRepository.class.getName())) {
            HacerseCargoHeladeraRepository instance = new HacerseCargoHeladeraRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(DonacionViandaRepository.class.getName())) {
            DonacionViandaRepository instance = new DonacionViandaRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(OfertaDeProductosRepository.class.getName())) {
            OfertaDeProductosRepository instance = new OfertaDeProductosRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ViandaRepository.class.getName())) {
            ViandaRepository instance = new ViandaRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(MensajeRepository.class.getName())) {
            MensajeRepository instance = new MensajeRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(TecnicoRepository.class.getName())) {
            TecnicoRepository instance = new TecnicoRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(VisitaTecnicoRepository.class.getName())) {
            VisitaTecnicoRepository instance = new VisitaTecnicoRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(ReporteRepository.class.getName())) {
            ReporteRepository instance = new ReporteRepository();
            instances.put(componentName, instance);
        }

        if (componentName.equals(CanjeDePuntosRepository.class.getName())) {
            CanjeDePuntosRepository instance = new CanjeDePuntosRepository();
            instances.put(componentName, instance);
        }

        return (T) instances.get(componentName);
    }

}
