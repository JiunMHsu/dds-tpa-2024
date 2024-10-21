package ar.edu.utn.frba.dds.services.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaborador.IColaboradorRepository;

import java.util.Optional;
import java.util.UUID;

public class ColaboradorService {

    private final IColaboradorRepository colaboradorRepository;

    public ColaboradorService (IColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    public Optional<Colaborador> obtenerColaboradorPorID(String id) {

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del colaborador no puede ser null o vacío");
        }

        return colaboradorRepository.buscarPorId(id);
    }

    public Optional<Colaborador> obtenerColaboradorPorUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El colaboradores debe tener un Usuario");
        }
        return colaboradorRepository.buscarPorUsuario(usuario);
    }


}
