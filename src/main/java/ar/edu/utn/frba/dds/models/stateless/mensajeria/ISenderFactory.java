package ar.edu.utn.frba.dds.models.stateless.mensajeria;

/**
 * Factory Interface for creating ISender objects.
 */
//TODO check mayuscula
public interface ISenderFactory {
  ISender create();

  ISender create(MedioDeNotificacion medioDeNotificacion);
}
