package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import lombok.Getter;

@Getter
public class OfrecerProducto {
  private Colaborador colaborador;
  private String nombreOferta;
  private Integer puntosNecesarios;
  private Imagen imagen;

  public OfrecerProducto(Colaborador colaborador, String nombreOferta, Integer puntosNecesarios, Imagen imagen) {
    this.colaborador = colaborador;
    this.nombreOferta = nombreOferta;
    this.puntosNecesarios = puntosNecesarios;
    this.imagen = imagen;
  }
}
