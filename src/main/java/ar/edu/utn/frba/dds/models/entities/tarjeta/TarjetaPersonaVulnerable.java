package ar.edu.utn.frba.dds.models.entities.tarjeta;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.stateless.ValidadorDeCodigosTarjeta;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarjeta_persona_vulnerable")
public class TarjetaPersonaVulnerable extends EntidadPersistente {

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @OneToOne
    @JoinColumn(name = "persona_vulnerable_id", unique = true, nullable = false)
    private PersonaVulnerable duenio;

    @Setter
    @Column(name = "usos_del_dia")
    private Integer usosEnElDia;

    @Setter
    @Column(name = "fecha_ultimo_uso", columnDefinition = "DATE")
    private LocalDate ultimoUso;

    public static TarjetaPersonaVulnerable de(PersonaVulnerable duenio) {
        return TarjetaPersonaVulnerable
                .builder()
                .codigo(ValidadorDeCodigosTarjeta.generar())
                .duenio(duenio)
                .usosEnElDia(0)
                .ultimoUso(LocalDate.now())
                .build();
    }

    public static TarjetaPersonaVulnerable de() {
        return TarjetaPersonaVulnerable
                .builder()
                .codigo(ValidadorDeCodigosTarjeta.generar())
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

    // TODO - Hacerlo atributo? como posible optimización
    // Para mi es innecesario, porque:
    // - No es un metodo que se utilice de manera frecuente (tengo entendido que solo cuando se entrega una tarjeta o se modifica la cantidad de menoresACargo)
    // - Es una pavada el metodo en si
    // - En caso que se considere adecuado persisitirlo puede ser, pero cmo solo se utilizar para validad que la persona pueda utilizar la taerjeta, en mi opinion, no lo es
    public Integer usosPorDia() {
        return 4 + duenio.getMenoresACargo() * 2;
    }

    public void sumarUso() {
        usosEnElDia += 1;
    }
}
