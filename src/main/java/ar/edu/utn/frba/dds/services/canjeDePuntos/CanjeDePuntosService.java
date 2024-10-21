package ar.edu.utn.frba.dds.services.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.colaboracion.*;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.puntosDeColaboracion.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.puntosDeColaboracion.VarianteCalculoDePuntos;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.*;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
@Setter
@Builder
public class CanjeDePuntosService {
    private Colaborador colaborador;
    private LocalDateTime fechaUltimoCanje;
    private Double puntosSobrantes;
    private VarianteCalculoDePuntos variante;
    private CanjeDePuntosRepository canjeDePuntosRepository;
    private final DonacionDineroRepository donacionDineroRepository;
    private final DistribucionViandasRepository distribucionViandasRepository;
    private final DonacionViandaRepository donacionViandaRepository;
    private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;

    public CanjeDePuntosService(DonacionDineroRepository donacionDineroRepository, DistribucionViandasRepository distribucionViandasRepository, DonacionViandaRepository donacionViandaRepository, RepartoDeTarjetasRepository repartoDeTarjetasRepository, HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository) {

    }

    public CanjeDePuntosService of(Colaborador colaborador , DonacionDineroRepository donacionDineroRepository, DistribucionViandasRepository distribucionViandasRepository, DonacionViandaRepository donacionViandaRepository, RepartoDeTarjetasRepository repartoDeTarjetasRepository, HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository ) {
        CanjeDePuntosService.CanjeDePuntosServiceBuilder canjeDePuntosService = CanjeDePuntosService
                .builder()
                .colaborador(colaborador)
                .donacionDineroRepository (donacionDineroRepository)
                .distribucionViandasRepository (distribucionViandasRepository)
                .donacionViandaRepository (donacionViandaRepository)
                .repartoDeTarjetasRepository (repartoDeTarjetasRepository)
                .hacerseCargoHeladeraRepository (hacerseCargoHeladeraRepository)
                .fechaUltimoCanje(null)
                .puntosSobrantes(0.0)
                .variante(new VarianteCalculoDePuntos())
                .canjeDePuntosRepository(new CanjeDePuntosRepository());


        CanjeDePuntos ultimoCanjeo = canjeDePuntosRepository.obtenerUltimoPorColaborador(colaborador);
        if (ultimoCanjeo != null) {
            this.setPuntosSobrantes(ultimoCanjeo.getPuntosRestantes());
            this.setFechaUltimoCanje(ultimoCanjeo.getFechaCanjeo());
        }
        return CanjeDePuntosService.build();
    }

    public Double calcularPuntos() {
        System.out.println(puntosSobrantes);
        System.out.println(fechaUltimoCanje);
        return this.calcularPorPesosDonados()
                + this.calcularPorViandasDistribuidas()
                + this.calcularPorViandasDonadas()
                + this.calcularPorTarjetasRepartidas()
                + this.calcularPorHeladerasActivas()
                + puntosSobrantes;
    }

    private Double calcularPorPesosDonados(DonacionDineroRepository donacionDineroRepository) {
        List<DonacionDinero> listaDonacionesDinero = donacionDineroRepository
                .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        Double pesosDonados = listaDonacionesDinero.stream()
                .mapToDouble(DonacionDinero::getMonto)
                .sum();

        Double puntaje = pesosDonados * variante.getDonacionDinero();
        System.out.println("donacion dinero");
        System.out.println(puntaje);
        return puntaje;
    }

    private Double calcularPorViandasDistribuidas(DistribucionViandasRepository distribucionViandasRepository) {
        List<DistribucionViandas> listaViandasDistribuidas = distribucionViandasRepository
                .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        Double viandasDistribuidas = listaViandasDistribuidas.stream()
                .mapToDouble(DistribucionViandas::getViandas)
                .sum();

        Double puntaje = viandasDistribuidas * variante.getDistribucionViandas();
        System.out.println("viandas distribuidas");
        System.out.println(puntaje);
        return puntaje;
    }

    private Double calcularPorViandasDonadas(DonacionViandaRepository donacionViandaRepository) {
        List<DonacionVianda> listaViandasDonadas = donacionViandaRepository
                .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        Double viandasDonadas = (double) listaViandasDonadas.size();

        Double puntaje = viandasDonadas * variante.getDonacionVianda();
        System.out.println("viandas donadas");
        System.out.println(puntaje);
        return puntaje;
    }

    private Double calcularPorTarjetasRepartidas(RepartoDeTarjetasRepository repartoDeTarjetasRepository) {
        List<RepartoDeTarjetas> listaTarjetasRepartidas = repartoDeTarjetasRepository
                .obtenerPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        Double tarjetasRepartidas = (double) listaTarjetasRepartidas.size();

        Double puntaje = tarjetasRepartidas * variante.getRepartoTarjeta();
        System.out.println("tarjeras repartidas");
        System.out.println(puntaje);
        return puntaje;
    }

    private Double calcularPorHeladerasActivas(HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository) {
        List<Heladera> listaHeladerasACargo = hacerseCargoHeladeraRepository
                .obtenerPorColaborador(colaborador)
                .stream()
                .map(HacerseCargoHeladera::getHeladeraACargo)
                .toList();

        List<Heladera> listHeladerasActivas = listaHeladerasACargo.stream()
                .filter(Heladera::estaActiva)
                .toList();

        Double heladerasActivas = (double) listHeladerasActivas.size();
        Double mesesActivas = listHeladerasActivas.stream()
                .mapToDouble(heladera -> this.calcularMesesActiva(heladera.getInicioFuncionamiento(), LocalDateTime.now()))
                .sum();

        Double puntajeTotalActual = heladerasActivas * mesesActivas * variante.getHeladerasActivas();
        Double puntajeAnterior;
        if (fechaUltimoCanje == null) {
            puntajeAnterior = 0.0;
        } else {
            puntajeAnterior = this.calcularPorHeladederasAnteriorCanje(listHeladerasActivas);
        }

        Double puntaje = puntajeTotalActual - puntajeAnterior;
        System.out.println("hacerse cargo heladera");
        System.out.println(puntaje);
        System.out.println(puntajeTotalActual);
        return puntaje;
    }

    /**
     * Supone que desde la 'fechaInicio' hasta la fecha de llamada
     * siempre estuvo activa.
     */
    private Integer calcularMesesActiva(LocalDateTime fechaInicio, LocalDateTime fechaActual) {
        Period periodo = Period.between(fechaInicio.toLocalDate(), fechaActual.toLocalDate());
        return periodo.getYears() * 12 + periodo.getMonths();
    }

    private Double calcularPorHeladederasAnteriorCanje(List<Heladera> listHeladerasActivas) {
        List<Heladera> heladerasActivasAntesDelUltimoCanje = listHeladerasActivas.stream()
                .filter(heladera -> heladera.getInicioFuncionamiento().isBefore(ChronoLocalDateTime.from(fechaUltimoCanje)))
                .toList();
        Double heladerasActivas = (double) heladerasActivasAntesDelUltimoCanje.size();
        Double mesesActivas = heladerasActivasAntesDelUltimoCanje.stream()
                .mapToDouble(heladera -> this.calcularMesesActiva(heladera.getInicioFuncionamiento(), fechaUltimoCanje))
                .sum();

        Double puntaje = heladerasActivas * mesesActivas * variante.getHeladerasActivas();
        System.out.println("heladera anterior puntaje");
        System.out.println(puntaje);
        return puntaje;
    }
}
