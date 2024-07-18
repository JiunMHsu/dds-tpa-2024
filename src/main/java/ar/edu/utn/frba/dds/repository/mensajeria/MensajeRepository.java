package ar.edu.utn.frba.dds.repository.mensajeria;

import ar.edu.utn.frba.dds.models.data.Mensaje;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MensajeRepository {
    private static final List<Mensaje> db = new ArrayList<>();

    public static void agregar(Mensaje mensaje) {
        db.add(mensaje);
    }

    public static List<Mensaje> obtenerTodos() {
        return new ArrayList<>(db); // Devolver una copia para proteger la integridad del repositorio
    }

    public static Mensaje obtenerUltimo() {
        if (!db.isEmpty()) {
            return db.get(db.size() - 1);
        } else {
            return null;
        }
    }

    public static void limpiar() {
        db.clear(); // Limpiar todos los mensajes del repositorio
    }
}
