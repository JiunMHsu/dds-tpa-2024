package ar.edu.utn.frba.dds.services.canjeDePuntos;

import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.CanjeDePuntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.PuntosInvalidosException;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteCalculoDePuntos;
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
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.Setter;

@Setter
public class CanjeDePuntosService implements WithSimplePersistenceUnit {
    private final CanjeDePuntosRepository canjeDePuntosRepository;
    private final DonacionDineroRepository donacionDineroRepository;
    private final DistribucionViandasRepository distribucionViandasRepository;
    private final DonacionViandaRepository donacionViandaRepository;
    private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;
    private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
    private final ColaboradorRepository colaboradorRepository;

    private VarianteCalculoDePuntos variante;


    public CanjeDePuntosService(CanjeDePuntosRepository canjeDePuntosRepository,
                                DonacionDineroRepository donacionDineroRepository,
                                DistribucionViandasRepository distribucionViandasRepository,
                                DonacionViandaRepository donacionViandaRepository,
                                RepartoDeTarjetasRepository repartoDeTarjetasRepository,
                                HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                                ColaboradorRepository colaboradorRepository) {
        this.canjeDePuntosRepository = canjeDePuntosRepository;
        this.donacionDineroRepository = donacionDineroRepository;
        this.distribucionViandasRepository = distribucionViandasRepository;
        this.donacionViandaRepository = donacionViandaRepository;
        this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
        this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
        this.colaboradorRepository = colaboradorRepository;
        this.variante = new VarianteCalculoDePuntos();
    }

    public double getPuntosDeColaborador(Colaborador colaborador) {
        double puntos;
        try {
            puntos = colaborador.puntos();
            return puntos;
        } catch (PuntosInvalidosException e) {
            puntos = this.calcularPuntos(colaborador);
            colaborador.setPuntos(new Puntos(puntos, true, LocalDate.now().plusMonths(1)));
            withTransaction(() -> this.colaboradorRepository.actualizar(colaborador));
            return puntos;
        }
    }

    private double calcularPuntos(Colaborador colaborador) {
        LocalDateTime fechaUltimoCanje = null;
        double puntosRestantes = 0;

        Optional<CanjeDePuntos> ultimoCanje = this.canjeDePuntosRepository
                .ultimoCanjePorColaborador(colaborador);

        if (ultimoCanje.isPresent()) {
            fechaUltimoCanje = ultimoCanje.get().getFechaHora();
            puntosRestantes = ultimoCanje.get().getPuntosRestantes();
        }

        return this.calcularPorPesosDonados(colaborador, fechaUltimoCanje)
                + this.calcularPorViandasDistribuidas(colaborador, fechaUltimoCanje)
                + this.calcularPorViandasDonadas(colaborador, fechaUltimoCanje)
                + this.calcularPorTarjetasRepartidas(colaborador, fechaUltimoCanje)
                + this.calcularPorHeladerasActivas(colaborador, fechaUltimoCanje)
                + puntosRestantes;
    }

    private double calcularPorPesosDonados(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<DonacionDinero> colaboraciones = this.donacionDineroRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        double pesosDonados = colaboraciones.stream()
                .mapToDouble(DonacionDinero::getMonto)
                .sum();

        double puntaje = pesosDonados * variante.getDonacionDinero();
        System.out.println("puntos por donacion dinero: " + puntaje);
        return puntaje;
    }

    private double calcularPorViandasDistribuidas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<DistribucionViandas> colaboraciones = this.distribucionViandasRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        double viandasDistribuidas = colaboraciones.stream()
                .mapToDouble(DistribucionViandas::getViandas)
                .sum();

        double puntaje = viandasDistribuidas * variante.getDistribucionViandas();
        System.out.println("puntos por viandas distribuidas" + puntaje);
        return puntaje;
    }

    private double calcularPorViandasDonadas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<DonacionVianda> colaboraciones = this.donacionViandaRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        double viandasDonadas = colaboraciones.size();
        double puntaje = viandasDonadas * variante.getDonacionVianda();
        System.out.println("puntos por viandas donadas: " + puntaje);
        return puntaje;
    }

    private double calcularPorTarjetasRepartidas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
        List<RepartoDeTarjetas> colaboraciones = this.repartoDeTarjetasRepository
                .buscarPorColaboradorAPartirDe(colaborador, fechaUltimoCanje);

        double tarjetasRepartidas = colaboraciones.size();
        double puntaje = tarjetasRepartidas * variante.getRepartoTarjeta();
        System.out.println("puntos por tarjeras repartidas: " + puntaje);
        return puntaje;
    }

    private double calcularPorHeladerasActivas(Colaborador colaborador, LocalDateTime fechaUltimoCanje) {
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

        double puntaje = heladerasActivas * mesesActivas * variante.getHeladerasActivas();
        System.out.println("puntos por hacerse cargo heladera: " + puntaje);
        return puntaje;
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
