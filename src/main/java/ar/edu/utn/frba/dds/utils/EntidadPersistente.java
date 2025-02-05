package ar.edu.utn.frba.dds.utils;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase abstracta que representa una entidad persistente.
 * Proporciona un identificador único (UUID), un estado de alta y una fecha de alta.
 * Utiliza las anotaciones de Lombok para generar automáticamente getters y setters.
 */
@Getter
@MappedSuperclass
public abstract class EntidadPersistente {

  @Id
  @GeneratedValue(generator = "uuid")
  @Column(name = "id", columnDefinition = "BINARY(16)")
  private UUID id;

  @Setter
  @Column(name = "alta", nullable = false)
  private Boolean alta;

  @Setter
  @Column(name = "fecha_alta", columnDefinition = "DATETIME", nullable = false)
  private LocalDateTime fechaAlta;

  /**
   * Constructor de la clase EntidadPersistente.
   * Inicializa el estado de alta a true y establece la fecha de alta
   * a la fecha y hora actuales.
   */
  public EntidadPersistente() {
    this.alta = true;
    this.fechaAlta = LocalDateTime.now();
  }
}

