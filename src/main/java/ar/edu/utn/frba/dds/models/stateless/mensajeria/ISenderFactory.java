package ar.edu.utn.frba.dds.models.stateless.mensajeria;

/**
 * Factory Interface for creating ISender objects.
 */
public interface ISenderFactory {
  ISender create();

  ISender create(MedioDeNotificacion medioDeNotificacion);
}
