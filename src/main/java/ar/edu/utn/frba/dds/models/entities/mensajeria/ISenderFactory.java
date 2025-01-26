package ar.edu.utn.frba.dds.models.entities.mensajeria;

/**
 * Factory Interface for creating ISender objects.
 */
public interface ISenderFactory {
  ISender create();

  ISender create(MedioDeNotificacion medioDeNotificacion);
}
