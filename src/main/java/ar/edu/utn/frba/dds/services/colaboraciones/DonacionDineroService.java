package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import java.util.Optional;

public class DonacionDineroService {
    private final DonacionDineroRepository donacionDineroRepository;

    public DonacionDineroService(DonacionDineroRepository repository) {
        this.donacionDineroRepository = repository;
    }

    public void registrarDonacion(DonacionDinero donacion) {
        donacionDineroRepository.guardar(donacion);
    }

    public Optional<DonacionDinero> buscarPorId(String id) {
        return donacionDineroRepository.buscarPorId(id);
    }

}
