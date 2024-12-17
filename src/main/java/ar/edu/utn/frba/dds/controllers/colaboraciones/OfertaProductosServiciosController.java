package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ColaboracionDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.NonColaboratorException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.exceptions.UnauthorizedException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.permissions.ColaboradorRequired;
import ar.edu.utn.frba.dds.services.colaboraciones.OfertaProductosServiciosService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.images.ImageService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import io.javalin.validation.ValidationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OfertaProductosServiciosController extends ColaboradorRequired implements ICrudViewsHandler {

    private final OfertaProductosServiciosService ofertaProductosServiciosService;
    private final ImageService imageService;


    public OfertaProductosServiciosController(UsuarioService usuarioService,
                                              ColaboradorService colaboradorService,
                                              OfertaProductosServiciosService ofertaProductosServiciosService,
                                              ImageService imageService) {

        super(usuarioService, colaboradorService);
        this.ofertaProductosServiciosService = ofertaProductosServiciosService;
        this.imageService = imageService;
    }

    @Override
    public void index(Context context) { // TODO - Revisar
        List<OfertaDeProductos> productos = this.ofertaProductosServiciosService.buscarTodos();

        List<ColaboracionDTO> ofertaDeProductosDTOS = productos.stream()
                .map(OfertaDeProductosDTO::preview)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("colaboraciones", ofertaDeProductosDTOS);
        model.put("titulo", "Listado por productos/servicios");

        context.render("colaboraciones/colaboraciones.hbs", model);
    }

    @Override
    public void show(Context context) { // TODO - Revisar
        String ofertaProductoId = context.pathParam("id");
        Optional<OfertaDeProductos> ofertaDeProductos = this.ofertaProductosServiciosService.buscarPorId(ofertaProductoId);

        if (ofertaDeProductos.isEmpty())
            throw new ResourceNotFoundException("No se encontró ninguna oferta por producto/servicio paraColaborador id " + ofertaProductoId);


        Map<String, Object> model = new HashMap<>();
        OfertaDeProductosDTO ofertaDeProductosDTO = OfertaDeProductosDTO.completa(ofertaDeProductos.get());

        model.put("oferta_producto_servicio", ofertaDeProductosDTO);

        context.render("canje_de_puntos/producto_detalle.hbs", model);

    }

    @Override
    public void create(Context context) {
        Colaborador colaborador = colaboradorFromSession(context);

        if (!colaborador.puedeColaborar(TipoColaboracion.OFERTA_DE_PRODUCTOS))
            throw new UnauthorizedException("No tienes permiso");

        List<String> rubros = Arrays.stream(RubroOferta.values())
                .map(Enum::toString).toList();

        Map<String, Object> model = new HashMap<>();
        model.put("rubros", rubros);
        render(context, "colaboraciones/oferta_prod_serv_crear.hbs", model);
    }

    @Override
    public void save(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            Colaborador colaborador = colaboradorFromSession(context);

            String nombre = context.formParamAsClass("nombre", String.class).get();
            Double puntosNecesarios = context.formParamAsClass("puntos", Double.class).get();
            RubroOferta rubro = RubroOferta.valueOf(context.formParamAsClass("rubro", String.class).get());

            UploadedFile uploadedFile = context.uploadedFile("imagen");
            if (uploadedFile == null) throw new InvalidFormParamException();
            String pathImagen = imageService.guardarImagen(uploadedFile.content(), uploadedFile.extension());

            OfertaDeProductos oferta = OfertaDeProductos.por(
                    colaborador, LocalDateTime.now(), nombre, puntosNecesarios, rubro, new Imagen(pathImagen));

            this.ofertaProductosServiciosService.registrar(oferta);

            // TODO - Delegar a Service??
            colaborador.invalidarPuntos();
            this.colaboradorService.actualizar(colaborador);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Seguir Colaborando"));

        } catch (NonColaboratorException e) {
            throw new UnauthorizedException();
        } catch (ValidationException | InvalidFormParamException | IOException e) {
            redirectDTOS.add(new RedirectDTO(context.fullUrl(), "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);
            context.render("post_result.hbs", model);
        }
    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {
        String ofertaProductoId = context.pathParam("id");

        Optional<OfertaDeProductos> posibleOfertaAEliminar = this.ofertaProductosServiciosService.buscarPorId(ofertaProductoId);

        if (posibleOfertaAEliminar.isEmpty())
            throw new ResourceNotFoundException("No se encontró ninguna oferta por producto/servicio paraColaborador id " + ofertaProductoId);

        Colaborador colaboradorOfertante = posibleOfertaAEliminar.get().getColaborador();

        Colaborador colaboradorSession = colaboradorFromSession(context);

        if (colaboradorOfertante != colaboradorSession) {
            throw new UnauthorizedException("No tiene permiso paraColaborador eliminar la oferta");
        }

        this.ofertaProductosServiciosService.eliminar(posibleOfertaAEliminar.get());
        context.status(HttpStatus.OK);
        // TODO mostrar algo por exitoso?

    }

}
