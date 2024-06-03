package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Heladera {

    private String nombre;
    private Direccion direccion;
    private LocalDate fechaInicioFuncionamiento;
    private Integer capacidad;
    private List<Vianda> contenido;
    private Float ultimaTemperatura;
    private RangoTemperatura rangoTemperatura;

    public Heladera(String nombre, Direccion direccion, Integer capacidad, Float ultimaTemperatura, RangoTemperatura rangoTemperatura) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaInicioFuncionamiento = LocalDate.now();
        this.capacidad = capacidad;
        this.contenido = new ArrayList<>();
        this.ultimaTemperatura = ultimaTemperatura;
        this.rangoTemperatura = rangoTemperatura;
    }

    public void agregarVianda(Vianda vianda) throws CapacidadExcedidaException {
        if(chequearCapacidad()){
            contenido.add(vianda);
        }
        else{
            throw new CapacidadExcedidaException("La capacidad de la heladera esta excedida");
        }

    }

    public void quitarVianda(Vianda vianda) throws ViandaNoEncontradaException {
        if (contenido == null || !contenido.remove(vianda)){
            throw new ViandaNoEncontradaException("La vianda no se encontro en la lista o no hay lista de viandas");
        }
    }

    private Boolean chequearCapacidad() {
        return contenido.size() < capacidad;
    }

    public Boolean verificarTemperatura(Float temperaturaActual) {
        ultimaTemperatura = temperaturaActual;

        Float maxima = this.rangoTemperatura.getTemperaturaMaxima();
        Float minima = this.rangoTemperatura.getTemperaturaMinima();
        if (temperaturaActual < minima || temperaturaActual > maxima) {
            return false;
        }

        return true;
    }

}