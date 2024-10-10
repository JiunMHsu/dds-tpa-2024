package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OfertaDeProductosDTO {

    private String nombre;

    private String colaborador;

    private String fechaHora;

    private String nombreProducto;

    private String rubro;

    private String pathImagen;

    public static OfertaDeProductosDTO completa(OfertaDeProductos ofertaDeProductos) {

        String nombre = "Ofrecer Producto o Servicio";

        return OfertaDeProductosDTO
                .builder()
                .nombre(nombre)
                .colaborador(ofertaDeProductos.getColaborador().getUsuario().getNombre())
                .fechaHora(ofertaDeProductos.getFechaHora().toString())
                .nombreProducto(ofertaDeProductos.getNombre())
                .rubro(ofertaDeProductos.getRubro().toString())
                .pathImagen(ofertaDeProductos.getImagen().getRuta())
                .build();
    }

    public static OfertaDeProductosDTO preview(OfertaDeProductos ofertaDeProductos) {

        String nombre = "Ofrecer Producto o Servicio";

        return OfertaDeProductosDTO
                .builder()
                .build();
    }
}
