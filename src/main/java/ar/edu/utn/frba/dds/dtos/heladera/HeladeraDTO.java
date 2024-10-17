package ar.edu.utn.frba.dds.dtos.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HeladeraDTO {
    private String id;

    private String nombre;

    private String estado;

    private String calleYAltura;

    private String barrio;

    private String capacidad;

    private String ubicacion;

    private String cantViandas; // cambie nombre, un poco mas expresivo

    private String fechaInicio;

    private String ultimaTemp;

    private String temperaturaMaxima;

    private String temperaturaMinima;

    public static HeladeraDTO completa(Heladera heladera) {

        String direccionString = heladera.getDireccion().getCalle().getNombre() + " " + heladera.getDireccion().getAltura().toString();

        String latitudLongitudString = heladera.getDireccion().getUbicacion().getLatitud() + ", " + heladera.getDireccion().getUbicacion().getLongitud();

        return HeladeraDTO
                .builder()
                .id(heladera.getId().toString())
                .nombre(heladera.getNombre())
                .estado(heladera.getEstado().toString())
                .calleYAltura(direccionString)
                .barrio(heladera.getDireccion().getBarrio().getNombre())
                .capacidad(heladera.getCapacidad().toString())
                .ubicacion(latitudLongitudString)
                .cantViandas(heladera.getViandas().toString())
                .fechaInicio(heladera.getInicioFuncionamiento().toString())
                .ultimaTemp(heladera.getUltimaTemperatura().toString())
                .temperaturaMaxima(heladera.getRangoTemperatura().getMaxima().toString())
                .temperaturaMinima(heladera.getRangoTemperatura().getMinima().toString())
                .build();
    }

    public static HeladeraDTO preview(Heladera heladera) {

        String direccionString = heladera.getDireccion().getCalle().getNombre() + " " + heladera.getDireccion().getAltura().toString();

        String latitudLongitudString = heladera.getDireccion().getUbicacion().getLatitud() + ", " + heladera.getDireccion().getUbicacion().getLongitud();

        return HeladeraDTO
                .builder()
                .id(heladera.getId().toString())
                .nombre(heladera.getNombre())
                .estado(heladera.getEstado().toString())
                .calleYAltura(direccionString)
                .ubicacion(latitudLongitudString)
                .cantViandas(heladera.getViandas().toString())
                .build();
    }
}
