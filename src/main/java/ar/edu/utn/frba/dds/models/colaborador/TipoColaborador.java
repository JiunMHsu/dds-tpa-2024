package ar.edu.utn.frba.dds.models.colaborador;

import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;
import java.util.List;
import lombok.Getter;

@Getter
public class TipoColaborador {

  private TipoPersona tipo;
  private List<TipoColaboracion> colaboraciones;

  public TipoColaborador(TipoPersona tipo, List<TipoColaboracion> colaboraciones) {
    this.tipo = tipo;
    this.colaboraciones = colaboraciones;
  }
}
