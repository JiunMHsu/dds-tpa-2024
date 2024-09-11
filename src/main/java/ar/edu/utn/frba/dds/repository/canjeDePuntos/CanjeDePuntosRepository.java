package ar.edu.utn.frba.dds.repository.canjeDePuntos;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.puntosDeColaboracion.CanjeDePuntos;
import java.util.List;
import lombok.Getter;

@Getter
public class CanjeDePuntosRepository {

  public static void agregar(CanjeDePuntos canjeDePuntos) {
  }

  public static List<CanjeDePuntos> obtenerPorColaborador(Colaborador colaborador) {
    return null;
  }

  public static CanjeDePuntos obtenerUltimoPorColaborador(Colaborador colaborador) {
    return null;
  }

}
