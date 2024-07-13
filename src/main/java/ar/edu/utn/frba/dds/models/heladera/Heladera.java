package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.suscripcion.ISuscipcionMovimientoVianda;
import ar.edu.utn.frba.dds.models.suscripcion.SuscripcionFallaHeladera;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
  private List<ISuscipcionMovimientoVianda> observersMovimiento;
  private List<SuscripcionFallaHeladera> observersFalla;

  public static Heladera with(String nombre,
                              Direccion direccion,
                              LocalDate inicioFuncionamiento,
                              Integer capacidad,
                              RangoTemperatura rangoTemperatura,
                              Double ultimaTemperatura,
                              EstadoHeladera estado,
                              Integer viandas,
                              List<ISuscipcionMovimientoVianda> observersMovimiento,
                              List<SuscripcionFallaHeladera> observersFalla) {
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
        .observersMovimiento(observersMovimiento)
        .observersFalla(observersFalla)
        .build();
  }

  public static Heladera with(String nombre,
                              Direccion direccion,
                              Integer capacidad,
                              RangoTemperatura rangoTemperatura,
                              EstadoHeladera estado) {
    return Heladera
        .pointerSafeBuider()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .estado(estado)
        .build();
  }

  public static Heladera with(String nombre,
                              Direccion direccion,
                              Integer capacidad,
                              RangoTemperatura rangoTemperatura) {
    return Heladera
        .pointerSafeBuider()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .build();
  }

  public static Heladera with(String nombre, Direccion direccion, Integer capacidad) {
    return Heladera
        .pointerSafeBuider()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .build();
  }

  public static Heladera with(Integer capacidad) {
    return Heladera
        .pointerSafeBuider()
        .capacidad(capacidad)
        .build();
  }

  private static HeladeraBuilder pointerSafeBuider() {
    return Heladera
        .builder()
        .viandas(0)
        .observersMovimiento(new ArrayList<>())
        .observersFalla(new ArrayList<>());
  }

  public void agregarVianda() throws ExcepcionCapacidadExcedida {
    if (!this.puedeAgregarVianda()) {
      throw new ExcepcionCapacidadExcedida("La capacidad de la heladera esta excedida");
    }
    viandas += 1;
    this.notificarObserversMovimiento();
  }

  public void agregarViandas(Integer cantViandas) throws ExcepcionCapacidadExcedida {
    for (int i = 0; i < cantViandas; i++) {
      this.agregarVianda();
    }
  }

  public void quitarVianda() {
    // excepción para no quitar viandas que no existen (cantidad = 0)
    viandas -= 1;
    this.notificarObserversMovimiento();
  }

  private Boolean puedeAgregarVianda() {
    return this.estaActiva() && (this.getViandas() < capacidad);
  }

  public Boolean estaActiva() {
    return estado == EstadoHeladera.ACTIVA;
  }

  public void setEstadoDeFalla() {
    this.estado = EstadoHeladera.INACTIVA;
    this.observersFalla
        .parallelStream()
        .forEach(SuscripcionFallaHeladera::serNotificado);
  }

  private void notificarObserversMovimiento() {
    this.observersMovimiento
        .parallelStream()
        .forEach(observer -> observer.serNotificado(viandas));
  }

}