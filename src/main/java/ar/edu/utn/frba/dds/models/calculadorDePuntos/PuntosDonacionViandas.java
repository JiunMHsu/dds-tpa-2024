package ar.edu.utn.frba.dds.models.calculadorDePuntos;

import ar.edu.utn.frba.dds.models.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
public class PuntosDonacionViandas implements CalculadorDe{
    private static double variable = 1.5;
    private List<DonacionVianda> listaViandasDonadas = new ArrayList<>();

    public Double calcularPuntos(Persona persona){
        // TODO cuando veamos base de datos
        int viandasDonadas = listaViandasDonadas.size();
        return viandasDonadas * variable;
    }
}
