package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.reportes.RegistroIncidente;

import ar.edu.utn.frba.dds.repository.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.repository.tecnico.TecnicoRepository;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Incidente {

  private TipoIncidente tipo;
  private Heladera heladera;
  private LocalDateTime fechaHora;

  public static Incidente of(TipoIncidente tipo, Heladera heladera, LocalDateTime fechaHora) {
    return Incidente
        .builder()
        .tipo(tipo)
        .heladera(heladera)
        .fechaHora(fechaHora)
        .build();
  }

  public void reportar() {
    heladera.setEstadoDeFalla();
    RegistroIncidente.incidentePorHeladeras(heladera);
    IncidenteRepository.agregar(this);

    Tecnico tecnicoMasCercano = heladera.tecnicoMasCercano(
        TecnicoRepository.obtenerTodos()
    );
    tecnicoMasCercano.notificarPorIncidente(this);
  }
}
