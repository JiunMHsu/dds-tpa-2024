package ar.edu.utn.frba.dds.models.sugerencia;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SugerenciaTrasladoVianda {

  /**
   * ¿Debería conocer al incidente también?
   */

  private Heladera heladeraOrigen;
  private List<Heladera> heladerasDestino;
  private Colaborador colaborador;
  private EstadoSugerencia estado;

  public static SugerenciaTrasladoVianda de(Heladera heladeraOrigen,
                                            List<Heladera> heladerasDestino,
                                            Colaborador colaborador,
                                            EstadoSugerencia estado) {
    return SugerenciaTrasladoVianda
        .builder()
        .heladeraOrigen(heladeraOrigen)
        .heladerasDestino(heladerasDestino)
        .colaborador(colaborador)
        .estado(estado)
        .build();
  }
}
