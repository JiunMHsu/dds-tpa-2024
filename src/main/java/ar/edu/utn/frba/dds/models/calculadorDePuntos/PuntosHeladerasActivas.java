package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.usuario.Persona;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

public class PuntosHeladerasActivas {
    private double variable = 5;
    private int mesesActivas;

    public static Double calcularPuntos(Persona persona){
        List<HacerseCargoHeladera> listaHeladerasACargo = Colaboracion.obtenerHeladerasACargo(persona);
        List<Heladera> listaHeladerasActivas = listaHeladerasACargo.stream()
                .map(HacerseCargoHeladera :: getHeladeraACargo)
                .filter(Heladera :: estaActiva)
                .collect(Collectors.toList());
        int heladerasActivas = listaHeladerasActivas.size();
        int mesesActivas = listaHeladerasActivas.stream()
                                                .mapToInt(heladera -> calcularMesesActiva(heladera.getFechaInicioFuncionamiento))
                                                .sum();
        return heladerasActivas * mesesActivas * variable;
    }

    public int calcularMesesActiva(LocalDate fechaInicio){
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaInicio, fechaActual);
        return periodo.getYears() * 12 + periodo.getMonths();
    }
}
