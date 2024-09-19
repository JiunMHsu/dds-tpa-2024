package ar.edu.utn.frba.dds.models.tarjeta;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import ar.edu.utn.frba.dds.utils.GeneradorDeCodigosTarjeta;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarjeta_colaborador")
public class TarjetaColaborador extends EntidadPersistente {

    @Column(name = "codigo", nullable = false)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador duenio;

    @Column(name = "fecha_alta", columnDefinition = "DATE", nullable = false)
    private LocalDate fechaAlta;

    @Setter
    @Column(name = "esta_activa")
    private Boolean esActiva;

    public static TarjetaColaborador de(String codigo,
                                        Colaborador duenio,
                                        LocalDate fechaAlta,
                                        Boolean esActiva) {
        return TarjetaColaborador
                .builder()
                .codigo(codigo)
                .duenio(duenio)
                .fechaAlta(fechaAlta)
                .esActiva(esActiva)
                .build();
    }

    public static TarjetaColaborador de(Colaborador duenio) {
        return TarjetaColaborador
                .builder()
                .codigo(GeneradorDeCodigosTarjeta.generar())
                .duenio(duenio)
                .fechaAlta(LocalDate.now())
                .esActiva(true)
                .build();
    }

}
