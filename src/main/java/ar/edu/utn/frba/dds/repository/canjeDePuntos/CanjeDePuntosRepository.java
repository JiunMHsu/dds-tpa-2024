package ar.edu.utn.frba.dds.repository.canjeDePuntos;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frba.dds.repository.colaboracion.DistribucionViandasRepository;
import lombok.Getter;

@Getter
public class CanjeDePuntosRepository {
  private static final List<CanjeDePuntos> db = new ArrayList<>();

  public static void agregar(CanjeDePuntos canjeDePuntos) {
    db.add(canjeDePuntos);
  }

  public static List<CanjeDePuntos> obtenerPorColaborador(Colaborador colaborador) {
    return db.stream()
            .filter(colab -> colab.getColaborador().equals(colaborador))
            .toList();
  }

  public static CanjeDePuntos obtenerUltimoPorColaborador(Colaborador colaborador) {
    List <CanjeDePuntos> canjeDePuntos = CanjeDePuntosRepository.obtenerPorColaborador(colaborador);
    if(!canjeDePuntos.isEmpty()) {
      return canjeDePuntos.get(canjeDePuntos.size() - 1);
    }else {
      return null; // throw new IllegalStateException("La lista de canjes está vacía");
    }
  }

}
