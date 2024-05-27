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

    /**
     * Default constructor
     */
    private String nombre;
    private Direccion direccion;
    private LocalDate fechaInicioFuncionamiento;
    private Integer capacidad;
    private List<Vianda> contenido;
    private Float ultimaTemperatura;
    private Boolean estaActiva;

    public Heladera(String nombre, Direccion direccion, LocalDate fechaInicioFuncionamiento, Integer capacidad, List<Vianda> contenido, Float ultimaTemperatura, Boolean estaActiva) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaInicioFuncionamiento = fechaInicioFuncionamiento;
        this.capacidad = capacidad;
        this.contenido = contenido;
        this.ultimaTemperatura = ultimaTemperatura;
        this.estaActiva = estaActiva;
    }

    public void agregarVianda(Vianda vianda) throws CapacidadExcedidaException {
        if (contenido == null){
            contenido = new ArrayList<>();
        }
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
    private Float obtenerTemperatura(SensorTemperatura sensor){
        ultimaTemperatura = sensor.consultarTemperatura(this);
        return ultimaTemperatura;
    }

}