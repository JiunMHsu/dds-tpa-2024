package ar.edu.utn.frba.dds.models.puntosDeColaboracion;

import lombok.Getter;

@Getter
public class VarianteCalculoDePuntos {

  private Double donacionDinero;
  private Double distribucionViandas;
  private Double donacionVianda;
  private Double repartoTarjeta;
  private Double heladerasActivas;

  /**
   * Por el momento esta harcodeado, pero la idea sería
   * setear los valores leyendo de la DB, así cumple con
   * el requerimiento de ser dinámico y poder cambiar
   * de mes en mes.
   */
  public VarianteCalculoDePuntos() {
    this.donacionDinero = 0.5;
    this.distribucionViandas = 1.0;
    this.donacionVianda = 1.5;
    this.repartoTarjeta = 2.0;
    this.heladerasActivas = 5.0;
  }
}
