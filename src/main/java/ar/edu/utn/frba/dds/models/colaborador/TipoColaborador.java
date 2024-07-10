package ar.edu.utn.frba.dds.models.colaborador;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import java.util.List;
import lombok.Getter;

@Getter
public class TipoColaborador {

  private TipoPersona tipo;
  private List<Colaboracion> colaboraciones;

  public TipoColaborador(TipoPersona tipo, List<Colaboracion> colaboraciones) {
    this.tipo = tipo;
    this.colaboraciones = colaboraciones;
  }
}
