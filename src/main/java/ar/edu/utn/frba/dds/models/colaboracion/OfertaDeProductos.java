package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.data.Imagen;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "oferta_productos")
public class OfertaDeProductos extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "colaborador_id", nullable = false)
    private Colaborador colaborador;

    @Column(name = "fecha_hora", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "puntos_necesarios", nullable = false)
    private Double puntosNecesarios;

    @Enumerated(EnumType.STRING)
    @Column(name = "rubro", nullable = false)
    private RubroOferta rubro;

    @Embedded
    private Imagen imagen;

    public static OfertaDeProductos por(Colaborador colaborador,
                                        LocalDateTime fechaOferta,
                                        String nombre,
                                        Double puntosNecesarios,
                                        RubroOferta rubro,
                                        Imagen imagen) {
        return OfertaDeProductos
                .builder()
                .colaborador(colaborador)
                .fechaHora(fechaOferta)
                .nombre(nombre)
                .puntosNecesarios(puntosNecesarios)
                .rubro(rubro)
                .imagen(imagen)
                .build();
    }

    public static OfertaDeProductos por(Colaborador colaborador,
                                        String nombre,
                                        Double puntosNecesarios) {
        return OfertaDeProductos
                .builder()
                .colaborador(colaborador)
                .nombre(nombre)
                .puntosNecesarios(puntosNecesarios)
                .build();
    }

}
