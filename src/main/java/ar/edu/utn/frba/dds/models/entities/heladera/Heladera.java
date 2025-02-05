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

/**
 * Modelo heladera.
 */
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

  @Column(name = "inicio_funcionamiento", columnDefinition = "DATETIME")
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

  @Column(name = "broker_topic")
  private String brokerTopic;

  /**
   * Crea una heladera.
   *
   * @param nombre               nombre de la heladera
   * @param direccion            dirección de la heladera
   * @param inicioFuncionamiento fecha de inicio de funcionamiento
   * @param capacidad            capacidad de la heladera
   * @param rangoTemperatura     rango de temperatura de la heladera
   * @param ultimaTemperatura    ultima temperatura registrada
   * @param estado               estado de la heladera
   * @param viandas              cantidad de viandas
   * @param brokerTopic          tópico del broker
   * @return heladera
   */
  public static Heladera con(String nombre,
                             Direccion direccion,
                             LocalDateTime inicioFuncionamiento,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             Double ultimaTemperatura,
                             EstadoHeladera estado,
                             Integer viandas,
                             String brokerTopic) {
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
        .brokerTopic(brokerTopic)
        .build();
  }

  /**
   * Crea una heladera, ACTIVA por defecto.
   * Constructor usado en el seeder.
   *
   * @param nombre           nombre de la heladera
   * @param direccion        dirección de la heladera
   * @param capacidad        capacidad de la heladera
   * @param rangoTemperatura rango de temperatura de la heladera
   * @param brokerTopic      tópico del broker
   * @return heladera
   */
  public static Heladera con(String nombre,
                             Direccion direccion,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             Integer viandas,
                             String brokerTopic) {
    return Heladera.con(
        nombre,
        direccion,
        LocalDateTime.now(),
        capacidad,
        rangoTemperatura,
        null,
        EstadoHeladera.ACTIVA,
        viandas,
        brokerTopic);
  }

  /**
   * Crea una heladera con nombre.
   *
   * @param nombre nombre de la heladera
   * @return heladera
   */
  public static Heladera con(String nombre) {
    return Heladera.con(
        nombre,
        null,
        LocalDateTime.now(),
        null,
        null,
        null,
        null,
        0,
        "");
  }

  /**
   * Crea una heladera, INACTIVA por defecto.
   * Constructor para dar de alta una heladera.
   *
   * @param nombre           nombre de la heladera
   * @param direccion        dirección de la heladera
   * @param capacidad        capacidad de la heladera
   * @param rangoTemperatura rango de temperatura de la heladera
   * @param brokerTopic      tópico del broker
   * @return heladera
   */
  public static Heladera con(String nombre,
                             Direccion direccion,
                             Integer capacidad,
                             RangoTemperatura rangoTemperatura,
                             String brokerTopic) {
    return Heladera.con(
        nombre,
        direccion,
        null,
        capacidad,
        rangoTemperatura,
        null,
        EstadoHeladera.INACTIVA,
        0,
        brokerTopic);
  }

  /**
   * Crea una heladera.
   *
   * @param capacidad capacidad de la heladera
   * @return heladera
   */
  public static Heladera con(int capacidad) {
    return Heladera.builder().capacidad(capacidad).build();
  }

  /**
   * Agrega viandas a la heladera.
   * Si la cantidad de viandas a agregar supera la capacidad de la heladera, lanza una excepción.
   *
   * @param cantViandas cantidad de viandas
   * @throws CantidadDeViandasException excepción de cantidad de viandas
   */
  public void agregarViandas(int cantViandas) throws CantidadDeViandasException {
    if (!this.puedeAgregarViandas(cantViandas)) {
      throw new CantidadDeViandasException();
    }
    viandas += cantViandas;
  }

  /**
   * Quita vianda de la heladera.
   * Si la cantidad de viandas a quitar supera la cantidad de viandas en la heladera,
   * lanza una excepción.
   *
   * @param cantViandas cantidad de viandas
   * @throws CantidadDeViandasException excepción de cantidad de viandas
   */
  public void quitarViandas(int cantViandas) throws CantidadDeViandasException {
    if (!this.puedeQuitarViandas(cantViandas)) {
      throw new CantidadDeViandasException();
    }
    viandas -= cantViandas;
  }

  /**
   * Verifica si se pueden agregar viandas a la heladera.
   *
   * @param cantidad cantidad de viandas
   */
  public boolean puedeAgregarViandas(int cantidad) {
    return (viandas + cantidad) <= capacidad;
  }

  /**
   * Verifica si se pueden quitar viandas de la heladera.
   *
   * @param cantidad cantidad de viandas
   */
  public boolean puedeQuitarViandas(int cantidad) {
    return (viandas - cantidad) >= 0;
  }

  /**
   * Verifica si la heladera está activa.
   */
  public boolean estaActiva() {
    return this.estado.equals(EstadoHeladera.ACTIVA);
  }

  /**
   * Retorna el espacio restante en la heladera.
   */
  public int espacioRestante() {
    return capacidad - viandas;
  }

  /**
   * Verifica si la heladera está llena.
   */
  public boolean estaLlena() {
    return this.espacioRestante() == 0;
  }

  /**
   * Verifica si la heladera admite una temperatura.
   *
   * @param unaTemperatura temperatura
   */
  public boolean admiteTemperatura(double unaTemperatura) {
    return rangoTemperatura.incluye(unaTemperatura);
  }

}
