package ar.edu.utn.frba.dds.utils;

import java.util.UUID;

/**
 * Manejador de mensajes del broker.
 */
public interface IBrokerMessageHandler {

  /**
   * Maneja la apertura de una heladera.
   *
   * @param temperatura temperatura de la heladera
   * @param heladeraId  Identificador de la heladera
   */
  void manejarTemperatura(double temperatura, UUID heladeraId);

  void manejarFraude(UUID heladeraId);

  void manejarFallaConexion(UUID heladeraId);

  void manejarSolicitudDeApertura(String codigoTarjeta, UUID heladeraId);
}
