package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.usuario.InfoHumana;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.time.LocalDate;
import java.time.Period;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DonacionDinero {

    private LocalDate fechaDonacion;

    private Integer monto;

    private Period frecuencia;

    private Persona colaborador;

    public DonacionDinero() {
    }
}