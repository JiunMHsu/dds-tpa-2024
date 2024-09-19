package ar.edu.utn.frba.dds.models.colaborador;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "tipo_colaborador")
public class TipoColaborador {

    @Id
    @GeneratedValue
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoPersona tipo;

    @ElementCollection
    @CollectionTable(name = "colaboraciones_permitidas", joinColumns = @JoinColumn(name = "tipo_colaborador_id"))
    @Column(name = "colaboracion")
    private List<Colaboracion> colaboraciones;

    public TipoColaborador(TipoPersona tipo, List<Colaboracion> colaboraciones) {
        this.tipo = tipo;
        this.colaboraciones = colaboraciones;
    }

    public TipoColaborador() {
        this.tipo = null;
        this.colaboraciones = null;
    }
}
