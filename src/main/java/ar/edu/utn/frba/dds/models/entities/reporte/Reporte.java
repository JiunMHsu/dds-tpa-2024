package ar.edu.utn.frba.dds.models.entities.reporte;

import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reporte")
public class Reporte extends EntidadPersistente {
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
