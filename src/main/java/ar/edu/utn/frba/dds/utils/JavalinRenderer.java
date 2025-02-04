package ar.edu.utn.frba.dds.utils;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Clase que implementa el renderizador de archivos para Javalin.
 * Permite registrar renderizadores personalizados para diferentes extensiones de archivos.
 */
public class JavalinRenderer implements FileRenderer {

  private final Map<String, FileRenderer> renderers = new HashMap<>();

  /**
   * Registra un renderizador para una extensión de archivo específica.
   *
   * @param extension la extensión del archivo (por ejemplo, "html", "json")
   * @param renderer  el renderizador a registrar
   * @return la instancia actual de JavalinRenderer
   */
  public JavalinRenderer register(String extension, FileRenderer renderer) {
    renderers.put(extension, renderer);
    return this;
  }

  @NotNull
  @Override
  public String render(@NotNull String s, @NotNull Map<String, ?> map, @NotNull Context context) {
    String extension = s.substring(s.lastIndexOf(".") + 1);
    return renderers.get(extension).render(s, map, context);
  }
}
