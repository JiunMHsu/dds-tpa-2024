package ar.edu.utn.frba.dds.models.entities.reporte;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reporte")
public class Reporte {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "path")
    private String path_pdf;
}
