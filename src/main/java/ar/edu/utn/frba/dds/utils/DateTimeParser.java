package ar.edu.utn.frba.dds.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilidad para formatear y parsear fechas y horas.
 */
public class DateTimeParser {

  /**
   * Formatea un objeto LocalDate en una cadena con el formato "dd/MM/yyyy".
   *
   * @param fecha Fecha a formatear.
   * @return La fecha formateada como String, o "--/--/--" si es null.
   */
  public static String parseFecha(LocalDate fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return fecha != null ? fecha.format(formatter) : "--/--/--";
  }

  /**
   * Formatea un objeto LocalTime en una cadena con el formato "HH:mm".
   *
   * @param hora Hora a formatear.
   * @return La hora formateada como String, o "--:--" si es null.
   */
  public static String parseHora(LocalTime hora) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return hora != null ? hora.format(formatter) : "--:--";
  }

  /**
   * Formatea un objeto LocalDateTime en una cadena con el formato "dd/MM/yyyy HH:mm".
   *
   * @param fechaHora Fecha y hora a formatear.
   * @return La fecha y hora formateadas como String, o "--/--/-- --:--" si es null.
   */
  public static String parseFechaHora(LocalDateTime fechaHora) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return fechaHora != null ? fechaHora.format(formatter) : "--/--/-- --:--";
  }

  /**
   * Convierte una cadena con formato "yyyy-MM-dd'T'HH:mm" en un objeto LocalDateTime.
   *
   * @param fecha Cadena que representa la fecha y hora en formato ISO de formularios.
   * @return Un objeto LocalDateTime parseado, o null si la cadena es null.
   */
  public static LocalDateTime fromFormInput(String fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    return fecha != null ? LocalDateTime.parse(fecha, formatter) : null;
  }
}
