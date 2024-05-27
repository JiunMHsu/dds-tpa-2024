package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.vianda.Vianda;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class DonacionVianda {

    private Vianda vianda;
    private LocalDate fechaDonacion;
    private Heladera heladera;
    private Persona colaborador;

    public DonacionVianda(Persona colaborador, Vianda vianda, Heladera heladera) {
        this.colaborador = colaborador;
        this.vianda = vianda;
        this.heladera = heladera;
        this.fechaDonacion = LocalDate.now();
    }

}