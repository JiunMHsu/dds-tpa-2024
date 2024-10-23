package ar.edu.utn.frba.dds.dtos.incidente;

import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FallaTecnicaDTO {

    private String id;

    private String heladera;

    private String fechaHora;

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
                .fechaHora(incidente.getFechaHora().toString())
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
                .fechaHora(incidente.getFechaHora().toString())
                .tipo(incidente.getTipo().toString())
                .foto(incidente.getFoto().getRuta())
                .build();
    }
}
