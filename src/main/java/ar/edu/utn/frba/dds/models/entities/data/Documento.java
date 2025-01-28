package ar.edu.utn.frba.dds.models.entities.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa un documento.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Documento {

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_documento")
  private TipoDocumento tipo;

  @Column(name = "numero")
  private String numero;

  /**
   * Constructor de un documento.
   *
   * @param tipoDocumento Tipo de documento.
   * @param nroDocumento  Número de documento.
   * @return Documento creado.
   */
  public static Documento con(TipoDocumento tipoDocumento, String nroDocumento) {

    return Documento
        .builder()
        .tipo(tipoDocumento)
        .numero(nroDocumento)
        .build();
  }
}