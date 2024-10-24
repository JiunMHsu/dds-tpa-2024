package ar.edu.utn.frba.dds.models.entities.incidente;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "incidente")
public class Incidente extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "heladera_id", nullable = false)
    protected Heladera heladera;

    @Column(name = "fecha_hora", nullable = false)
    protected LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_incidente", nullable = false)
    protected TipoIncidente tipo;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Embedded
    private Imagen foto;

    private static Incidente con(Heladera heladera,
                                 LocalDateTime fechaHora,
                                 TipoIncidente tipo,
                                 Colaborador colaborador,
                                 String descripcion,
                                 Imagen foto) {

        return Incidente
                .builder()
                .heladera(heladera)
                .fechaHora(fechaHora)
                .tipo(tipo)
                .colaborador(colaborador)
                .descripcion(descripcion)
                .foto(foto)
                .build();
    }

    public static Incidente fallaTecnica(Heladera heladera,
                                         LocalDateTime fechaHora,
                                         Colaborador colaborador,
                                         String descripcion,
                                         Imagen foto) {
        return con(heladera, fechaHora, TipoIncidente.FALLA_TECNICA, colaborador, descripcion, foto);
    }

    public static Incidente fallaTemperatura(Heladera heladera,
                                             LocalDateTime fechaHora) {
        return con(heladera, fechaHora, TipoIncidente.FALLA_TEMPERATURA, null, null, null);
    }

    public static Incidente fallaConexion(Heladera heladera,
                                          LocalDateTime fechaHora) {
        return con(heladera, fechaHora, TipoIncidente.FALLA_CONEXION, null, null, null);
    }

    public static Incidente fraude(Heladera heladera,
                                   LocalDateTime fechaHora) {
        return con(heladera, fechaHora, TipoIncidente.FRAUDE, null, null, null);
    }

}
