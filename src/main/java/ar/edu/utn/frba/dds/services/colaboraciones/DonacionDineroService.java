package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class DonacionDineroService implements WithSimplePersistenceUnit {
    private final DonacionDineroRepository donacionDineroRepository;

    public DonacionDineroService(DonacionDineroRepository repository) {
        this.donacionDineroRepository = repository;
    }

    public void registrar(DonacionDinero donacion) {
        // TODO - Validaciones??
        withTransaction(() -> donacionDineroRepository.guardar(donacion));
    }

    public Optional<DonacionDinero> buscarPorId(String id) {
        return donacionDineroRepository.buscarPorId(id);
    }

}
