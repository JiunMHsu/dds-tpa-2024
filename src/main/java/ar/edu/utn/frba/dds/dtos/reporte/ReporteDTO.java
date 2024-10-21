package ar.edu.utn.frba.dds.dtos.reporte;

import ar.edu.utn.frba.dds.models.entities.reporte.Reporte;
import lombok.Builder;

@Builder
public class ReporteDTO {
    private String titulo;
    private String fecha;

    public static ReporteDTO completa(Reporte reporte) {
        return ReporteDTO
                .builder()
                .titulo(reporte.getTitulo())
                .fecha(reporte.getFecha().toString())
                .build();
    }
}
