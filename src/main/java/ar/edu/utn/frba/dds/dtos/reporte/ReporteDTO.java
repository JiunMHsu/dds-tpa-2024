package ar.edu.utn.frba.dds.dtos.reporte;

import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReporteDTO {
    private String titulo;
    private String fecha;
    private String pathToPdf;

    public static ReporteDTO completa(Reporte reporte) {
        return ReporteDTO
                .builder()
                .titulo(reporte.getTitulo())
                .fecha(reporte.getFecha().toString())
                .build();
    }

}
