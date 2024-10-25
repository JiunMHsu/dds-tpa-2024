package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.utils.DateTimeParser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class OfertaDeProductosDTO extends ColaboracionDTO {

    private String id;

    private String colaborador;

    private String fechaHora;

    private String nombreProducto;

    private String rubro;

    private String pathImagen;

    public static OfertaDeProductosDTO completa(OfertaDeProductos ofertaDeProductos) {

        return OfertaDeProductosDTO
                .builder()
                .etiqueta(getAction(Colaboracion.OFERTA_DE_PRODUCTOS))
                .path(getPath(Colaboracion.OFERTA_DE_PRODUCTOS))
                .id(ofertaDeProductos.getId().toString())
                .colaborador(ofertaDeProductos.getColaborador().getUsuario().getNombre())
                .fechaHora(DateTimeParser.parseFechaHora(ofertaDeProductos.getFechaHora()))
                .nombreProducto(ofertaDeProductos.getNombre())
                .rubro(ofertaDeProductos.getRubro().toString())
                .pathImagen(ofertaDeProductos.getImagen().getRuta())
                .build();
    }

    public static OfertaDeProductosDTO preview(OfertaDeProductos ofertaDeProductos) {

        return OfertaDeProductosDTO
                .builder()
                .etiqueta(getAction(Colaboracion.OFERTA_DE_PRODUCTOS))
                .path(getPath(Colaboracion.OFERTA_DE_PRODUCTOS))
                .id(ofertaDeProductos.getId().toString())
                .fechaHora(DateTimeParser.parseFechaHora(ofertaDeProductos.getFechaHora()))
                .build();
    }
}
