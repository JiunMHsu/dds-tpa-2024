package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.vianda.ViandaRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class DonacionViandaService implements WithSimplePersistenceUnit {
    private final DonacionViandaRepository donacionViandaRepository;
    private final ViandaRepository viandaRepository;

    public DonacionViandaService(DonacionViandaRepository donacionViandaRepository, ViandaRepository viandaRepository) {
        this.donacionViandaRepository = donacionViandaRepository;
        this.viandaRepository = viandaRepository;
    }

    public void registrar(DonacionVianda donacionVianda) {
        beginTransaction();
        this.viandaRepository.guardar(donacionVianda.getVianda());
        donacionViandaRepository.guardar(donacionVianda);
        commitTransaction();
    }

    public Optional<DonacionVianda> buscarPorId(String id) {
        return donacionViandaRepository.buscarPorId(id);
    }
}
