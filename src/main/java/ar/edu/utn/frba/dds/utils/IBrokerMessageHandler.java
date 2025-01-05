package ar.edu.utn.frba.dds.utils;

import java.util.UUID;

public interface IBrokerMessageHandler {

  void manejarTemperatura(double temperatura, UUID heladeraId);

  void manejarFraude(UUID heladeraId);

  void manejarFallaConexion(UUID heladeraId);

  void manejarSolicitudDeApertura(String codigoTarjeta, UUID heladeraId);
}
