package ar.edu.utn.frba.dds.models.repositories.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.utils.ICrudRepository;

import java.util.List;
import java.util.Optional;

public interface IColaboradorRepository extends ICrudRepository<Colaborador> {

    Optional<Colaborador> buscarPorEmail(String nombre);

}
