package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import java.time.LocalDate;
import java.util.Comparator;
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

  public static Heladera with(String nombre,
                              Direccion direccion,
                              LocalDate inicioFuncionamiento,
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
        .build();
  }

  public void agregarVianda() throws ExcepcionCantidadDeViandas {
    if (!this.puedeAgregarVianda()) {
      throw new ExcepcionCantidadDeViandas("La capacidad de la heladera esta excedida");
    }
    viandas += 1;

    // TODO (no deberia estar esto)
    RegistroMovimiento.agregarViandaPorHeladera(nombre);
  }

  public void agregarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
    for (int i = 0; i < cantViandas; i++) {
      this.agregarVianda();
    }
  }

  public void quitarVianda() throws ExcepcionCantidadDeViandas {
    if (!this.puedeQuitarVianda()) {
      throw new ExcepcionCantidadDeViandas("La heladera esta vacia");
    }
    viandas -= 1;

    // TODO (no deberia estar esto)
    RegistroMovimiento.quitarViandaPorHeladera(nombre);
  }

  public void quitarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
    for (int i = 0; i < cantViandas; i++) {
      this.quitarVianda();
    }
  }

  private Boolean puedeAgregarVianda() {
    return viandas < capacidad;
  }

  private boolean puedeQuitarVianda() {
    return viandas > 0;
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

  public Tecnico tecnicoMasCercano(List<Tecnico> listaTecnicos) {
    return listaTecnicos.stream()
        .min(Comparator.comparingDouble(tecnico -> tecnico.getAreaDeCobertura().calcularDistanciaAUbicacion(direccion.getUbicacion())))
        .orElseThrow(() -> new RuntimeException("No se encontró ningún técnico."));
  }

  public List<Heladera> heladerasActivasMasCercanas() {
    return HeladeraRepository.obtenerTodos().stream()
        .filter(Heladera::estaActiva)
        .sorted(Comparator.comparingDouble(heladera1 -> heladera1.getDireccion().getUbicacion().calcularDistanciaEntreUbicaciones(direccion.getUbicacion())))
        .toList();
  }

}