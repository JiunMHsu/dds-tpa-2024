package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "heladera")
public class Heladera {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Embedded
    private Direccion direccion;

    @Column(name = "capacidad", columnDefinition = "SMALLINT", nullable = false)
    private Integer capacidad;

    @Setter
    @Column(name = "inicio_funcionamiento", columnDefinition = "DATE", nullable = false)
    private LocalDateTime inicioFuncionamiento;

    @Setter
    @Embedded
    private RangoTemperatura rangoTemperatura;

    @Setter
    @Column(name = "ultima_temperatura", columnDefinition = "DOUBLE")
    private Double ultimaTemperatura;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoHeladera estado;

    @Setter
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

    public static Heladera con(String nombre,
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

    public static Heladera con(String nombre, Direccion direccion, Integer capacidad) {
        return Heladera
                .builder()
                .nombre(nombre)
                .direccion(direccion)
                .capacidad(capacidad)
                .build();
    }

    public static Heladera con(Integer capacidad) {
        return Heladera
                .builder()
                .capacidad(capacidad)
                .build();
    }

    public void agregarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
        if (!this.puedeAgregarViandas(cantViandas)) {
            throw new ExcepcionCantidadDeViandas("La capacidad de la heladera esta excedida");
        }

        viandas += cantViandas;
    }

    public void quitarViandas(Integer cantViandas) throws ExcepcionCantidadDeViandas {
        if (!this.puedeQuitarViandas(cantViandas)) {
            throw new ExcepcionCantidadDeViandas("La heladera esta vacia");
        }

        viandas -= cantViandas;
    }

    public Boolean puedeAgregarViandas(Integer cantidad) {
        return (viandas + cantidad) < capacidad;
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

    public Tecnico tecnicoMasCercano(TecnicoRepository tecnicoRepository) {
        return tecnicoRepository.obtenerPorBarrio(direccion.getBarrio()).get(0);
//            .stream()
//            .min(Comparator.comparingDouble(tecnico -> tecnico.getAreaDeCobertura()
//            .calcularDistanciaAUbicacion(direccion.getUbicacion())))
//            .orElseThrow(() -> new RuntimeException("No se encontró ningún técnico."));
    }

    public List<Heladera> heladerasActivasMasCercanas(HeladeraRepository heladeraRepository) {
        return heladeraRepository.obtenerPorBarrio(direccion.getBarrio()).stream()
                .filter(Heladera::estaActiva)
                .toList();
    }

}