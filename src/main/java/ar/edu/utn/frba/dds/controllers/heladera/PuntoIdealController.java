package ar.edu.utn.frba.dds.controllers.heladera;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;

public class PuntoIdealController {

  public void create(Context context) {
    try {

      // TODO - recuperar los datos del form

      String latitudQuery = "lat=";
      String longitudQuery = "lon=";
      String radioQuery = "radio=";

      context.redirect("/heladeras/new?"
          + latitudQuery
          + "&" + longitudQuery
          + "&" + radioQuery);

    } catch (ValidationException e) {
      // error
      context.status(400);
    }
  }
}
