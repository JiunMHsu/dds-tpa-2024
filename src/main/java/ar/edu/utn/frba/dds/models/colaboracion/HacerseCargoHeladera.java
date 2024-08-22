package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HacerseCargoHeladera {

  private Colaborador colaborador;
  private LocalDate fecha;
  private Heladera heladeraACargo;

  public static HacerseCargoHeladera por(Colaborador colaborador,
                                         LocalDate fecha,
                                         Heladera heladeraACargo) {
    return HacerseCargoHeladera
        .builder()
        .colaborador(colaborador)
        .fecha(fecha)
        .heladeraACargo(heladeraACargo)
        .build();
  }

  public static HacerseCargoHeladera por(Colaborador colaborador,
                                         Heladera heladeraACargo) {
    return HacerseCargoHeladera
        .builder()
        .colaborador(colaborador)
        .heladeraACargo(heladeraACargo)
        .build();
  }

}