package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.repositories.heladera.ISolicitudDeAperturaRepository;

import java.util.List;

public class SolicitudDeAperturaService {

  private final ISolicitudDeAperturaRepository solicitudDeAperturaRepository;

  public SolicitudDeAperturaService(ISolicitudDeAperturaRepository solicitudDeAperturaRepository) {
    this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
  }

  public List<SolicitudDeApertura> buscarPorTarjetaHeladeraEnLasUltimas(String tarjeta, Heladera heladera) {
    return this.solicitudDeAperturaRepository.buscarPorTarjetaHeladeraEnLasUltimas(tarjeta, heladera);
  }

}