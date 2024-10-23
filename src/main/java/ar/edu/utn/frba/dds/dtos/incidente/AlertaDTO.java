package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AlertaDTO {

    private String id;

    private String heladera;

    private String fecha;

    private String hora;

    private String tipo;

    // En la vista completa podría mapearse más info del colaborador y la heladera
    // puede servir para redirecciones
    public static AlertaDTO completa(Incidente incidente) {


        return AlertaDTO.builder()
                .id(incidente.getId().toString())
                .heladera(incidente.getHeladera().getNombre())
                .fecha(parseFecha(incidente.getFechaHora().toLocalDate()))
                .hora(parseHora(incidente.getFechaHora().toLocalTime()))
                .tipo(incidente.getTipo().getDescription())
                .build();
    }

    public static AlertaDTO preview(Incidente incidente) {
        return AlertaDTO.builder()
                .id(incidente.getId().toString())
                .heladera(incidente.getHeladera().getNombre())
                .fecha(parseFecha(incidente.getFechaHora().toLocalDate()))
                .hora(parseHora(incidente.getFechaHora().toLocalTime()))
                .tipo(incidente.getTipo().getDescription())
                .build();
    }

    private static String parseFecha(LocalDate fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha != null ? fecha.format(formatter) : "--/--/--";
    }

    private static String parseHora(LocalTime hora) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return hora != null ? hora.format(formatter) : "--:--";
    }
}
