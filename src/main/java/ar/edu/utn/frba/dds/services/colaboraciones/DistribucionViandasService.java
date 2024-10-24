package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;

import java.util.Optional;

public class DistribucionViandasService {
    private final DistribucionViandasRepository distribucionViandasRepository;

    public DistribucionViandasService(DistribucionViandasRepository distribucionViandasRepository) {
        this.distribucionViandasRepository = distribucionViandasRepository;
    }
    public void guardar(DistribucionViandas distribucionViandas){distribucionViandasRepository.guardar(distribucionViandas);};

    public Optional<DistribucionViandas> buscarPorId(String id){return distribucionViandasRepository.buscarPorId(id);}
}
