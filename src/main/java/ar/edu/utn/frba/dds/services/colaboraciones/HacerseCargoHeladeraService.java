package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import java.util.Optional;

public class HacerseCargoHeladeraService {

    private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

    public HacerseCargoHeladeraService(HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository) {
        this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
    }

    public Optional<HacerseCargoHeladera> buscarPorId(String id) {
        return hacerseCargoHeladeraRepository.buscarPorId(id);
    }

    public void guardar(HacerseCargoHeladera hacerseCargoHeladera) {
        hacerseCargoHeladeraRepository.guardar(hacerseCargoHeladera);
    }

    ;
}
