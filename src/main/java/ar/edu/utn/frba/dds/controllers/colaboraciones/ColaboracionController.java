package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.DistribucionViandasDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionDineroDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.DonacionViandaDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.HacerseCargoHeladeraDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.RepartoDeTarjetasDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ColaboracionController extends ColaboradorPorSession {
    private DistribucionViandasRepository distribucionViandasRepository;
    private DonacionDineroRepository donacionDineroRepository;
    private DonacionViandaRepository donacionViandaRepository;
    private OfertaDeProductosRepository ofertaDeProductosRepository;
    private HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
    private RepartoDeTarjetasRepository repartoDeTarjetasRepository;

    public ColaboracionController(UsuarioService usuarioService, ColaboradorService colaboradorService) {
        super(usuarioService, colaboradorService);
    }

    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        if (context.sessionAttribute("userRol") == "ADMIN") {
            Map<Colaboracion, Supplier<List<?>>> colaboracionHandlers = Map.of(
                    Colaboracion.OFERTA_DE_PRODUCTOS, () -> ofertaDeProductosRepository.buscarTodos().stream()
                            .map(OfertaDeProductosDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.REPARTO_DE_TARJETAS, () -> repartoDeTarjetasRepository.buscarTodos().stream()
                            .map(RepartoDeTarjetasDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.DONACION_DINERO, () -> donacionDineroRepository.buscarTodos().stream()
                            .map(DonacionDineroDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.DONACION_VIANDAS, () -> donacionViandaRepository.buscarTodos().stream()
                            .map(DonacionViandaDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.DISTRIBUCION_VIANDAS, () -> distribucionViandasRepository.buscarTodos().stream()
                            .map(DistribucionViandasDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.HACERSE_CARGO_HELADERA, () -> hacerseCargoHeladeraRepository.buscarTodos().stream()
                            .map(HacerseCargoHeladeraDTO::preview)
                            .collect(Collectors.toList())
            );

            List<Colaboracion> tiposDeColaboracion = List.of(
                    Colaboracion.OFERTA_DE_PRODUCTOS,
                    //Colaboracion.REPARTO_DE_TARJETAS,
                    Colaboracion.DONACION_DINERO,
                    Colaboracion.DONACION_VIANDAS,
                    Colaboracion.DISTRIBUCION_VIANDAS,
                    Colaboracion.HACERSE_CARGO_HELADERA
            );
            for (Colaboracion tipo : tiposDeColaboracion) {
                List<?> dtos = colaboracionHandlers.get(tipo).get();

                if (!dtos.isEmpty()) {
                    model.put(tipo.name().toLowerCase(), dtos);
                }
            }

        } else if (context.sessionAttribute("userRol") == "COLABORADOR") {

            Colaborador colaborador = obtenerColaboradorPorSession(context);

            List<Colaboracion> formasColaborar = colaborador.getFormaDeColaborar();

            Map<Colaboracion, Function<String, List<?>>> colaboracionHandlers = Map.of(
                    Colaboracion.OFERTA_DE_PRODUCTOS, id -> ofertaDeProductosRepository.obtenerPorColaboradorId(id).stream()
                            .map(OfertaDeProductosDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.REPARTO_DE_TARJETAS, id -> repartoDeTarjetasRepository.obtenerPorColaboradorId(id).stream()
                            .map(RepartoDeTarjetasDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.DONACION_DINERO, id -> donacionDineroRepository.obtenerPorColaboradorId(id).stream()
                            .map(DonacionDineroDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.DONACION_VIANDAS, id -> donacionViandaRepository.obtenerPorColaboradorId(id).stream()
                            .map(DonacionViandaDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.DISTRIBUCION_VIANDAS, id -> distribucionViandasRepository.obtenerPorColaboradorId(id).stream()
                            .map(DistribucionViandasDTO::preview)
                            .collect(Collectors.toList()),
                    Colaboracion.HACERSE_CARGO_HELADERA, id -> hacerseCargoHeladeraRepository.obtenerPorColaboradorId(id).stream()
                            .map(HacerseCargoHeladeraDTO::preview)
                            .collect(Collectors.toList())
            );

            for (Colaboracion tipo : formasColaborar) {

                List<?> dtos = colaboracionHandlers.get(tipo).apply(context.sessionAttribute("userId")); // TODO - ver si no c rompio nada

                if (!dtos.isEmpty()) {
                    model.put(tipo.name().toLowerCase(), dtos);
                }
            }
        }
        model.put("titulo", "Listado de colaboraciones");
        context.render("colaboraciones/colaboraciones.hbs", model);
    }
}
