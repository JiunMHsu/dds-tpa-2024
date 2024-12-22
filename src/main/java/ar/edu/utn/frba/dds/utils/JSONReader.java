package ar.edu.utn.frba.dds.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JSONReader {

  private final JSONObject jsonObject;

  public JSONReader(InputStream inputStream) {
    String json = this.scan(inputStream);
    this.jsonObject = this.parse(json);
  }

  private String scan(InputStream inputStream) {
    StringBuilder jsonBuilder = new StringBuilder();
    Scanner scanner = new Scanner(inputStream);

    while (scanner.hasNext()) jsonBuilder.append(scanner.nextLine());
    scanner.close();

    return jsonBuilder.toString();
  }

  private JSONObject parse(String jsonString) {
    JSONParser parse = new JSONParser();
    try {
      return (JSONObject) parse.parse(jsonString);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  public JSONObject readObject(String key) {
    return (JSONObject) this.jsonObject.get(key);
  }

  public List<JSONObject> readArray(String key) {
    List<JSONObject> list = new ArrayList<>();
    JSONArray array = (JSONArray) this.jsonObject.get(key);

    for (Object object : array) list.add((JSONObject) object);

    return list;
  }

}
