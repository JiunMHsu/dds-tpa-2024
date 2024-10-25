package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;

import java.util.List;
import java.util.Optional;

public class OfertaProductosServiciosService {
    private final OfertaDeProductosRepository ofertaDeProductosRepository;

    public OfertaProductosServiciosService(OfertaDeProductosRepository ofertaDeProductosRepository) {
        this.ofertaDeProductosRepository = ofertaDeProductosRepository;
    }
    public void guardar(OfertaDeProductos oferta){this.ofertaDeProductosRepository.guardar(oferta);}
    public void eliminar(OfertaDeProductos oferta){this.ofertaDeProductosRepository.eliminar(oferta);}
    public Optional<OfertaDeProductos> buscarPorId(String id){return this.ofertaDeProductosRepository.buscarPorId(id);}

    public List<OfertaDeProductos> buscarTodos(){return this.ofertaDeProductosRepository.buscarTodos();}
}
