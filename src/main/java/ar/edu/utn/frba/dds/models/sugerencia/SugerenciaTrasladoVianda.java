package ar.edu.utn.frba.dds.models.sugerencia;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sugerencia_traslado_vianda")
public class SugerenciaTrasladoVianda extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "heladera_origen_id", nullable = false)
    private Heladera heladeraOrigen;

    @OneToMany
    @JoinColumn(name = "sugerencia_traslado_id")
    private List<Heladera> heladerasDestino;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoSugerencia estado;

    // TODO - Debería conocer al incidente, para la trazabilidiad
    // private Incidente incidente_causa;

    public static SugerenciaTrasladoVianda de(Heladera heladeraOrigen,
                                              List<Heladera> heladerasDestino,
                                              Colaborador colaborador,
                                              EstadoSugerencia estado) {
        return SugerenciaTrasladoVianda
                .builder()
                .heladeraOrigen(heladeraOrigen)
                .heladerasDestino(heladerasDestino)
                .colaborador(colaborador)
                .estado(estado)
                .build();
    }
}
