package ar.edu.utn.frba.dds.models.entities.puntoDonacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PuntoDonacion {

  private long id;

  private String nombre;

  private double latitud;

  private double longitud;

  private double distanciaEnKm;

  public void print() {
    System.out.println("lugar: " + nombre);
    System.out.println("id: " + id);
    System.out.println("lat:" + latitud);
    System.out.println("lon: " + longitud);
    System.out.println("dist en km: " + distanciaEnKm);
    System.out.println();
  }
}
