package ar.edu.utn.frba.dds.services.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.PuntosInvalidosException;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.HacerseCargoHeladera;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.CanjeDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.VarianteDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class CanjeDePuntosService implements WithSimplePersistenceUnit {
    private final CanjeDePuntosRepository canjeDePuntosRepository;
    private final DonacionDineroRepository donacionDineroRepository;
    private final DistribucionViandasRepository distribucionViandasRepository;
    private final DonacionViandaRepository donacionViandaRepository;
    private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final VarianteDePuntosRepository varianteDePuntosRepository;

    public CanjeDePuntosService(CanjeDePuntosRepository canjeDePuntosRepository,
                                DonacionDineroRepository donacionDineroRepository,
                                DistribucionViandasRepository distribucionViandasRepository,
                                DonacionViandaRepository donacionViandaRepository,
                                RepartoDeTarjetasRepository repartoDeTarjetasRepository,
                                HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                                ColaboradorRepository colaboradorRepository,
                                VarianteDePuntosRepository varianteDePuntosRepository) {
        this.canjeDePuntosRepository = canjeDePuntosRepository;
        this.donacionDineroRepository = donacionDineroRepository;
        this.distribucionViandasRepository = distribucionViandasRepository;
        this.donacionViandaRepository = donacionViandaRepository;
        this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
        this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.varianteDePuntosRepository = varianteDePuntosRepository;
    }

    public double getPuntosDeColaborador(Colaborador colaborador) {
        try {
            double puntos = colaborador.puntos();
            System.out.println("se obtuvieron los puntos almacenados de " + colaborador.getNombre());
            return puntos;
        } catch (PuntosInvalidosException e) {
            VarianteDePuntos variante = this.varianteDePuntosRepository.buscarUltimo()
                    .orElseThrow(() -> new IllegalStateException("No hay variante de puntos configurada"));

            double puntos = this.calcularPuntos(colaborador, variante);
            colaborador.setPuntos(new Puntos(puntos, true, LocalDate.now().plusMonths(1).withDayOfMonth(1)));
            withTransaction(() -> this.colaboradorRepository.actualizar(colaborador));
            System.out.println("se recalcularon los puntos de " + colaborador.getNombre() + " a " + puntos);
            return puntos;
        }
    }

    private double calcularPuntos(Colaborador colaborador, VarianteDePuntos variante) {
        LocalDateTime fechaUltimoCanje = null;
        double puntosRestantes = 0;

        Optional<CanjeDePuntos> ultimoCanje = this.canjeDePuntosRepository
                .ultimoPorColaborador(colaborador);

        if (ultimoCanje.isPresent()) {
            fechaUltimoCanje = ultimoCanje.get().getFechaHora();
            puntosRestantes = ultimoCanje.get().getPuntosRestantes();
        }

        return this.pesosDonados(colaborador, fechaUltimoCanje) * variante.getDonacionDinero()
                + this.viandasDistribuidas(colaborador, fechaUltimoCanje) * variante.getDistribucionViandas()
                + this.viandasDonadas(colaborador, fechaUltimoCanje) * variante.getDonacionVianda()
                + this.tarjetasRepartidas(colaborador, fechaUltimoCanje) * variante.getRepartoTarjeta()
                + this.heladerasPorMesesActivas(colaborador, fechaUltimoCanje) * variante.getHeladerasActivas()
                + puntosRestantes;
    }

    private double pesosDonados(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<DonacionDinero> colaboraciones = this.donacionDineroRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        return colaboraciones.stream().mapToDouble(DonacionDinero::getMonto).sum();
    }

    private double viandasDistribuidas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<DistribucionViandas> colaboraciones = this.distribucionViandasRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        return colaboraciones.stream().mapToDouble(DistribucionViandas::getViandas).sum();
    }

    private double viandasDonadas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<DonacionVianda> colaboraciones = this.donacionViandaRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        return colaboraciones.size();
    }

    private double tarjetasRepartidas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<RepartoDeTarjetas> colaboraciones = this.repartoDeTarjetasRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        return colaboraciones.size();
    }

    private double heladerasPorMesesActivas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<Heladera> heladerasActivasACargo = this.hacerseCargoHeladeraRepository
                .buscarPorColaborador(colaborador).stream()
                .map(HacerseCargoHeladera::getHeladeraACargo)
                .filter(Heladera::estaActiva).toList();

        int heladerasActivas = heladerasActivasACargo.size();
        int mesesActivas = heladerasActivasACargo.stream()
                .mapToInt(heladera -> {
                    LocalDateTime fechaPartida = fechaUltimoCanje == null || fechaUltimoCanje.isBefore(heladera.getInicioFuncionamiento())
                            ? heladera.getInicioFuncionamiento()
                            : fechaUltimoCanje;

                    return this.mesesEntre(fechaPartida, LocalDateTime.now());
                }).sum();

        return heladerasActivas * mesesActivas;
    }

    /**
     * Supone que desde la 'fechaInicio' hasta la fecha por llamada
     * siempre estuvo activa.
     */
    private int mesesEntre(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
        return (int) ChronoUnit.MONTHS.between(YearMonth.from(fechaInicio), YearMonth.from(fechaFinal));
    }

    public void registrar(CanjeDePuntos canjeDePuntos) {
        this.canjeDePuntosRepository.guardar(canjeDePuntos);
    }
}
