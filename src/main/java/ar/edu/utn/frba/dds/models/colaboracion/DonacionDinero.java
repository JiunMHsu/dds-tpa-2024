package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.usuario.Persona;
import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DonacionDinero {

    private LocalDate fechaDonacion;
    private Integer monto;
    private Period frecuencia;
    private Persona colaborador;

    public DonacionDinero(Persona colaborador, Integer monto) {
        this.colaborador = colaborador;
        this.monto = monto;
        this.fechaDonacion = LocalDate.now();
        this.frecuencia = null;
    }

    public void setPeriod(Period frecuencia) {
        this.frecuencia = frecuencia;
    }
}