package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TarjetaPersonaVulnerable implements Tarjeta {

  private String codigo;
  private PersonaVulnerable duenio;
  private Integer usosEnElDia;
  private LocalDate ultimoUso;

  public static TarjetaPersonaVulnerable with(PersonaVulnerable duenio) {
    return TarjetaPersonaVulnerable
        .builder()
        .codigo(GeneradorDeCodigosTarjeta.generar())
        .duenio(duenio)
        .usosEnElDia(0)
        .ultimoUso(LocalDate.now())
        .build();
  }

  public static TarjetaPersonaVulnerable with() {
    return TarjetaPersonaVulnerable
        .builder()
        .codigo(GeneradorDeCodigosTarjeta.generar())
        .build();
  }

  private Boolean puedeUsar() {
    if (LocalDate.now().isAfter(ultimoUso)) {
      this.setUsosEnElDia(0);
    }
    return usosEnElDia < this.usosPorDia();
  }

  public Boolean puedeUsarseEn(Heladera heladera) {
    return heladera.estaActiva() && this.puedeUsar();
  }

  public Integer usosPorDia() {
    return 4 + duenio.getMenoresACargo() * 2;
  }
}
