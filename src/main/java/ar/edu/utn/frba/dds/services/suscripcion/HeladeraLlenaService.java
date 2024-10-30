package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.exceptions.SuscripcionHeladeraLlenaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionHeladeraLlena;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.HeladeraLlenaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class HeladeraLlenaService implements WithSimplePersistenceUnit {

    private final HeladeraLlenaRepository heladeraLlenaRepositoy;

    public HeladeraLlenaService(HeladeraLlenaRepository heladeraLlenaRepositoy) { this.heladeraLlenaRepositoy = heladeraLlenaRepositoy; }

    public void registrar(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, Integer espacioRestante) throws SuscripcionHeladeraLlenaException {

        if (espacioRestante < 0 || espacioRestante > heladera.getCapacidad())
            throw new SuscripcionHeladeraLlenaException("El espacio restante debe ser mayor o igual a 0 y menor a la capacidad máxima por la heladera");

        SuscripcionHeladeraLlena nuevaSuscripcion = SuscripcionHeladeraLlena.de(
                colaborador,
                heladera,
                medioDeNotificacion,
                espacioRestante);

        beginTransaction();
        heladeraLlenaRepositoy.guardar(nuevaSuscripcion);
        commitTransaction();
    }

}
