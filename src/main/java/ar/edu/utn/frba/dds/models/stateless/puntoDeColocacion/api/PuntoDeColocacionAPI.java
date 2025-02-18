package ar.edu.utn.frba.dds.models.stateless.puntoDeColocacion.api;

/**
 * API de punto de colocación.
 */
// TODO check mayuscula
public class PuntoDeColocacionAPI {
  private final String apiUrl;
  private final String endpoint;

  public PuntoDeColocacionAPI() {
    this.apiUrl = "https://api.puntodecolocacion.com";
    this.endpoint = "/recomendaciones";
  }

  /**
   * Solicitar recomendación de punto de colocación.
   *
   * @param queryString query string
   * @return json string
   */
  public String solicitarRecomendacion(String queryString) {
    // hace la request al API
    System.out.println("Haciendo request a " + this.apiUrl + endpoint + queryString);

    return """
        {
          "puntos": [
            {
              "id": 1,
              "nombre": "Punto 1",
              "latitud": -34.603722,
              "longitud": -58.381592,
            },
            {
              "id": 2,
              "nombre": "Punto 2",
              "latitud": -34.603722,
              "longitud": -58.381592,
            }
          ]
        }""";
  }
}
