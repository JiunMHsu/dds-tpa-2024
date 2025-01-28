package ar.edu.utn.frba.dds.models.entities.canjeDePuntos;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Clase que representa una variante de puntos en el sistema.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "variante_puntos")
public class VarianteDePuntos {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "fecha_configuracion", columnDefinition = "DATE", nullable = false)
  private LocalDate fechaConfiguracion;

  @Column(name = "donacion_dinero", nullable = false)
  private double donacionDinero;

  @Column(name = "distribucion_viandas", nullable = false)
  private double distribucionViandas;

  @Column(name = "donacion_vianda", nullable = false)
  private double donacionVianda;

  @Column(name = "reparto_tarjeta", nullable = false)
  private double repartoTarjeta;

  @Column(name = "heladeras_activas", nullable = false)
  private double heladerasActivas;

  /**
   * Constructor de la clase VarianteDePuntos.
   *
   * @param fechaConfiguracion  Fecha de configuración de la variante.
   * @param donacionDinero      Porcentaje de donaciones de dinero.
   * @param distribucionViandas Porcentaje de distribución de viandas.
   * @param donacionVianda      Porcentaje de donaciones de viandas.
   * @param repartoTarjeta      Porcentaje de reparto de tarjeta.
   * @param heladerasActivas    Porcentaje de heladeras activas.
   */
  public VarianteDePuntos(LocalDate fechaConfiguracion,
                          double donacionDinero,
                          double distribucionViandas,
                          double donacionVianda,
                          double repartoTarjeta,
                          double heladerasActivas) {
    this.fechaConfiguracion = fechaConfiguracion;
    this.donacionDinero = donacionDinero;
    this.distribucionViandas = distribucionViandas;
    this.donacionVianda = donacionVianda;
    this.repartoTarjeta = repartoTarjeta;
    this.heladerasActivas = heladerasActivas;
  }
}
