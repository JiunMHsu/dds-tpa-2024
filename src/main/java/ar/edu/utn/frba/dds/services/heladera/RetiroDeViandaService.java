package ar.edu.utn.frba.dds.services.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.repositories.heladera.RetiroDeViandaRepository;

public class RetiroDeViandaService {
    RetiroDeViandaRepository retiroDeViandaRepository;

    public RetiroDeViandaService(RetiroDeViandaRepository retiroDeViandaRepository) {
        this.retiroDeViandaRepository = retiroDeViandaRepository;
    }
    public void guardar(RetiroDeVianda retiroDeVianda) {
        this.retiroDeViandaRepository.guardar(retiroDeVianda);
    }

}
