package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistribucionViandas {

  private Colaborador colaborador;
  private LocalDate fechaDistribucion;
  private Heladera origen;
  private Heladera destino;
  private List<Vianda> viandas;
  private String motivo;

  public static DistribucionViandas with(Colaborador colaboradorHumano,
                                         Heladera origen,
                                         Heladera destino,
                                         List<Vianda> viandas,
                                         String motivo) {
    return DistribucionViandas
        .builder()
        .colaborador(colaboradorHumano)
        .fechaDistribucion(LocalDate.now())
        .origen(origen)
        .destino(destino)
        .viandas(viandas)
        .motivo(motivo)
        .build();
  }

  public static DistribucionViandas with(Colaborador colaboradorHumano,
                                         Heladera origen,
                                         Heladera destino,
                                         List<Vianda> viandas) {
    return DistribucionViandas
        .builder()
        .colaborador(colaboradorHumano)
        .fechaDistribucion(LocalDate.now())
        .origen(origen)
        .destino(destino)
        .viandas(viandas)
        .build();
  }

  public static DistribucionViandas with(Colaborador colaborador) {
    return DistribucionViandas
        .builder()
        .colaborador(colaborador)
        .fechaDistribucion(LocalDate.now())
        .viandas(new ArrayList<>())
        .build();
  }
}