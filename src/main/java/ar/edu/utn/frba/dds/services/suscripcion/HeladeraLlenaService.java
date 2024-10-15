package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.HeladeraLlenaRepository;

import java.util.List;
import java.util.Optional;

public class HeladeraLlenaService {

    private final HeladeraLlenaRepository heladeraLlenaRepositoy;
    private final ColaboradorRepository colaboradorRepository;
    private final HeladeraRepository heladeraRepository;

    public HeladeraLlenaService (HeladeraLlenaRepository heladeraLlenaRepositoy,
                                 ColaboradorRepository colaboradorRepository,
                                 HeladeraRepository heladeraRepository) {

        this.heladeraLlenaRepositoy = heladeraLlenaRepositoy;
        this.colaboradorRepository = colaboradorRepository;
        this.heladeraRepository = heladeraRepository;
    }

    public void registrarSuscripcionHeladeraLlena(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, Integer espacioRestante) {

        Optional<Colaborador> colaboradorExistente = colaboradorRepository.buscarPorId(colaborador.getId().toString());
        if (colaboradorExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        Optional<Heladera> heladeraExistente = heladeraRepository.buscarPorId(heladera.getId().toString());
        if (heladeraExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        if (espacioRestante < 0 || espacioRestante > heladeraExistente.get().getCapacidad())
            throw new IllegalArgumentException("El espacio restante debe ser mayor o igual a 0 y menor a la capacidad máxima de la heladera");

        SuscripcionHeladeraLlena nuevaSuscripcion = SuscripcionHeladeraLlena.de(
                colaborador,
                heladera,
                medioDeNotificacion,
                espacioRestante);

        heladeraLlenaRepositoy.guardar(nuevaSuscripcion);
    }

    public List<SuscripcionHeladeraLlena> buscarTodasLasSuscripcionesDe(Colaborador colaborador) {
        return heladeraLlenaRepositoy.obtenerPorColaborador(colaborador);
    }
}
