package ar.edu.utn.frba.dds.controllers.tecnico;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.dtos.tecnico.TecnicoDTO;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.data.*;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.services.tecnico.TecnicoService;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

import java.util.*;


public class TecnicoController implements ICrudViewsHandler {

    private final TecnicoService tecnicoService;

    public TecnicoController (TecnicoService tecnicoService) { this.tecnicoService = tecnicoService; }

    @Override
    public void index(Context context) { // TODO - Ver desp que matchee con las vistas
        List<Tecnico> tecnicos = this.tecnicoService.buscarTodos();

        List<TecnicoDTO> tecnicosDTO = tecnicos.stream()
                .map(TecnicoDTO::preview)
                .toList();

        Map<String, Object> model = new HashMap<>();
        model.put("tecnicos", tecnicosDTO);

        // context.render("/", model);
    }

    @Override
    public void show(Context context) { // TODO - Ver desp que matchee con las vistas

        String cuitTecnico = context.pathParam("cuit");
        Optional<Tecnico> tecnicoBuscado = this.tecnicoService.buscarTecnicoPorCuit(cuitTecnico); // TODO - hago que se busque por CUIT, ver si va o no

        if (tecnicoBuscado.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró tecnico con cuit " + cuitTecnico);
        }

        Map<String, Object> model = new HashMap<>();
        TecnicoDTO tecnicoDTO = TecnicoDTO.completa(tecnicoBuscado.get());
        model.put("tecnico", tecnicoDTO);

        // context.render("/", model);
    }

    @Override
    public void create(Context context) {
        // TODO - solo contex.render?
    }

    @Override
    public void save(Context context) { // TODO - Ver desp que matchee con las vistas

        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {

            String nombre = context.formParamAsClass("nombre", String.class).get();
            String apellido = context.formParamAsClass("apellido", String.class).get();

            Documento documento = Documento.with(
                    TipoDocumento.valueOf(context.formParamAsClass("tipo_documento", String.class).get()),
                    context.formParamAsClass("nro_documento", String.class).get()
            );

            String cuit = context.formParamAsClass("cuit", String.class).get();

            MedioDeNotificacion medioDeNotificacion = MedioDeNotificacion.valueOf(context.formParamAsClass("medio-notificacion", String.class).get());

            Contacto contacto = new Contacto(); // TODO - medio brownie, no c si esta bn

            if (medioDeNotificacion.equals(MedioDeNotificacion.EMAIL)) {
                contacto = Contacto.conEmail(context.formParamAsClass("email", String.class).get());
            } else if (medioDeNotificacion.equals(MedioDeNotificacion.WHATSAPP)) {
                contacto = Contacto.conEmail(context.formParamAsClass("whatsapp", String.class).get());
            } else {
                contacto = Contacto.conEmail(context.formParamAsClass("telegram", String.class).get());
            }

            Ubicacion ubicacion = new Ubicacion(
                    context.formParamAsClass("latitud", Double.class).get(),
                    context.formParamAsClass("longitud", Double.class).get()
            );

            Area area = Area.with(
                    ubicacion,
                    Double.valueOf(context.formParamAsClass("radio", Double.class).get()),
                    new Barrio(context.formParamAsClass("barrio", String.class).get())
            );

            Tecnico tecnico = Tecnico.con(
                    nombre,
                    apellido,
                    documento,
                    cuit,
                    contacto,
                    medioDeNotificacion,
                    area
            );

            this.tecnicoService.guardarTecnico(tecnico);

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/home", "Siguiente"));

        } catch (ValidationException e) {
            redirectDTOS.add(new RedirectDTO("/home", "Reintentar"));
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

    }
}
