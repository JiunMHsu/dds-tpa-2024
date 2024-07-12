package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Heladera {

  private String nombre;
  private Direccion direccion;
  private LocalDate inicioFuncionamiento;
  private Integer capacidad;
  private RangoTemperatura rangoTemperatura;
  private Double ultimaTemperatura;
  private EstadoHeladera estado;
  private Integer viandas;

  public static Heladera with(String nombre,
                              Direccion direccion,
                              Integer capacidad,
                              RangoTemperatura rangoTemperatura,
                              EstadoHeladera estado) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .estado(estado)
        .viandas(0)
        .build();
  }

  public static Heladera with(String nombre,
                              Direccion direccion,
                              Integer capacidad,
                              RangoTemperatura rangoTemperatura) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .viandas(0)
        .build();
  }

  public static Heladera with(String nombre, Direccion direccion, Integer capacidad) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .viandas(0)
        .build();
  }

  public static Heladera with(Integer capacidad) {
    return Heladera
        .builder()
        .capacidad(capacidad)
        .viandas(0)
        .build();
  }

  public void agregarVianda() throws ExcepcionCapacidadExcedida {
    if (!this.puedeAgregarVianda()) {
      throw new ExcepcionCapacidadExcedida("La capacidad de la heladera esta excedida");
    }
    viandas += 1;
  }

  public void agregarViandas(Integer cantViandas) throws ExcepcionCapacidadExcedida {
    for (int i = 0; i < cantViandas; i++) {
      this.agregarVianda();
    }
  }

  public void quitarVianda() {
    viandas -= 1;
  }

  private Boolean puedeAgregarVianda() {
    return this.estaActiva() && (this.getViandas() < capacidad);
  }

  public Boolean estaActiva() {
    return estado == EstadoHeladera.ACTIVA;
  }

}