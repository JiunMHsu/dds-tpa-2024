package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.tarjeta.Tarjeta;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaColaborador;
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
  private LocalDate inicioFuncionamiento;
  private Integer capacidad;
  private RangoTemperatura rangoTemperatura;
  private Double ultimaTemperatura;
  private EstadoHeladera estado;
  private List<Vianda> contenido;

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
        .contenido(new ArrayList<>())
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
    if (!this.puedeAgregarVianda()) {
      throw new CapacidadExcedidaException("La capacidad de la heladera esta excedida");
    }
    contenido.add(vianda);
  }

  public void quitarVianda(Vianda vianda) throws ViandaNoEncontradaException {
    if (!contenido.remove(vianda)) {
      throw new ViandaNoEncontradaException("Vianda no existe en la heladera");
    }
  }

  private Boolean puedeAgregarVianda() {
    return this.estaActiva() && (contenido.size() < capacidad);
  }

  public Boolean estaActiva() {
    return estado == EstadoHeladera.ACTIVA;
  }

  // TODO
  public Boolean haySolicitudDeAperturaPor(TarjetaColaborador tarjeta) {
    // sería consultar si hay alguna solicitud para la tarjeta
    // dentro del rango de 3 horas
    return true;
  }
}