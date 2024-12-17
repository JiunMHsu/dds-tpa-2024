package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.AperturaHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import ar.edu.utn.frba.dds.models.repositories.heladera.AperturaHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.ISolicitudDeAperturaRepository;

import java.util.List;
import java.util.Optional;

public class AperturaHeladeraService {
    private final AperturaHeladeraRepository aperturaHeladeraRepository;

    public AperturaHeladeraService(AperturaHeladeraRepository aperturaHeladeraRepository) {
        this.aperturaHeladeraRepository = aperturaHeladeraRepository;
    }

    public void guardar(AperturaHeladera apertura) {
        this.aperturaHeladeraRepository.guardar(apertura);
    }
    public Optional<AperturaHeladera> buscarPorSolicitud(SolicitudDeApertura solicitudDeApertura) {
        return this.aperturaHeladeraRepository.buscarPorSolicitud(solicitudDeApertura);
    }
}
