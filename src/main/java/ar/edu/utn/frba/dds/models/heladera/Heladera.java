package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
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
  private LocalDate fechaInicioFuncionamiento;
  private Integer capacidad;
  private List<Vianda> contenido;
  private Double ultimaTemperatura;
  private RangoTemperatura rangoTemperatura;
  private EstadoHeladera estado;

  public static Heladera with(String nombre, Direccion direccion, Integer capacidad, RangoTemperatura rangoTemperatura, EstadoHeladera estado) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .estado(estado)
        .contenido(new ArrayList<>())
        .build();
  }

  public static Heladera with(String nombre, Direccion direccion, Integer capacidad, RangoTemperatura rangoTemperatura) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .rangoTemperatura(rangoTemperatura)
        .contenido(new ArrayList<>())
        .build();
  }

  public static Heladera with(String nombre, Direccion direccion, Integer capacidad) {
    return Heladera
        .builder()
        .nombre(nombre)
        .direccion(direccion)
        .capacidad(capacidad)
        .build();
  }

  public static Heladera with(Integer capacidad) {
    return Heladera
        .builder()
        .capacidad(capacidad)
        .contenido(new ArrayList<>())
        .build();
  }

  public void agregarVianda(Vianda vianda) throws CapacidadExcedidaException {
    if (!this.chequearCapacidad()) {
      throw new CapacidadExcedidaException("La capacidad de la heladera esta excedida");
    }
    contenido.add(vianda);
  }

  public void quitarVianda(Vianda vianda) throws ViandaNoEncontradaException {
    if (!contenido.remove(vianda)) {
      throw new ViandaNoEncontradaException("Vianda no existe en la heladera");
    }
  }

  private Boolean chequearCapacidad() {
    return contenido.size() < capacidad;
  }

  public Boolean verificarTemperatura(Double temperaturaActual) {
    ultimaTemperatura = temperaturaActual;

    Double maxima = this.rangoTemperatura.getTemperaturaMaxima();
    Double minima = this.rangoTemperatura.getTemperaturaMinima();
    return temperaturaActual >= minima && temperaturaActual <= maxima;
  }

  public Boolean estaActiva() {
    return estado == EstadoHeladera.Activa;
  }

}