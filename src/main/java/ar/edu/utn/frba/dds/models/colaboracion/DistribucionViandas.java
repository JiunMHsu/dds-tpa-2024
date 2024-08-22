package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DistribucionViandas {

  private Colaborador colaborador;
  private LocalDate fechaDistribucion;
  private Heladera origen;
  private Heladera destino;
  private Integer viandas;
  private String motivo;

  public static DistribucionViandas por(Colaborador colaboradorHumano,
                                        LocalDate fechaDistribucion,
                                        Heladera origen,
                                        Heladera destino,
                                        Integer viandas,
                                        String motivo) {
    return DistribucionViandas
        .builder()
        .colaborador(colaboradorHumano)
        .fechaDistribucion(fechaDistribucion)
        .origen(origen)
        .destino(destino)
        .viandas(viandas)
        .motivo(motivo)
        .build();
  }

  public static DistribucionViandas por(Colaborador colaboradorHumano,
                                        LocalDate fechaDistribucion,
                                        Integer viandas) {
    return DistribucionViandas
        .builder()
        .colaborador(colaboradorHumano)
        .fechaDistribucion(fechaDistribucion)
        .viandas(viandas)
        .build();
  }

}