package ar.edu.utn.frba.dds.models.entities.puntosDeColaboracion;

import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Optional;

import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import lombok.Builder;

@Builder
public class PuntosPorColaboracion {

    private Colaborador colaborador;
    private LocalDateTime fechaUltimoCanje;
    private Double puntosSobrantes;
    private CanjeDePuntosService canjeDePuntosService;

    public PuntosPorColaboracion of(Colaborador colaborador, CanjeDePuntosService canjeDePuntosService) {
        PuntosPorColaboracionBuilder puntosPorColaboracion = PuntosPorColaboracion
                .builder()
                .colaborador(colaborador)
                .fechaUltimoCanje(null)
                .puntosSobrantes(0.0)
                .canjeDePuntosService(canjeDePuntosService);
        Optional<CanjeDePuntos> ultimoCanjeo = canjeDePuntosService.obtenerUltimoPorColaborador(colaborador);
        if (ultimoCanjeo.isPresent()) {
            puntosPorColaboracion.puntosSobrantes(ultimoCanjeo.get().getPuntosRestantes());
            puntosPorColaboracion.fechaUltimoCanje(ultimoCanjeo.get().getFechaCanjeo());
        }
        return puntosPorColaboracion.build();
    }

    public Double calcularPuntos() {
        return canjeDePuntosService.calcularPuntos(colaborador, fechaUltimoCanje, puntosSobrantes);
    }

}