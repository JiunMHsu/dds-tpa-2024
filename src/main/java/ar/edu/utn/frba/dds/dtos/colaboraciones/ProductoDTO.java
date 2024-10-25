package ar.edu.utn.frba.dds.dtos.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ProductoDTO{

    private String id;

    private String nombreProducto;

    private String rubro;

    private String pathImagen;

    public static ProductoDTO completa(OfertaDeProductos producto) {
        return ProductoDTO
                .builder()
                .id(producto.getId().toString())
                .nombreProducto(producto.getNombre())
                .rubro(producto.getRubro().toString())
                .pathImagen(producto.getImagen().getRuta())
                .build();
    }

    public static ProductoDTO preview(OfertaDeProductos producto) {

        return ProductoDTO
                .builder()
                .id(producto.getId().toString())
                .nombreProducto(producto.getNombre())
                .pathImagen(producto.getImagen().getRuta())
                .build();
    }
}
