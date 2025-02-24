package ar.edu.utn.frba.dds.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Gestiona la configuración de la aplicación mediante la carga de propiedades desde un archivo
 * externo, implementando el patrón Singleton.
 */
public class AppProperties {

  private static AppProperties instance = null;
  private final Properties props;

  private AppProperties() {
    this.props = new Properties();
    this.readProperties();
  }

  /**
   * Obtiene la instancia única de AppProperties.
   *
   * @return La instancia única de AppProperties.
   */
  public static AppProperties getInstance() {
    if (instance == null) {
      instance = new AppProperties();
    }
    return instance;
  }

  private void readProperties() {
    Path devConfigPath = Paths.get("src/main/resources/config.properties");

    try {
      InputStream input;
      if (Files.exists(devConfigPath)) {
        input = new FileInputStream("src/main/resources/config.properties");
      } else {
        input = new FileInputStream("/home/ubuntu/app/config.properties");
      }

      this.props.load(input);
    } catch (FileNotFoundException e) {
      System.out.println("No se encontró el archivo de configuración");
      e.printStackTrace();
      System.exit(1);
    } catch (IOException e) {
      System.out.println("Error de entrada salida");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Obtiene el valor de una propiedad como cadena de texto a partir de un nombre.
   *
   * @param name Nombre de la propiedad.
   * @return Valor de la propiedad como String, o null si no existe.
   */
  public String propertyFromName(String name) {
    return this.props.getProperty(name);
  }

  /**
   * Obtiene una propiedad como un número entero a través de su nombre.
   *
   * @param name Nombre de la propiedad.
   * @return Valor de la propiedad como Integer.
   * @throws NumberFormatException si el valor no es un número válido.
   */
  public Integer intPropertyFromName(String name) {
    return Integer.parseInt(propertyFromName(name));
  }

  /**
   * Obtiene una propiedad como un valor booleano a través de su nombre.
   *
   * @param name Nombre de la propiedad.
   * @return true si el valor es "true", false en caso contrario.
   */
  public Boolean boolPropertyFromName(String name) {
    return Boolean.parseBoolean(propertyFromName(name));
  }
}
