package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.sensor.*;
import ar.edu.utn.frba.dds.models.suscripcion.ISuscipcionMovimientoVianda;
import ar.edu.utn.frba.dds.models.suscripcion.SuscripcionFallaHeladera;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.reportes.RegistroMovimiento;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
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

  private List<ISuscipcionMovimientoVianda> observersMovimientoVianda;
  private List<SuscripcionFallaHeladera> observersFalla;

  private String topicSensorTemperatura;
  private String topicSensorMovimiento;
  private String topicLectorTarjeta;

  // No se que tan correcto es
  private SensorTemperatura sensorTemperatura;
  private SensorMovimiento sensorMovimiento;
  private LectorTarjeta lectorTarjeta;

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
        .observersMovimientoVianda(observersMovimiento)
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
        .observersMovimientoVianda(new ArrayList<>())
        .observersFalla(new ArrayList<>());
  }

  public void inicializarSensores() {
    sensorTemperatura = new SensorTemperatura(this);
    sensorMovimiento = new SensorMovimiento(this);
    lectorTarjeta = new LectorTarjeta(this);
  }

  public void agregarVianda() throws ExcepcionCantidadDeViandas {
    if (!this.puedeAgregarVianda()) {
      throw new ExcepcionCantidadDeViandas("La capacidad de la heladera esta excedida");
    }
    viandas += 1;
    RegistroMovimiento.agregarViandaPorHeladera(nombre);
    this.notificarObserversMovimiento();
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
    RegistroMovimiento.quitarViandaPorHeladera(nombre);
    this.notificarObserversMovimiento();
  }

  public void quitarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
    for (int i = 0; i < cantViandas; i++) {
      this.quitarVianda();
    }
  }

  private Boolean puedeAgregarVianda() {
    return this.estaActiva() && (this.getViandas() < capacidad);
  }

  private boolean puedeQuitarVianda() {
    return this.estaActiva() && (viandas != 0);
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

  public void reanudarFuncionamiento() {
    this.estado = EstadoHeladera.ACTIVA;
  }

  private void notificarObserversMovimiento() {
    this.observersMovimientoVianda
        .parallelStream()
        .forEach(observer -> observer.serNotificado(viandas));
  }

  public void serSuscriptoPor(ISuscipcionMovimientoVianda suscriptor) {
    observersMovimientoVianda.add(suscriptor);
  }

  public void serSuscriptoPor(SuscripcionFallaHeladera suscriptor) {
    observersFalla.add(suscriptor);
  }

  public Integer espacioRestante() {
    return capacidad - viandas;
  }

  public Boolean estaLlena() {
    return this.espacioRestante() == 0;
  }

  public Tecnico tecnicoMasCercano(List<Tecnico> listaTecnicos) {
    return listaTecnicos.stream()
        .min(Comparator.comparingDouble(tecnico -> tecnico.getAreaDeCobertura().calcularDistanciaAUbicacion(direccion.getUbicacion())))
        .orElseThrow(() -> new RuntimeException("No se encontró ningún técnico."));
  }

  // TODO REFACTOR
  public List<Heladera> heladerasRecomendadasPorFalla() {
    List<Heladera> listaHeladerasActivasConEspacio = HeladeraRepository.obtenerTodos().stream()
        .filter(Heladera::estaActiva)
        .filter(heladera -> !heladera.estaLlena())
        .toList();

    List<Heladera> listaHeladerasOrdenadasPorCercania = listaHeladerasActivasConEspacio.stream()
        .sorted(Comparator.comparingDouble(heladera1 -> heladera1.getDireccion().getUbicacion().calcularDistanciaEntreUbicaciones(direccion.getUbicacion())))
        .toList();

    List<Heladera> heladerasSeleccionadas = new ArrayList<>();
    Integer cantViandasATransportar = viandas;
    for (int i = 0; cantViandasATransportar > 0; i++) {
      Heladera heladeraX = listaHeladerasOrdenadasPorCercania.get(i);
      heladerasSeleccionadas.add(heladeraX);
      cantViandasATransportar -= heladeraX.espacioRestante();
    }

    return heladerasSeleccionadas;
  }
}