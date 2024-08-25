package ar.edu.utn.frba.dds;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

public class AppConfig {
  private static Properties properties;
  private static String archivoDeConfiguracion;

  private static Properties props() {
    if (archivoDeConfiguracion == null) {
      archivoDeConfiguracion = "src/main/resources/config.properties";
    }

    if (properties == null) {
      try {
        FileInputStream propsInput = new FileInputStream(archivoDeConfiguracion);
        properties = new Properties();
        properties.load(propsInput);
      } catch (FileNotFoundException e) {
        System.out.println("No se encontró el archivo de configuración");
        System.exit(1);
      } catch (IOException e) {
        System.out.println("Error de entrada salida");
        System.exit(1);
      }
    }

    return properties;
  }

  public static void setConfigFilePath(String path) {
    archivoDeConfiguracion = path;
    properties = null; // para que en el próximo uso se instancie de nuevo
  }

  public static String getProperty(String key) {
    return AppConfig.props().getProperty(key);
  }

  public static Integer getIntProperty(String key) {
    return Integer.parseInt(AppConfig.props().getProperty(key));
  }
}
