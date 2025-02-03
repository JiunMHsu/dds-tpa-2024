package ar.edu.utn.frba.dds.models.entities.heladera;

import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
     * Crea una heladera.
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
     * Crea una heladera.
     *
     * @param nombre           nombre de la heladera
     * @param direccion        dirección de la heladera
     * @param capacidad        capacidad de la heladera
     * @param rangoTemperatura rango de temperatura de la heladera
     * @param estado           estado de la heladera
     * @param brokerTopic      tópico del broker
     * @return heladera
     */
    public static Heladera con(String nombre,
                               Direccion direccion,
                               Integer capacidad,
                               RangoTemperatura rangoTemperatura,
                               EstadoHeladera estado,
                               String brokerTopic) {
        return Heladera.con(
                nombre,
                direccion,
                LocalDateTime.now(),
                capacidad,
                rangoTemperatura,
                null,
                estado,
                0,
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
     * Crea una heladera.
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
                capacidad,
                rangoTemperatura,
                EstadoHeladera.ACTIVA,
                brokerTopic);
    }

    /**
     * Crea una heladera.
     *
     * @param capacidad capacidad de la heladera
     * @return heladera
     */
    public static Heladera con(Integer capacidad) {
        return Heladera.builder().capacidad(capacidad).build();
    }

    /**
     * Agrega viandas a la heladera.
     * Si la cantidad de viandas a agregar supera la capacidad de la heladera, lanza una excepción.
     *
     * @param cantViandas cantidad de viandas
     * @throws CantidadDeViandasException excepción de cantidad de viandas
     */
    public void agregarViandas(Integer cantViandas) throws CantidadDeViandasException {
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
    public void quitarViandas(Integer cantViandas) throws CantidadDeViandasException {
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
    public Boolean puedeAgregarViandas(Integer cantidad) {
        return (viandas + cantidad) <= capacidad;
    }

    /**
     * Verifica si se pueden quitar viandas de la heladera.
     *
     * @param cantidad cantidad de viandas
     */
    public Boolean puedeQuitarViandas(Integer cantidad) {
        return (viandas - cantidad) >= 0;
    }

    /**
     * Verifica si la heladera está activa.
     */
    public Boolean estaActiva() {
        return estado == EstadoHeladera.ACTIVA;
    }

    /**
     * Retorna el espacio restante en la heladera.
     */
    public Integer espacioRestante() {
        return capacidad - viandas;
    }

    /**
     * Verifica si la heladera está llena.
     */
    public Boolean estaLlena() {
        return this.espacioRestante() == 0;
    }

    /**
     * Verifica si la heladera admite una temperatura.
     *
     * @param unaTemperatura temperatura
     */
    public Boolean admiteTemperatura(Double unaTemperatura) {
        return rangoTemperatura.incluye(unaTemperatura);
    }

    /**
     * Verifica si la heladera tiene n viandas o menos.
     *
     * @param N numero a comparar
     */
    public Boolean tieneNViandasOMenos(Integer N) {
        return this.viandas <= N;
    }

}
