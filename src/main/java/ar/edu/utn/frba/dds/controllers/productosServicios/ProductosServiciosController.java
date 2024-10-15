package ar.edu.utn.frba.dds.controllers.productosServicios;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.Optional;

public class ProductosServiciosController implements ICrudViewsHandler {

    private OfertaDeProductosRepository ofertaDeProductosRepository;

    public ProductosServiciosController(OfertaDeProductosRepository ofertaDeProductosRepository) {
        this.ofertaDeProductosRepository = ofertaDeProductosRepository;
    }

    @Override
    public void index(Context context){

    }
    @Override
    public void show(Context context){

    }
    @Override
    public void create(Context context){

    }
    @Override
    public void save(Context context){

    }
    @Override
    public void edit(Context context){

    }
    @Override
    public void update(Context context){

    }
    @Override
    public void delete(Context context){

    }

}
