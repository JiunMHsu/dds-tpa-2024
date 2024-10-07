package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.utils.ICrudRepository;
import java.util.List;
import java.util.Optional;

// TODO - Si refectorizamos las entidades de tarjeta, cambiar para que se pase la Tarjeta
public interface IOperacionPorTarjetaRepository<T> extends ICrudRepository<T> {
    List<T> buscarPorTarjeta(String tarjeta);

    Optional<T> buscarUltimoPorTarjeta(String tarjeta);
}
