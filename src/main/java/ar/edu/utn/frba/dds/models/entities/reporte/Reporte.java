package ar.edu.utn.frba.dds.models.entities.reporte;

import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public static Reporte con(String titulo, LocalDate fecha, String path) {

        return Reporte
                .builder()
                .titulo(titulo)
                .fecha(fecha)
                .path(path)
                .build();
    }

    public static Reporte con(String titulo,
                              LocalDate fecha) {

        return Reporte.con(titulo, fecha, null);
    }
}
