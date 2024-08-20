package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Documento {
  private TipoDocumento tipo;
  private String numero;
  
  public Documento(String numero, TipoDocumento tipo) {
    this.numero = numero;
    this.tipo = tipo;
  }
}