package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;

public interface IBrokerMessageHandler {

    void recibirTemperatura(double temperatura, Heladera heladera);

    /**
     * En teoría llega un mensaje, pero se usa paraColaborador nada
     */
    void recibirMovimiento(Heladera heladera);

    void recibirFallaConexion(Heladera heladera);

    void recibirCodigoTarjeta(String codigoTarjeta);
}
