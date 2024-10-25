package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.RedirectDTO;
import ar.edu.utn.frba.dds.exceptions.CargaMasivaException;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.services.colaboraciones.ColaboracionService;
import ar.edu.utn.frba.dds.services.colaborador.ColaboradorService;
import ar.edu.utn.frba.dds.services.usuario.UsuarioService;
import ar.edu.utn.frba.dds.utils.ColaboradorPorSession;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ColaboracionController extends ColaboradorPorSession {

    private final ColaboracionService colaboracionService;

    public ColaboracionController(UsuarioService usuarioService,
                                  ColaboradorService colaboradorService,
                                  ColaboracionService colaboracionService) {
        super(usuarioService, colaboradorService);
        this.colaboracionService = colaboracionService;
    }

    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();

        Boolean isAdmin = Objects.equals(context.sessionAttribute("userRol"), TipoRol.ADMIN.toString());
        Boolean isColaborador = Objects.equals(context.sessionAttribute("userRol"), TipoRol.COLABORADOR.toString());

        model.put("isAdmin", isAdmin);
        model.put("isColaborador", isColaborador);
        context.render("colaboraciones/colaboraciones.hbs", model);
    }

    public void cargarColaboraciones(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<RedirectDTO> redirectDTOS = new ArrayList<>();
        boolean operationSuccess = false;

        try {
            UploadedFile uploadedFile = context.uploadedFile("csv");
            if (uploadedFile == null) throw new InvalidFormParamException();

            colaboracionService.cargarColaboraciones(uploadedFile.content());

            operationSuccess = true;
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Ver Colaboraciones"));

        } catch (ValidationException | CargaMasivaException e) {
            System.out.println(e.getMessage());
            redirectDTOS.add(new RedirectDTO("/colaboraciones", "Reintentar"));
        } finally {
            model.put("success", operationSuccess);
            model.put("redirects", redirectDTOS);

            context.render("post_result.hbs", model);
        }
    }
}
