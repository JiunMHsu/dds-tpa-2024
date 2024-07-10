package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

@Getter
public class DistribucionViandas {

  private Heladera origen;
  private Heladera destino;
  private List<Vianda> viandasAMover;
  private String motivo;
  private LocalDate fechaDistribucion;
  private Colaborador colaboradorHumano;

  public DistribucionViandas(Heladera origen, Heladera destino, List<Vianda> viandasAMover, Colaborador colaboradorHumano) {
    this.origen = origen;
    this.destino = destino;
    this.viandasAMover = viandasAMover;
    this.colaboradorHumano = colaboradorHumano;
    this.fechaDistribucion = LocalDate.now();
    this.motivo = "";
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }
}