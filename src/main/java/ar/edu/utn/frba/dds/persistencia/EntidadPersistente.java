package ar.edu.utn.frba.dds.persistencia;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

// TODO - Hacer heredar las entidades necesarias
@Getter
@MappedSuperclass
public abstract class EntidadPersistente {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private UUID id;

    @Setter
    @Column(name = "alta")
    private Boolean alta;

    @Setter
    @Column(name = "fecha_alta", columnDefinition = "DATE")
    private LocalDate fechaAlta;

    public EntidadPersistente() {
        this.alta = true;
        this.fechaAlta = LocalDate.now();
    }
}

