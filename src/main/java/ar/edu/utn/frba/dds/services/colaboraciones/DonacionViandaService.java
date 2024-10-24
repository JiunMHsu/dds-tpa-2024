package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;

import java.util.Optional;

public class DonacionViandaService {
    private final DonacionViandaRepository donacionViandaRepository;

    public DonacionViandaService(DonacionViandaRepository donacionViandaRepository) {
        this.donacionViandaRepository = donacionViandaRepository;
    }
    public Optional<DonacionVianda> buscarPorId(String id){return donacionViandaRepository.buscarPorId(id);}

    public void guardar(DonacionVianda donacionVianda){donacionViandaRepository.guardar(donacionVianda);};

}
