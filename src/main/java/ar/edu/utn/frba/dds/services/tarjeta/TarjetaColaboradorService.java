package ar.edu.utn.frba.dds.services.tarjeta;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaColaboradorRepository;

/**
 * Servicio de Tarjeta de Colaborador.
 */
public class TarjetaColaboradorService {

  private final TarjetaColaboradorRepository tarjetaColaboradorRepository;

  public TarjetaColaboradorService(TarjetaColaboradorRepository tarjetaColaboradorRepository) {
    this.tarjetaColaboradorRepository = tarjetaColaboradorRepository;
  }

  // TODO: Método para generar tarjeta colaborador

  /**
   * Busca una tarjeta de colaborador por el colaborador.
   *
   * @param colaborador Colaborador
   * @return Tarjeta de Colaborador
   * @throws ResourceNotFoundException Excepción de recurso no encontrado
   */
  public TarjetaColaborador buscarPorColaborador(Colaborador colaborador) {
    return tarjetaColaboradorRepository
        .buscarPorColaborador(colaborador)
        .orElseThrow(ResourceNotFoundException::new);
  }
}
