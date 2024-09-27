package ar.edu.utn.frba.dds.controllers.heladera;

import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;

public class HeladeraController {

    private HeladeraRepository heladeraRepository;

    public HeladeraController(HeladeraRepository heladeraRepository) {
        this.heladeraRepository = heladeraRepository;
    }
}
