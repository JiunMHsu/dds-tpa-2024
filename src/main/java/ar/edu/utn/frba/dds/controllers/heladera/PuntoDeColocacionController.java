package ar.edu.utn.frba.dds.controllers.heladera;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

/**
 * Controlador para la creación de un punto de colocación de heladeras.
 */
public class PuntoDeColocacionController {

  /**
   * Lee los parámetros de la petición y redirige a la creación de la heladera.
   *
   * @param context Contexto de Javalin
   */
  public void create(Context context) {
    try {
      Double latitud = context.formParamAsClass("latitud", Double.class).get();
      Double longitud = context.formParamAsClass("longitud", Double.class).get();
      Integer radio = context.formParamAsClass("radio", Integer.class).get();

      String latitudQuery = "lat=" + latitud;
      String longitudQuery = "lon=" + longitud;
      String radioQuery = "radio=" + radio;

      context.redirect("/heladeras/new?"
          + latitudQuery
          + "&" + longitudQuery
          + "&" + radioQuery);

    } catch (ValidationException e) {
      context.status(400);
    }
  }
}
