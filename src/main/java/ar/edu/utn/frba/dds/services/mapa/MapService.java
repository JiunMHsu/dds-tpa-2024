package ar.edu.utn.frba.dds.services.mapa;

import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapService {

  private final HeladeraRepository heladeraRepository;

  public MapService(HeladeraRepository heladeraRepository) {
    this.heladeraRepository = heladeraRepository;
  }

  public String crearGeoJson() {
    try {
      List<Heladera> heladeras = heladeraRepository.buscarTodos();

      JSONObject geoJson = new JSONObject();
      geoJson.put("type", "FeatureCollection");

      JSONArray features = new JSONArray();
      for (Heladera heladera : heladeras) {
        features.put(crearFeature(heladera));
      }
      geoJson.put("features", features);

      return guardarArchivo(geoJson);
    } catch (Exception e) {
      throw new RuntimeException("Error al generar el archivo GeoJSON", e);
    }
  }

  private JSONObject crearFeature(Heladera heladera) {
    JSONObject feature = new JSONObject();
    feature.put("type", "Feature");

    JSONObject properties = new JSONObject();
    properties.put("id", heladera.getId());
    properties.put("name", heladera.getNombre());
    properties.put("barrio", heladera.getDireccion().getBarrio().getNombre());
    properties.put("calle", heladera.getDireccion().getCalle().getNombre());
    properties.put("altura", heladera.getDireccion().getAltura());
    properties.put("isActive", heladera.getEstado() == EstadoHeladera.ACTIVA ? 1 : 0);
    properties.put("capacidad", heladera.getCapacidad());
    properties.put("disponibilidad", heladera.getViandas());
    properties.put("temperatura_max", heladera.getRangoTemperatura().getMaxima());
    properties.put("temperatura_min", heladera.getRangoTemperatura().getMinima());

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

  private String guardarArchivo(JSONObject geoJson) throws Exception {
    Path carpeta = Paths.get("src", "main", "resources", "public", "json");
    Files.createDirectories(carpeta);

    Path rutaArchivo = carpeta.resolve("heladeras.json");

    String contenido = geoJson.toString(4);
    Files.writeString(rutaArchivo, contenido, StandardCharsets.UTF_8);

    return carpeta.relativize(rutaArchivo).toString();
  }
}
