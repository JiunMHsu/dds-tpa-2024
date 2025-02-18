package ar.edu.utn.frba.dds.utils;

import java.util.Map;

/**
 * Interfaz que define los métodos para la generación de documentos PDF.
 */
//TODO check mayuscula
public interface IPDFGenerator {

  /**
   * Genera un documento PDF con el título y los datos proporcionados.
   *
   * @param title el título del documento
   * @param data  un mapa con los datos a incluir en el documento
   * @return una cadena que representa el contenido del documento PDF generado
   */
  String generateDocument(String title, Map<String, Integer> data);

  /**
   * Genera un documento PDF con secciones, basado en el título y los datos proporcionados.
   *
   * @param title el título del documento
   * @param data  un mapa con las secciones y los datos a incluir en cada sección del documento
   * @return una cadena que representa el contenido del documento PDF generado
   */
  String generateDocumentWithSections(String title, Map<String, Map<String, Integer>> data);
}
