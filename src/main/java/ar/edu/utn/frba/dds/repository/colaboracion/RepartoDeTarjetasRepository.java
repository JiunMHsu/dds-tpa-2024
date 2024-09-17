package ar.edu.utn.frba.dds.repository.colaboracion;

import ar.edu.utn.frba.dds.models.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import java.time.LocalDate;
import java.util.List;

public class RepartoDeTarjetasRepository extends ColaboracionRepository<RepartoDeTarjetas>{

  public RepartoDeTarjetasRepository() {
    super(RepartoDeTarjetas.class);
  }
}
