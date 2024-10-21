package ar.edu.utn.frba.dds.models.entities.reporte;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.incidente.TipoIncidente;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@Table(name = "reporte")
public class Reporte extends EntidadPersistente {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "path")
    private String path;

    public Reporte con(String titulo,
                         LocalDate fecha,
                         String path) {

        return Reporte
                .builder()
                .titulo(titulo)
                .fecha(fecha)
                .path(path)
                .build();
    }
    public Reporte con(String titulo,
                       LocalDate fecha) {

        return Reporte
                .builder()
                .titulo(titulo)
                .fecha(fecha)
                .build();
    }
}
