package ar.edu.utn.frba.dds.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {

  public static String parseFecha(LocalDate fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return fecha != null ? fecha.format(formatter) : "--/--/--";
  }

  public static String parseHora(LocalTime hora) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return hora != null ? hora.format(formatter) : "--:--";
  }

  public static String parseFechaHora(LocalDateTime fechaHora) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    return fechaHora != null ? fechaHora.format(formatter) : "--/--/-- --:--";
  }

  public static LocalDateTime fromFormInput(String fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    return fecha != null ? LocalDateTime.parse(fecha, formatter) : null;
  }
}
