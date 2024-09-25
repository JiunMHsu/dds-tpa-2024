package ar.edu.utn.frba.dds.models.entities.colaboracion;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
