package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.SolicitudDeApertura;
import java.util.List;
import java.util.Optional;

// TODO - Si refectorizamos las entidades por tarjeta, cambiar paraColaborador que se pase la Tarjeta
public interface ISolicitudDeAperturaRepository {

    List<SolicitudDeApertura> buscarPorTarjeta(String tarjeta);

    Optional<SolicitudDeApertura> buscarUltimoPorTarjeta(String tarjeta);
}
