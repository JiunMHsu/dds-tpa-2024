package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import java.time.LocalDate;
import java.util.UUID;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarjeta_vulnerable")
public class TarjetaPersonaVulnerable implements Tarjeta {

  @Id
  private String codigo;

  @OneToOne
  @JoinColumn(name = "vulnerable_id", unique = true, nullable = false)
  private PersonaVulnerable duenio;

  @Setter
  @Column (name = "usos_dia")
  private Integer usosEnElDia;

  @Setter
  @Column (name = "fecha_ultimo_uso")
  private LocalDate ultimoUso;

  public static TarjetaPersonaVulnerable de(PersonaVulnerable duenio) {
    return TarjetaPersonaVulnerable
        .builder()
        .codigo(GeneradorDeCodigosTarjeta.generar())
        .duenio(duenio)
        .usosEnElDia(0)
        .ultimoUso(LocalDate.now())
        .build();
  }

  public static TarjetaPersonaVulnerable de() {
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

  public void sumarUso() {
    usosEnElDia += 1;
  }
}
