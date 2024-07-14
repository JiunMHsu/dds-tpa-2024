package ar.edu.utn.frba.dds.repository.tecnico;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;
import ar.edu.utn.frba.dds.repository.canjeDePuntos.CanjeDePuntosRepository;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TecnicoRepository {

    private static final List<Tecnico> db = new ArrayList<>();

    public static void agregar(Tecnico tecnico) {
        db.add(tecnico);
    }

    public static List<Tecnico> obtenerTodos() {
        return db; // ¿es una copia?
    }

}
