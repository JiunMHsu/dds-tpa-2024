package ar.edu.utn.frba.dds.models.entities.tecnico;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.persistencia.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tecnico")
public class Tecnico extends EntidadPersistente {

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Embedded
    private Documento documento;

    @Column(name = "cuit", unique = true, nullable = false)
    private String cuit;

    @Embedded
    private Contacto contacto;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_notificacion", nullable = false)
    private MedioDeNotificacion medioDeNotificacion;

    @Embedded
    private Area areaDeCobertura;

    public static Tecnico con(String nombre,
                              String apellido,
                              Documento documento,
                              String cuit,
                              Contacto contacto,
                              MedioDeNotificacion medioDeNotificacion,
                              Area areaDeCobertura) {
        return Tecnico
                .builder()
                .nombre(nombre)
                .apellido(apellido)
                .documento(documento)
                .cuit(cuit)
                .contacto(contacto)
                .medioDeNotificacion(medioDeNotificacion)
                .areaDeCobertura(areaDeCobertura)
                .build();
    }

    public static Tecnico con(String nombre,
                              Contacto contacto,
                              MedioDeNotificacion medioDeNotificacion,
                              Area areaDeCobertura) {
        return Tecnico
                .builder()
                .nombre(nombre)
                .contacto(contacto)
                .medioDeNotificacion(medioDeNotificacion)
                .areaDeCobertura(areaDeCobertura)
                .build();
    }

    public static Tecnico con(String nombre,
                              Contacto contacto,
                              Area areaDeCobertura) {
        return Tecnico
                .builder()
                .nombre(nombre)
                .contacto(contacto)
                .areaDeCobertura(areaDeCobertura)
                .build();
    }

}
