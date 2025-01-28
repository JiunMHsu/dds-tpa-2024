package ar.edu.utn.frba.dds.services.puntoDonacion;

import ar.edu.utn.frba.dds.exceptions.BadAPIRequestException;
import ar.edu.utn.frba.dds.models.entities.puntoDeDonacion.PuntoDeDonacion;
import ar.edu.utn.frba.dds.utils.JSONReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.json.simple.JSONObject;

public class PuntoDonacionService {

  private final String apiBaseUrl;
  private final String apiAuthToken;

  public PuntoDonacionService(String apiUrl, String authToken) {
    this.apiBaseUrl = apiUrl;
    this.apiAuthToken = authToken;
  }

  public List<PuntoDeDonacion> obneterPuntoDonacion(Double latitude, Double longitud, Integer limite, Double distanciaMaxEnKM) {

    String coordinatesQueryParam;
    String limiteQueryParam = "";
    String distanciaQueryParam = "";

    if (latitude == null || longitud == null) {
      throw new BadAPIRequestException("Latitude and longitud are required");
    }
    coordinatesQueryParam = "lat=" + latitude + "&lon=" + longitud;

    if (limite != null) {
      if (limite < 0)
        throw new BadAPIRequestException("Limite negativo");

      limiteQueryParam = "&limite=" + limite;
    }

    if (distanciaMaxEnKM != null) {
      if (distanciaMaxEnKM < 0)
        throw new BadAPIRequestException("Distancia max en km negativo");

      distanciaQueryParam = "&distanciaMaxEnKM=" + distanciaMaxEnKM;
    }

    String urlToFetch = apiBaseUrl
        + "/locaciones-donacion?"
        + coordinatesQueryParam
        + limiteQueryParam
        + distanciaQueryParam;

    try {
      JSONReader jsonResponse = getResponse(urlToFetch);
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

      if (conn == null) throw e;

      int responseCode = conn.getResponseCode();

      if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED)
        throw new BadAPIRequestException("No credentials provided");

      if (responseCode == HttpURLConnection.HTTP_FORBIDDEN)
        throw new BadAPIRequestException("Invalid API Key");
    }

    return null;
  }
}
