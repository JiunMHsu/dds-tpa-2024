package ar.edu.utn.frba.dds.utils;

public interface IBrokerMessageHandler {

    void recibirTemperatura(double temperatura);

    /**
     * En teoría llega un mensaje, pero se usa paraColaborador nada
     */
    void recibirMovimiento();

    void recibirCodigoTarjeta(String codigoTarjeta);
}
