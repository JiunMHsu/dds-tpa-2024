package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDateTime;
import java.util.List;

public class DonacionDineroRepository extends ColaboracionRepository<DonacionDinero>{
  public DonacionDineroRepository() {
    super(DonacionDinero.class);
  }
}
