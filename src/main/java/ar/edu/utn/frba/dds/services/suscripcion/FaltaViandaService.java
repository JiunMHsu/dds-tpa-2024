package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FaltaViandaRepository;

import java.util.List;
import java.util.Optional;

public class FaltaViandaService {

    private final FaltaViandaRepository faltaViandaRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final HeladeraRepository heladeraRepository;



    public FaltaViandaService (FaltaViandaRepository faltaViandaRepository,
                               ColaboradorRepository colaboradorRepository,
                               HeladeraRepository heladeraRepository) {

        this.faltaViandaRepository = faltaViandaRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.heladeraRepository = heladeraRepository;
    }

    public void registrarSuscripcionFaltaVianda(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, Integer viandasRestantes) {

        Optional<Colaborador> colaboradorExistente = colaboradorRepository.buscarPorId(colaborador.getId().toString());
        if (colaboradorExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        Optional<Heladera> heladeraExistente = heladeraRepository.buscarPorId(heladera.getId().toString());
        if (heladeraExistente.isEmpty()) {
            throw new IllegalArgumentException("El colaborador no existe en la base de datos");
        }

        // un Colaborador se puede suscribir a una Heladera inactiva?

        if (viandasRestantes <= 0 || viandasRestantes > heladeraExistente.get().getCapacidad()) {
            throw new IllegalArgumentException("La cantidad de viandas restantes debe ser mayor a 0 y menor o igual a la capacidad máxima de la heladera");
        }

        SuscripcionFaltaVianda nuevaSuscripcion = SuscripcionFaltaVianda.de(
                colaborador,
                heladera,
                medioDeNotificacion,
                viandasRestantes);

        faltaViandaRepository.guardar(nuevaSuscripcion);
    }

    public List<SuscripcionFaltaVianda> buscarTodasLasSuscripcionesDe(Colaborador colaborador) {
        return faltaViandaRepository.obtenerPorColaborador(colaborador);
    }


}
