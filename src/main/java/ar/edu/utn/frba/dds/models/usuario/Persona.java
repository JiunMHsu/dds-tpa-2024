package ar.edu.utn.frba.dds.models.usuario;

import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.data.Contacto;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.formulario.FormularioRespondido;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Persona {

    private Direccion direccion;
    private List<TipoColaboracion> formaDeColaborar;
    private FormularioRespondido datosAdicionales;
    private Usuario usuario;
    private Contacto contacto;
    private TipoDePersona tipoDePersona;
    private Double puntosObtenidos;
    private Double puntosCanjeados;
    private String razonSocial;
    private TipoRazonSocial tipoRazonSocial;
    private String rubro;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    public Persona(){

    }
}
