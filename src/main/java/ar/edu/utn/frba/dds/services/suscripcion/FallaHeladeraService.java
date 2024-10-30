package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class FallaHeladeraService implements WithSimplePersistenceUnit {

    private final FallaHeladeraRepository fallaHeladeraRepository;

    public FallaHeladeraService(FallaHeladeraRepository fallaHeladeraRepository) { this.fallaHeladeraRepository = fallaHeladeraRepository; }

    public void registrar(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion) {

        SuscripcionFallaHeladera nuevaSuscripcion = SuscripcionFallaHeladera.de(
                colaborador,
                heladera,
                medioDeNotificacion);

        beginTransaction();
        fallaHeladeraRepository.guardar(nuevaSuscripcion);
        commitTransaction();
      }
}
