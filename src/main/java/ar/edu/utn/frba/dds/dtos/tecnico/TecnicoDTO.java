package ar.edu.utn.frba.dds.dtos.tecnico;

import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TecnicoDTO {

    private String id;

    private String nombre;

    private String apellido;

    private String documento;

    private String cuit;

    private String contacto;

    private String medioDeNotificacion;

    private String areaDeCobertura;

    public static TecnicoDTO completa(Tecnico tecnico) {

        return TecnicoDTO
                .builder()
                .id(tecnico.getId().toString())
                .nombre(tecnico.getNombre())
                .apellido(tecnico.getApellido())
                .documento(tecnico.getDocumento().getNumero())
                .cuit(tecnico.getCuit())
//                .contacto(tecnico.getContacto()) // TODO - ver como hacer
                .medioDeNotificacion(tecnico.getMedioDeNotificacion().toString())
                .areaDeCobertura(tecnico.getAreaDeCobertura().getBarrio().getNombre())
                .build();
    }

    public static TecnicoDTO preview(Tecnico tecnico) {

        return TecnicoDTO
                .builder()
                .id(tecnico.getId().toString())
                .nombre(tecnico.getNombre())
                .apellido(tecnico.getApellido())
                .areaDeCobertura(tecnico.getAreaDeCobertura().getBarrio().getNombre())
                .build();
    }

}
