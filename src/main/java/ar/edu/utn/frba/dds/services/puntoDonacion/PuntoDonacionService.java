package ar.edu.utn.frba.dds.services.puntoDonacion;

import ar.edu.utn.frba.dds.exceptions.BadAPIRequestException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class PuntoDonacionService {

    private final String apiBaseUrl;
    private final String apiAuthToken;

    public PuntoDonacionService(String apiUrl, String authToken) {
        this.apiBaseUrl = apiUrl;
        this.apiAuthToken = authToken;
    }

    // armar la clase respuesta List<PuntoDonacion>
    public void obneterPuntoDonacion(Double latitude, Double longitud, Integer limite, Double distanciaMaxEnKM) {

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

        HttpURLConnection conn;
        URL url;

        try {
            url = new URL(urlToFetch);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("authorization", apiAuthToken);
            conn.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // TODO

    }
}
