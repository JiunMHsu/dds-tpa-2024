package ar.edu.utn.frba.dds.models.heladera;

import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.vianda.Vianda;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Heladera {

    /**
     * Default constructor
     */
    public Heladera() {
    } //??
    private String nombre;
    private Direccion direccion;
    private LocalDateTime fechaInicioFuncionamiento;
    private Integer capacidad;
    private List<Vianda> contenido;
    private Float ultimaTemperatura;

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