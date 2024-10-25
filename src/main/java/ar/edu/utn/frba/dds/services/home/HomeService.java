package ar.edu.utn.frba.dds.services.home;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Optional;

public class HomeService implements WithSimplePersistenceUnit {
    private ColaboradorRepository colaboradorRepository;
    private UsuarioRepository usuarioRepository;

    public HomeService(ColaboradorRepository colaboradorRepository,
                       UsuarioRepository usuarioRepository) {
        this.colaboradorRepository = colaboradorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> buscarPorId(String id) {
        if (id == null || id.isEmpty())
            throw new IllegalArgumentException("El ID por la heladera no puede ser null o vacío");

        return this.usuarioRepository.buscarPorId(id);
    }

    public Optional<Colaborador> buscarPorUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El colaboradores debe tener un Usuario");
        }
        return colaboradorRepository.buscarPorUsuario(usuario);
    }

}
