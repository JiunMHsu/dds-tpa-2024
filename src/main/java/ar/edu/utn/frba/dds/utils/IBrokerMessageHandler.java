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

  /**
   * Maneja un caso de fraude en una heladera.
   *
   * @param heladeraId Identificador de la heladera
   */
  void manejarFraude(UUID heladeraId);

  /**
   * Maneja una falla de conexión de una heladera.
   *
   * @param heladeraId Identificador de la heladera
   */
  void manejarFallaConexion(UUID heladeraId);

  /**
   * Maneja una solicitud de apertura de una heladera.
   *
   * @param codigoTarjeta Código de la tarjeta utilizada para la solicitud
   * @param heladeraId    Identificador de la heladera
   */
  void manejarSolicitudDeApertura(String codigoTarjeta, UUID heladeraId);
}
