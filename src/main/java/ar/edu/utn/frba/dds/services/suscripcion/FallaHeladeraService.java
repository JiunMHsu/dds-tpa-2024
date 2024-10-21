package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
import java.util.List;
import java.util.Optional;

public class FallaHeladeraService {

    private final FallaHeladeraRepository fallaHeladeraRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final HeladeraRepository heladeraRepository;

    public FallaHeladeraService(FallaHeladeraRepository fallaHeladeraRepository,
                                ColaboradorRepository colaboradorRepository,
                                HeladeraRepository heladeraRepository) {

        this.fallaHeladeraRepository = fallaHeladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.heladeraRepository = heladeraRepository;
    }

    public void registrarSuscripcionFallaHeladera(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion) {

        Optional<Colaborador> colaboradorExistente = colaboradorRepository.buscarPorId(colaborador.getId().toString());
        if (colaboradorExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        Optional<Heladera> heladeraExistente = heladeraRepository.buscarPorId(heladera.getId().toString());
        if (heladeraExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        SuscripcionFallaHeladera nuevaSuscripcion = SuscripcionFallaHeladera.de(
                colaborador,
                heladera,
                medioDeNotificacion);

        fallaHeladeraRepository.guardar(nuevaSuscripcion);
    }

    public List<SuscripcionFallaHeladera> buscarTodasLasSuscripcionesDe(Colaborador colaborador) {
        return fallaHeladeraRepository.obtenerPorColaborador(colaborador);
    }

}
