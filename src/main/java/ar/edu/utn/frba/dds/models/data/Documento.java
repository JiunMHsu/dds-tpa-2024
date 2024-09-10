package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Documento {

  @Column (name = "tipo_documento")
  private final TipoDocumento tipo;

  @Column (name = "numero")
  private final String numero;
  
  public Documento(String numero, TipoDocumento tipo) {
    this.numero = numero;
    this.tipo = tipo;
  }

  public Documento() {
    this.numero = null;
    this.tipo = null;
  }
}