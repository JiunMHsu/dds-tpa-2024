package ar.edu.utn.frba.dds.services.mapa;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servicio para generar GeoJSON de las heladeras.
 */
public class MapService {

  /**
   * Genera un json con las heladeras.
   *
   * @param heladeras lista de heladeras
   * @return stringyfied json
   */
  public String generarGeoJson(List<Heladera> heladeras) {
    try {
      JSONObject geoJson = new JSONObject();
      geoJson.put("type", "FeatureCollection");

      JSONArray features = new JSONArray();
      for (Heladera heladera : heladeras) {
        features.put(crearFeature(heladera));
      }
      geoJson.put("features", features);

      return geoJson.toString();
    } catch (Exception e) {
      throw new RuntimeException("Error al generar el archivo GeoJSON", e);
    }
  }

  private JSONObject crearFeature(Heladera heladera) {
    JSONObject feature = new JSONObject();
    feature.put("type", "Feature");

    JSONObject properties = getJsonObject(heladera);

    feature.put("properties", properties);

    JSONObject geometry = new JSONObject();
    geometry.put("type", "Point");
    JSONArray coordinates = new JSONArray();
    coordinates.put(heladera.getDireccion().getUbicacion().getLongitud());
    coordinates.put(heladera.getDireccion().getUbicacion().getLatitud());
    geometry.put("coordinates", coordinates);

    feature.put("geometry", geometry);

    return feature;
  }

  private static @NotNull JSONObject getJsonObject(Heladera heladera) {
    JSONObject properties = new JSONObject();
    properties.put("id", heladera.getId().toString());
    properties.put("name", heladera.getNombre());
    properties.put("isActive", heladera.estaActiva() ? 1 : 0);

    return properties;
  }


}
