package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FaltaViandaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class FaltaViandaService implements WithSimplePersistenceUnit {

    private final FaltaViandaRepository faltaViandaRepository;


    public FaltaViandaService(FaltaViandaRepository faltaViandaRepository) { this.faltaViandaRepository = faltaViandaRepository; }

    public void registrar(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, Integer viandasRestantes) throws SuscripcionFaltaViandaException {

        if (viandasRestantes <= 0 || viandasRestantes > heladera.getCapacidad()) {
            throw new SuscripcionFaltaViandaException("La cantidad por viandas restantes debe ser mayor a 0 y menor o igual a la capacidad máxima por la heladera");
        }

        SuscripcionFaltaVianda nuevaSuscripcion = SuscripcionFaltaVianda.de(
                colaborador,
                heladera,
                medioDeNotificacion,
                viandasRestantes);

        beginTransaction();
        faltaViandaRepository.guardar(nuevaSuscripcion);
        commitTransaction();
    }

}
