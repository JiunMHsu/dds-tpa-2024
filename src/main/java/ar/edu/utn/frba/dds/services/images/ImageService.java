package ar.edu.utn.frba.dds.services.images;

import ar.edu.utn.frba.dds.utils.AppProperties;
import ar.edu.utn.frba.dds.utils.RandomString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageService {

  private final RandomString randomString;

  public ImageService(RandomString randomString) {
    this.randomString = randomString;
  }

  public String guardarImagen(InputStream imagen, String extension) throws IOException {
    String relativePath = AppProperties.getInstance().propertyFromName("IMAGE_DIR");
    if (relativePath == null) throw new IOException();

    String fileName = randomString.nextString() + extension;
    Path path = Path.of(relativePath, fileName).toAbsolutePath();
    System.out.println(path);

    Files.copy(imagen, path, StandardCopyOption.REPLACE_EXISTING);
    return fileName;
  }

  public File obtenerImagen(String nombre) {
    return null;
  }

}
