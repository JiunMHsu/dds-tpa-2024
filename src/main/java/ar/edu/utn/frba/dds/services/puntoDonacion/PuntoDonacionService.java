package ar.edu.utn.frba.dds.services.puntoDonacion;

import ar.edu.utn.frba.dds.exceptions.BadAPIRequestException;
import ar.edu.utn.frba.dds.models.entities.puntoDeDonacion.PuntoDeDonacion;
import ar.edu.utn.frba.dds.utils.JSONReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.simple.JSONObject;

/**
 * Servicio de puntos de donacion.
 */
public class PuntoDonacionService {

  private final String apiBaseUrl;
  private final String apiAuthToken;

  /**
   * Constructor.
   *
   * @param apiUrl    URL de la API.
   * @param authToken Token de autenticación.
   */
  public PuntoDonacionService(String apiUrl, String authToken) {
    this.apiBaseUrl = apiUrl;
    this.apiAuthToken = authToken;
  }

  /**
   * Obtiene los puntos de donación cercanos a una ubicación.
   *
   * @param latitude         Latitud.
   * @param longitud         Longitud.
   * @param limite           Límite de resultados.
   * @param distanciaMaxEnKm Distancia máxima en kilómetros.
   * @return Lista de puntos de donación.
   */
  public List<PuntoDeDonacion> obneterPuntoDonacion(Double latitude,
                                                    Double longitud,
                                                    Integer limite,
                                                    Double distanciaMaxEnKm) {

    if (latitude == null || longitud == null) {
      throw new BadAPIRequestException("Latitude and longitud are required");
    }

    if (limite != null && limite < 0) {
      throw new BadAPIRequestException("Limite negativo");
    }

    if (distanciaMaxEnKm != null && distanciaMaxEnKm < 0) {
      throw new BadAPIRequestException("Distancia max en km negativo");
    }

    String coordinatesQueryParam = "lat=" + latitude + "&lon=" + longitud;
    String limiteQueryParam = "&limite=" + limite;
    String distanciaQueryParam = "&distanciaMaxEnKM=" + distanciaMaxEnKm;

    String urlToFetch = apiBaseUrl
        + "/locaciones-donacion?"
        + coordinatesQueryParam
        + limiteQueryParam
        + distanciaQueryParam;

    try {
      JSONReader jsonResponse = getResponse(urlToFetch);
      if (jsonResponse == null) {
        return List.of();
      }
      List<JSONObject> lugares = jsonResponse.readArray("lugares");

      return lugares
          .stream()
          .map(objectLugar -> PuntoDeDonacion
              .builder()
              .id((Long) objectLugar.get("id"))
              .nombre((String) objectLugar.get("nombre"))
              .latitud((Double) objectLugar.get("lat"))
              .longitud((Double) objectLugar.get("lon"))
              .distanciaEnKm(Double.parseDouble(objectLugar.get("distanciaEnKM").toString()))
              .build())
          .toList();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  private JSONReader getResponse(String urlToFetch) throws IOException {
    HttpURLConnection conn = null;

    try {
      URL url = new URL(urlToFetch);
      conn = (HttpURLConnection) url.openConnection();

      System.out.println(apiAuthToken);

      conn.setRequestProperty("authorization", apiAuthToken);
      conn.setRequestMethod("GET");

      return new JSONReader(conn.getInputStream());

    } catch (IOException e) {

      if (conn == null) {
        throw e;
      }

      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
        throw new BadAPIRequestException("No credentials provided");
      }

      if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
        throw new BadAPIRequestException("Invalid API Key");
      }
    }

    return null;
  }
}
