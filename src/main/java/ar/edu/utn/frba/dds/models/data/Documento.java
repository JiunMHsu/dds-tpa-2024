package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Documento {
  private final TipoDocumento tipo;
  private final String numero;
  
  public Documento(String numero, TipoDocumento tipo) {
    this.numero = numero;
    this.tipo = tipo;
  }
}