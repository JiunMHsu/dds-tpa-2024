package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.repositories.heladera.ISolicitudDeAperturaRepository;

public class SolicitudDeAperturaService {

    private final ISolicitudDeAperturaRepository solicitudDeAperturaRepository;

    public SolicitudDeAperturaService (ISolicitudDeAperturaRepository solicitudDeAperturaRepository) {
        this.solicitudDeAperturaRepository = solicitudDeAperturaRepository;
    }
}
