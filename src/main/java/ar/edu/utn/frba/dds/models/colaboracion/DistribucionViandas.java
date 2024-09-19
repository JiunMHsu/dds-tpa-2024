package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "distribucion_viandas")
public class DistribucionViandas extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "heladera_origen_id", nullable = false)
    private Heladera origen;

    @ManyToOne
    @JoinColumn(name = "heladera_destino_id", nullable = false)
    private Heladera destino;

    @Column(name = "cant_viandas", columnDefinition = "SMALLINT", nullable = false)
    private Integer viandas;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    public static DistribucionViandas por(Colaborador colaboradorHumano,
                                          LocalDateTime fechaDistribucion,
                                          Heladera origen,
                                          Heladera destino,
                                          Integer viandas,
                                          String motivo) {
        return DistribucionViandas
                .builder()
                .colaborador(colaboradorHumano)
                .fechaHora(fechaDistribucion)
                .origen(origen)
                .destino(destino)
                .viandas(viandas)
                .motivo(motivo)
                .build();
    }

    public static DistribucionViandas por(Colaborador colaboradorHumano,
                                          LocalDateTime fechaDistribucion,
                                          Integer viandas) {
        return DistribucionViandas
                .builder()
                .colaborador(colaboradorHumano)
                .fechaHora(fechaDistribucion)
                .viandas(viandas)
                .build();
    }

}