package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "heladera")
public class Heladera extends EntidadPersistente {

  @Column(name = "nombre", unique = true, nullable = false)
  private String nombre;

  @Embedded
  private Direccion direccion;

  @Column(name = "capacidad", columnDefinition = "SMALLINT", nullable = false)
  private Integer capacidad;

  @Column(name = "inicio_funcionamiento", columnDefinition = "DATE")
  private LocalDateTime inicioFuncionamiento;

  @Embedded
  private RangoTemperatura rangoTemperatura;

  @Column(name = "ultima_temperatura", columnDefinition = "DOUBLE")
  private Double ultimaTemperatura;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado", nullable = false)
  private EstadoHeladera estado;

  @Column(name = "cant_viandas", columnDefinition = "SMALLINT", nullable = false)
  private Integer viandas;

  public static Heladera con(String nombre,
                             Direccion direccion,
                             LocalDateTime inicioFuncionamiento,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             Double ultimaTemperatura,
                             EstadoHeladera estado,
                             Integer viandas) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .inicioFuncionamiento(inicioFuncionamiento)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .ultimaTemperatura(ultimaTemperatura)
        .estado(estado)
        .viandas(viandas)
        .build();
  }

  public static Heladera con(String nombre,
                             Direccion direccion,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             Integer viandas) {
    return Heladera.con(nombre, direccion, LocalDateTime.now(), capacidad, rangoTemperatura, null, EstadoHeladera.ACTIVA, viandas);
  }

  public static Heladera con(String nombre,
                             Direccion direccion,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             EstadoHeladera estado,
                             Integer viandas) {
    return Heladera.con(nombre, direccion, LocalDateTime.now(), capacidad, rangoTemperatura, null, estado, viandas);
  }

  public static Heladera con(String nombre,
                             Direccion direccion,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             EstadoHeladera estado) {
    return Heladera.con(nombre, direccion, LocalDateTime.now(), capacidad, rangoTemperatura, null, estado, 0);
  }

  public static Heladera con(String nombre) {
    return Heladera.con(nombre, null, LocalDateTime.now(), null, null, null, null, 0);
  }

  public static Heladera con(String nombre,
                             Direccion direccion,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura) {
    return Heladera.con(nombre, direccion, capacidad, rangoTemperatura, EstadoHeladera.ACTIVA);
  }

  public static Heladera con(Integer capacidad) {
    return Heladera.builder().capacidad(capacidad).build();
  }

  public void agregarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
    if (!this.puedeAgregarViandas(cantViandas))
      throw new ExcepcionCantidadDeViandas();
    viandas += cantViandas;
  }

  public void quitarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
    if (!this.puedeQuitarViandas(cantViandas))
      throw new ExcepcionCantidadDeViandas();
    viandas -= cantViandas;
  }

  public Boolean puedeAgregarViandas(Integer cantidad) {
    return (viandas + cantidad) <= capacidad;
  }

  public Boolean puedeQuitarViandas(Integer cantidad) {
    return (viandas - cantidad) >= 0;
  }

  public Boolean estaActiva() {
    return estado == EstadoHeladera.ACTIVA;
  }

  public Integer espacioRestante() {
    return capacidad - viandas;
  }

  public Boolean estaLlena() {
    return this.espacioRestante() == 0;
  }

  public Boolean admiteTemperatura(Double unaTemperatura) {
    return rangoTemperatura.incluye(unaTemperatura);
  }

}
