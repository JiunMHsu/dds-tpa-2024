package ar.edu.utn.frba.dds.mensajeria;

import ar.edu.utn.frba.dds.models.data.Contacto;

public interface INotificador {
  void enviarMensaje(String mensaje, Contacto contacto);
}
