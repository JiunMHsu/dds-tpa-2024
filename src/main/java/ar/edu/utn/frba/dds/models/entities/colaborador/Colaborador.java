package ar.edu.utn.frba.dds.models.entities.colaborador;

import ar.edu.utn.frba.dds.models.entities.colaboracion.Colaboracion;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.formulario.FormularioRespondido;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.utils.EntidadPersistente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name = "colaborador")
public class Colaborador extends EntidadPersistente {

    @ManyToOne
    @JoinColumn(name = "tipo_colaborador_id")
    private TipoColaborador tipoColaborador;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Embedded
    private Contacto contacto;

    @Embedded
    private Direccion direccion;

    @OneToOne
    @JoinColumn(name = "formulario_respondido_id")
    private FormularioRespondido datosAdicionales;

    @ElementCollection
    @CollectionTable(name = "formas_de_colaborar", joinColumns = @JoinColumn(name = "colaborador_id"))
    @Column(name = "colaboracion")
    private List<Colaboracion> formaDeColaborar;

    @Column(name = "razon_social")
    private String razonSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoRazonSocial tipoRazonSocial;

    @Column(name = "rubro")
    private String rubro;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fecha_nacimiento", columnDefinition = "DATE")
    private LocalDate fechaNacimiento;

    public static Colaborador juridica(Usuario usuario,
                                       String razonSocial,
                                       TipoRazonSocial tipoRazonSocial,
                                       String rubro,
                                       Contacto contacto,
                                       Direccion direccion,
                                       List<Colaboracion> formaDeColaborar) {
        return Colaborador
                .builder()
                .usuario(usuario)
                .razonSocial(razonSocial)
                .tipoRazonSocial(tipoRazonSocial)
                .rubro(rubro)
                .contacto(contacto)
                .direccion(direccion)
                .formaDeColaborar(formaDeColaborar)
                .build();
    }

    public static Colaborador juridica(Usuario usuario,
                                       String razonSocial,
                                       TipoRazonSocial tipoRazonSocial,
                                       Contacto contacto,
                                       Direccion direccion) {
        return Colaborador.juridica(
                usuario,
                razonSocial,
                tipoRazonSocial,
                "",
                contacto,
                direccion,
                new ArrayList<>()
        );
    }

    public static Colaborador humana(Usuario usuario,
                                     String nombre,
                                     String apellido,
                                     LocalDate fechaNacimiento,
                                     Contacto contacto,
                                     Direccion direccion,
                                     List<Colaboracion> formaDeColaborar) {
        return Colaborador
                .builder()
                .usuario(usuario)
                .nombre(nombre)
                .apellido(apellido)
                .fechaNacimiento(fechaNacimiento)
                .contacto(contacto)
                .direccion(direccion)
                .formaDeColaborar(formaDeColaborar)
                .build();
    }

    public static Colaborador humana(Usuario usuario,
                                     String nombre,
                                     String apellido,
                                     LocalDate fechaNacimiento) {
        return Colaborador.humana(
                usuario,
                nombre,
                apellido,
                fechaNacimiento,
                null,
                null,
                new ArrayList<>()
        );
    }

    public static Colaborador colaborador(Usuario usuario) {
        return Colaborador
                .builder()
                .usuario(usuario)
                .build();
    }

}
