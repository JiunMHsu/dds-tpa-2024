package ar.edu.utn.frba.dds.dtos.colaboraciones.donacionVianda;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;

/**
 * Donación de viandas DTO.
 */
@Getter
public class DonacionViandaDTO extends ColaboracionDTO {

  private final String esEntregada;
  private final String nombreComida;
  private final String pesoVianda;
  private final String caloriasComida;
  private final String fechaCaducidadComida;

  private DonacionViandaDTO(DonacionVianda donacionVianda) {
    super(donacionVianda.getId().toString(),
        TipoColaboracion.DONACION_VIANDAS.getDescription(),
        DateTimeParser.parseFechaHora(donacionVianda.getFechaHora()),
        getPath(TipoColaboracion.DONACION_VIANDAS),
        donacionVianda.getColaborador().getUsuario().getNombre()
    );
    this.esEntregada = donacionVianda.getEsEntregada() ? "Entregada" : "No entregada";
    this.nombreComida = (donacionVianda.getVianda() != null && donacionVianda.getVianda().getComida() != null)
        ? donacionVianda.getVianda().getComida().getNombre()
        : "Desconocido";
    this.pesoVianda = (donacionVianda.getVianda() != null && donacionVianda.getVianda().getPeso() != null)
        ? donacionVianda.getVianda().getPeso().toString()
        : "Desconocido";
    this.caloriasComida = (donacionVianda.getVianda() != null && donacionVianda.getVianda().getComida() != null)
        ? String.valueOf(donacionVianda.getVianda().getComida().getCalorias())
        : "Desconocido";
    this.fechaCaducidadComida = (donacionVianda.getVianda() != null && donacionVianda.getVianda().getFechaCaducidad() != null)
        ? DateTimeParser.parseFecha(donacionVianda.getVianda().getFechaCaducidad())
        : "Desconocida";
  }

  public static DonacionViandaDTO fromColaboracion(DonacionVianda donacionVianda) {
    return new DonacionViandaDTO(donacionVianda);
  }
}
