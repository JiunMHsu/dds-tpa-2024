package ar.edu.utn.frba.dds.utils;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
public abstract class EntidadPersistente {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Setter
    @Column(name = "alta", nullable = false)
    private Boolean alta;

    @Setter
    @Column(name = "fecha_alta", columnDefinition = "DATE", nullable = false)
    private LocalDate fechaAlta;

    public EntidadPersistente() {
        this.alta = true;
        this.fechaAlta = LocalDate.now();
    }
}

