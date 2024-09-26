package ar.edu.utn.frba.dds.models.entities.puntosDeColaboracion;

import lombok.Getter;

@Getter
public class VarianteCalculoDePuntos {

    private final Double donacionDinero;
    private final Double distribucionViandas;
    private final Double donacionVianda;
    private final Double repartoTarjeta;
    private final Double heladerasActivas;

    /**
     * Por el momento esta harcodeado, pero la idea sería
     * setear los valores leyendo de la DB, así cumple con
     * el requerimiento de ser dinámico y poder cambiar
     * de mes en mes.
     */

    // TODO - Crear una tabla donde se almacenen esos valores
    public VarianteCalculoDePuntos() {
        this.donacionDinero = 0.5;
        this.distribucionViandas = 1.0;
        this.donacionVianda = 1.5;
        this.repartoTarjeta = 2.0;
        this.heladerasActivas = 5.0;
    }
}
