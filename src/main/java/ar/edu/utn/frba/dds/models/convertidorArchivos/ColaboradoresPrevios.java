package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.colaboracion.Colaboracion;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ColaboradoresPrevios {


  private Documento documento;
  private String nombre;
  private String apellido;
  private String email;
  private LocalDateTime fechaDeColaboracion;
  private Colaboracion formaDeColaboracion;
  private Integer cantidad;
  private Boolean registrado;

  public ColaboradoresPrevios() {
    this.documento = null;
    this.nombre = "nombre";
    this.apellido = "apellido";
    this.email = "email";
    this.fechaDeColaboracion = null;
    this.formaDeColaboracion = null;
    this.cantidad = null;
    this.registrado = false;
  }

}
