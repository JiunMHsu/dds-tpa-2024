package ar.edu.utn.frba.dds.controllers.heladera;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

/**
 * Controlador para la creación de un punto de colocación de heladeras.
 */
public class PuntoDeColocacionController {

  /**
   * Crea un nuevo punto de colocación de heladeras.
   *
   * @param context Contexto HTTP
   */
  public void create(Context context) {
    try {
      // TODO: recuperar los datos del form?

      String latitudQuery = "lat=";
      String longitudQuery = "lon=";
      String radioQuery = "radio=";

      context.redirect("/heladeras/new?"
          + latitudQuery
          + "&" + longitudQuery
          + "&" + radioQuery);

    } catch (ValidationException e) {
      context.status(400);
    }
  }
}
