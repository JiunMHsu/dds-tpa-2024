package ar.edu.utn.frba.dds.services.suscripcion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.suscripcion.FallaHeladeraRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;
import java.util.Optional;

public class FallaHeladeraService implements WithSimplePersistenceUnit {

    private final FallaHeladeraRepository fallaHeladeraRepository;
    private final IColaboradorRepository colaboradorRepository;

    public FallaHeladeraService(FallaHeladeraRepository fallaHeladeraRepository,
                                ColaboradorRepository colaboradorRepository) {
        this.fallaHeladeraRepository = fallaHeladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
    }

    public void registrar(Colaborador colaborador, Heladera heladera, MedioDeNotificacion medioDeNotificacion, String infoContacto) {

        Contacto contacto = colaborador.getContacto();

        if (contacto == null) {
            contacto = Contacto.vacio();
            colaborador.setContacto(contacto);
        }

        boolean contactoActualizado = false;

        switch (medioDeNotificacion) {
            case WHATSAPP:
                if (contacto.getWhatsApp() == null) {
                    contacto.setWhatsApp(infoContacto);
                    contactoActualizado = true;
                }
                break;
            case TELEGRAM:
                if (contacto.getTelegram() == null) {
                    contacto.setTelegram(infoContacto);
                    contactoActualizado = true;
                }
                break;
            case EMAIL:
                if (contacto.getEmail() == null) {
                    contacto.setEmail(infoContacto);
                    contactoActualizado = true;
                }
                break;
        }

        SuscripcionFallaHeladera nuevaSuscripcion = SuscripcionFallaHeladera.de(
                colaborador,
                heladera,
                medioDeNotificacion);

        if (contactoActualizado) {
            beginTransaction();
            colaboradorRepository.actualizar(colaborador);
            fallaHeladeraRepository.guardar(nuevaSuscripcion);
            commitTransaction();
        } else {
            beginTransaction();
            fallaHeladeraRepository.guardar(nuevaSuscripcion);
            commitTransaction();
        }
      }
}
