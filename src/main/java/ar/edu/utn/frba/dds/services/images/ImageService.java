package ar.edu.utn.frba.dds.services.images;

import ar.edu.utn.frba.dds.utils.AppProperties;
import ar.edu.utn.frba.dds.utils.RandomString;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Servicio para guardar y obtener imágenes del sistema de archivos.
 */
public class ImageService {

  private final RandomString randomString;

  /**
   * Constructor.
   *
   * @param randomString Servicio para generar strings aleatorios.
   */
  public ImageService(RandomString randomString) {
    this.randomString = randomString;
  }

  /**
   * Guarda una imagen en el sistema de archivos.
   *
   * @param imagen    InputStream de la imagen a guardar.
   * @param extension Extensión de la imagen.
   * @return Nombre del archivo guardado.
   * @throws IOException Si no se pudo guardar la imagen.
   */
  public String guardarImagen(InputStream imagen, String extension) throws IOException {

    String relativePath = AppProperties.getInstance().propertyFromName("IMAGE_DIR");
    if (relativePath == null) {
      throw new IOException();
    }

    String fileName = randomString.generate() + extension;
    Path path = Path.of(relativePath, fileName).toAbsolutePath();
    System.out.println(path);

    Files.copy(imagen, path, StandardCopyOption.REPLACE_EXISTING);
    return fileName;
  }

}
