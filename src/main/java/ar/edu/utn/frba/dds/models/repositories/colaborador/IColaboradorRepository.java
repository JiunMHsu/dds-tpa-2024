package ar.edu.utn.frba.dds.models.repositories.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.ICrudRepository;
import java.util.Optional;

public interface IColaboradorRepository extends ICrudRepository<Colaborador> {
    Optional<Colaborador> buscarPorEmail(String email);

    Optional<Colaborador> buscarPorUsuario(Usuario usuario);

    Optional<Colaborador> buscarPorId(String id);
}
