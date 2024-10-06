package ar.edu.utn.frba.dds.models.entities.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

}
