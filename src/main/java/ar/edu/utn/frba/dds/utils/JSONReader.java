package ar.edu.utn.frba.dds.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Clase que facilita la lectura de datos de un archivo JSON.
 */
//TODO check mayuscula
public class JSONReader {

  private final JSONObject jsonObject;

  /**
   * Constructor que lee y parsea un archivo JSON desde un InputStream.
   *
   * @param inputStream el InputStream desde el que se lee el JSON
   */
  public JSONReader(InputStream inputStream) {
    String json = this.scan(inputStream);
    this.jsonObject = this.parse(json);
  }

  /**
   * Escanea el InputStream y convierte su contenido a una cadena JSON.
   *
   * @param inputStream el InputStream a escanear
   * @return el contenido del InputStream como una cadena JSON
   */
  private String scan(InputStream inputStream) {
    StringBuilder jsonBuilder = new StringBuilder();
    Scanner scanner = new Scanner(inputStream);

    while (scanner.hasNext()) {
      jsonBuilder.append(scanner.nextLine());
    }
    scanner.close();

    return jsonBuilder.toString();
  }

  /**
   * Parsea una cadena JSON a un objeto JSONObject.
   *
   * @param jsonString la cadena JSON a parsear
   * @return el objeto JSONObject resultante
   */
  private JSONObject parse(String jsonString) {
    JSONParser parse = new JSONParser();
    try {
      return (JSONObject) parse.parse(jsonString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Lee un arreglo JSON asociado a una clave específica y
   * lo convierte en una lista de objetos JSON.
   *
   * @param key la clave del arreglo JSON a leer
   * @return una lista de objetos JSON del arreglo asociado a la clave proporcionada
   */
  public List<JSONObject> readArray(String key) {
    List<JSONObject> list = new ArrayList<>();
    JSONArray array = (JSONArray) this.jsonObject.get(key);

    for (Object object : array) {
      list.add((JSONObject) object);
    }

    return list;
  }

}
