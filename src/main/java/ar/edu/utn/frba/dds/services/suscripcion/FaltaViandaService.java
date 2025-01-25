package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.exceptions.SuscripcionFaltaViandaException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFaltaVianda;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FaltaViandaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FaltaViandaService implements WithSimplePersistenceUnit {

  private final FaltaViandaRepository faltaViandaRepository;
  private final IColaboradorRepository colaboradorRepository;


  public FaltaViandaService(FaltaViandaRepository faltaViandaRepository,
                            ColaboradorRepository colaboradorRepository) {
    this.faltaViandaRepository = faltaViandaRepository;
    this.colaboradorRepository = colaboradorRepository;
  }

  public void registrar(Colaborador colaborador, Heladera heladera, Integer viandasRestantes, MedioDeNotificacion medioDeNotificacion, String infoContacto) throws SuscripcionFaltaViandaException {

    if (colaborador.getContactos().isEmpty()) {
      List<Contacto> contactos = new ArrayList<>(Arrays.asList(Contacto.vacio()));
      colaborador.setContactos(contactos);
    }

    boolean contactoActualizado = false;

    if (colaborador.getContacto(medioDeNotificacion).isEmpty()) {
      colaborador.agregarContacto(Contacto.con(medioDeNotificacion, infoContacto));
      contactoActualizado = true;
    }

    if (viandasRestantes <= 0 || viandasRestantes > heladera.getCapacidad()) {
      throw new SuscripcionFaltaViandaException("La cantidad por viandas restantes debe ser mayor a 0 y menor o igual a la capacidad máxima por la heladera");
    }

    SuscripcionFaltaVianda nuevaSuscripcion = SuscripcionFaltaVianda.de(
        colaborador,
        heladera,
        medioDeNotificacion,
        viandasRestantes);

    if (contactoActualizado) {
      beginTransaction();
      colaboradorRepository.actualizar(colaborador);
      faltaViandaRepository.guardar(nuevaSuscripcion);
      commitTransaction();
    } else {
      beginTransaction();
      faltaViandaRepository.guardar(nuevaSuscripcion);
      commitTransaction();
    }
  }

}
