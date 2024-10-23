package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnicaDTO {

    private String id;

    private String heladera;

    private String fecha;

    private String hora;

    private String tipo;

    private String colaborador;

    private String descripcion;

    private String foto;

    // En la vista completa podría mapearse más info del colaborador y la heladera
    // puede servir para redirecciones
    public static FallaTecnicaDTO completa(Incidente incidente) {
        return FallaTecnicaDTO.builder()
                .id(incidente.getId().toString())
                .heladera(incidente.getHeladera().getNombre())
                .fecha(parseFecha(incidente.getFechaHora().toLocalDate()))
                .hora(parseHora(incidente.getFechaHora().toLocalTime()))
                .tipo(incidente.getTipo().toString())
                .colaborador(incidente.getColaborador().getNombre())
                .descripcion(incidente.getDescripcion())
                .foto(incidente.getFoto().getRuta())
                .build();
    }

    public static FallaTecnicaDTO preview(Incidente incidente) {
        return FallaTecnicaDTO.builder()
                .id(incidente.getId().toString())
                .heladera(incidente.getHeladera().getNombre())
                .fecha(parseFecha(incidente.getFechaHora().toLocalDate()))
                .hora(parseHora(incidente.getFechaHora().toLocalTime()))
                .tipo(incidente.getTipo().toString())
                .foto(incidente.getFoto().getRuta())
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
