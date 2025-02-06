package ar.edu.utn.frba.dds.services.tarjeta;

import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.utils.RandomString;

/**
 * Servicio de Tarjeta de Colaborador.
 */
public class TarjetaColaboradorService {

  private final TarjetaColaboradorRepository tarjetaColaboradorRepository;

  public TarjetaColaboradorService(TarjetaColaboradorRepository tarjetaColaboradorRepository) {
    this.tarjetaColaboradorRepository = tarjetaColaboradorRepository;
  }

  /**
   * Genera una tarjeta de colaborador si no tiene.
   * La operación siempre se realiza sin transacción, entiendiendo que simepre se ejecuta en una
   * transacción mayor (guardado o actualización de colaborador).
   *
   * @param colaborador Colaborador
   */
  public void generarTarjetaPara(Colaborador colaborador) {
    if (tarjetaColaboradorRepository.buscarPorColaborador(colaborador).isPresent()) {
      return;
    }

    String codigo = new RandomString(11).generate();
    TarjetaColaborador tarejta = TarjetaColaborador.de(codigo, colaborador, true);

    tarjetaColaboradorRepository.guardar(tarejta);
  }

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
