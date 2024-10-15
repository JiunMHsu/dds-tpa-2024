package ar.edu.utn.frba.dds.controllers.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.models.entities.colaboracion.*;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.*;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ColaboracionController {
    private DistribucionViandasRepository distribucionViandasRepository;
    private DonacionDineroRepository donacionDineroRepository;
    private DonacionViandaRepository donacionViandaRepository;
    private OfertaDeProductosRepository ofertaDeProductosRepository;
    private HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
    private RepartoDeTarjetasRepository repartoDeTarjetasRepository;

    public void index(Context context){
        if(context.sessionAttribute("userRol") == "ADMIN"){

        }
        else if(context.sessionAttribute("userRol") == "COLABORADOR"){
            UUID id = UUID.fromString(context.sessionAttribute("userId"));

            List<OfertaDeProductos> ofertaDeProductos = this.ofertaDeProductosRepository.obtenerPorColaboradorId(id);
            List<RepartoDeTarjetas> repartoDeTarjetas = this.repartoDeTarjetasRepository.obtenerPorColaboradorId(id);
            List<DonacionDinero> donacionDineros = this.donacionDineroRepository.obtenerPorColaboradorId(id);
            List<DonacionVianda> donacionViandas = this.donacionViandaRepository.obtenerPorColaboradorId(id);
            List<DistribucionViandas> distribucionViandas = this.distribucionViandasRepository.obtenerPorColaboradorId(id);
            List<HacerseCargoHeladera> hacerseCargoHeladeras = this.hacerseCargoHeladeraRepository.obtenerPorColaboradorId(id);

            List<OfertaDeProductosDTO> ofertaDeProductosDTOS = ofertaDeProductos.stream()
                    .map(OfertaDeProductosDTO::preview)
                    .collect(Collectors.toList());

            Map<String, Object> model = new HashMap<>();
            model.put("productos_canjear.hbs", ofertaDeProductos);
            model.put("titulo", "Listado de productos/servicios");
        }

    }
}
