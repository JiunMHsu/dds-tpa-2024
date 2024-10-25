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

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "path", nullable = false)
    private String nombreArchivo;

    public static Reporte de(String titulo, LocalDate fecha, String nombreArchivo) {
        return Reporte.builder().titulo(titulo).fecha(fecha).nombreArchivo(nombreArchivo).build();
    }

    public static Reporte de(String titulo, String nombreArchivo) {
        return Reporte.de(titulo, LocalDate.now(), nombreArchivo);
    }
}
