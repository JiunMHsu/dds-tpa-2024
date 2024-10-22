package ar.edu.utn.frba.dds.models.entities.puntosPorColaborador;

import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.services.canjeDePuntos.CanjeDePuntosService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;

@Builder
public class PuntosPorColaborador {

    private Colaborador colaborador;
    private LocalDateTime fechaUltimoCanje;
    private Double puntosSobrantes;
    private CanjeDePuntosService canjeDePuntosService;

    public static PuntosPorColaborador of(Colaborador colaborador, CanjeDePuntosService canjeDePuntosService) {
        PuntosPorColaboradorBuilder puntosPorColaboracion = PuntosPorColaborador
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