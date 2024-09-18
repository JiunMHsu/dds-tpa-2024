package ar.edu.utn.frba.dds.models.sugerencia;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sugerencia_traslado_vianda")
public class SugerenciaTrasladoVianda {
    // ¿Debería conocer al incidente también?

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

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
