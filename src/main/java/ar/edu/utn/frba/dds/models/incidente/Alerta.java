package ar.edu.utn.frba.dds.models.incidente;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@DiscriminatorValue("alerta")
public class Alerta extends Incidente {
    public Alerta(Heladera heladera, LocalDateTime fechaHora, TipoIncidente tipo) {
        super(heladera, fechaHora, tipo);
    }

    public Alerta() {
        super(null, null, null);
    }

}
