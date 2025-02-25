package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaColaborador;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.VarianteDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.tarjeta.TarjetaColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.services.heladera.SuscriptorSensorService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de inicializar los datos de la aplicación,
 * configurando entidades y registros iniciales.
 * Esto incluye la creación de usuarios, colaboradores,
 * heladeras, incidentes, variantes de puntos y ofertas de productos.
 */
public class Initializer implements WithSimplePersistenceUnit {

  private final UsuarioRepository usuarioRepository;
  private final ColaboradorRepository colaboradorRepository;
  private final ContactoRepository contactoRepository;
  private final HeladeraRepository heladeraRepository;
  private final IncidenteRepository incidenteRepository;
  private final VarianteDePuntosRepository varianteDePuntosRepository;
  private final OfertaDeProductosRepository ofertaDeProductosRepository;
  private final TecnicoRepository tecnicoRepository;

  private Initializer() {
    usuarioRepository = new UsuarioRepository();
    colaboradorRepository = new ColaboradorRepository();
    contactoRepository = new ContactoRepository();
    heladeraRepository = new HeladeraRepository();
    incidenteRepository = new IncidenteRepository();
    varianteDePuntosRepository = new VarianteDePuntosRepository();
    ofertaDeProductosRepository = new OfertaDeProductosRepository();
    tecnicoRepository = new TecnicoRepository();
  }

  /**
   * Seeder del sistema.
   */
  public static void init() {
    Initializer instance = new Initializer();

    instance.cleanupDatabase();
    instance.withSuperUser();
    instance.withColaboradores();
    instance.withHeladeras();
    instance.withIncidentes();
    instance.withVarianteDePuntos();
    instance.withOfertas();
    instance.withTecnicos();
    instance.withTarjetas();
    instance.initializeMqttSubscribers();

    // ServiceLocator.instanceOf(ReporteService.class).generarReporteSemanal();
  }

  /**
   * Crea un usuario con rol de superadministrador y lo guarda en la base de datos.
   */
  public void withSuperUser() {
    Usuario superUser = Usuario.con(
        "Admin del Sistema",
        "0000",
        "utn.dds.g22@gmail.com",
        TipoRol.ADMIN
    );

    UsuarioRepository usuarioRepository = new UsuarioRepository();

    withTransaction(() -> usuarioRepository.guardar(superUser));
  }

  /**
   * Crea una lista de usuarios colaboradores y los guarda junto con sus contactos y direcciones.
   */
  public void withColaboradores() {
    final Usuario u1 = Usuario.con(
        "JiunMHsu", "1111", "jhsu@frba.utn.edu.ar", TipoRol.COLABORADOR);

    final Usuario u4 = Usuario.con(
        "Melselep", "1111", "melperez@frba.utn.edu.ar", TipoRol.COLABORADOR);

    final Direccion direccion = new Direccion(
        new Barrio("Almagro"),
        new Calle("Medrano"),
        951,
        new Ubicacion(-34.59857981526152, -58.420110294464294)
    );

    final List<TipoColaboracion> colabHumana1 = List.of(
        TipoColaboracion.DISTRIBUCION_VIANDAS,
        TipoColaboracion.DONACION_DINERO);

    final List<TipoColaboracion> colabJuridica2 = List.of(
        TipoColaboracion.HACERSE_CARGO_HELADERA,
        TipoColaboracion.OFERTA_DE_PRODUCTOS,
        TipoColaboracion.DONACION_DINERO);

    final List<Contacto> contactos = List.of(Contacto.conWhatsApp("whatsapp:+5491132420699"));

    beginTransaction();
    usuarioRepository.guardar(u1);
    usuarioRepository.guardar(u4);

    contactoRepository.guardar(contactos);

    colaboradorRepository.guardar(Colaborador.humana(
        u1,
        "Jiun Ming",
        "Hsu",
        new Documento(TipoDocumento.DNI, "94474536"),
        LocalDate.of(2003, 2, 19),
        new ArrayList<>(),
        direccion,
        new ArrayList<>(colabHumana1),
        new Puntos(2039, true, null)));

    colaboradorRepository.guardar(Colaborador.juridica(
        u4,
        "MELSELEP SRL",
        TipoRazonSocial.EMPRESA,
        "Música",
        new ArrayList<>(),
        direccion,
        new ArrayList<>(colabJuridica2),
        new Puntos(0, false, null)));

    commitTransaction();
  }

  /**
   * Crea las heladeras y las guarda en la base de datos.
   * Asocia cada heladera con una dirección y configura su temperatura.
   */
  public void withHeladeras() {

    Ubicacion utnLugano = new Ubicacion(-34.65980, -58.46812);
    Ubicacion utnMedrano = new Ubicacion(-34.59862, -58.42011);
    Ubicacion abastoShopping = new Ubicacion(-34.60286, -58.41129);
    Ubicacion plazaSerrano = new Ubicacion(-34.58885, -58.43026);
    Ubicacion plazaItalia = new Ubicacion(-34.58139, -58.42103);

    Barrio b1 = new Barrio("Villa Lugano");
    Barrio b3 = new Barrio("Almagro");
    Barrio b6 = new Barrio("Balvanera");
    Barrio b7 = new Barrio("Palermo");

    Calle c1 = new Calle("Agüero");
    Calle c3 = new Calle("Mozart");
    Calle c5 = new Calle("Avenida Medrano");
    Calle c8 = new Calle("Serrano");
    Calle c12 = new Calle("Avenida Santa Fe");

    final Direccion dirUtnLugano = new Direccion(b1, c3, 2300, utnLugano);
    final Direccion dirUtnMedrano = new Direccion(b3, c5, 951, utnMedrano);
    final Direccion dirAbastoShopping = new Direccion(b6, c1, 611, abastoShopping);
    final Direccion dirPlazaSerrano = new Direccion(b7, c8, 1595, plazaSerrano);
    final Direccion dirPlazaItalia = new Direccion(b7, c12, 4138, plazaItalia);

    String baseTopic = AppProperties.getInstance().propertyFromName("BASE_TOPIC") + "/heladeras/";

    beginTransaction();

    heladeraRepository.guardar(Heladera.con(
        "Heladera UTN Lugano",
        dirUtnLugano,
        80,
        new RangoTemperatura(3.0, -5.0),
        58,
        baseTopic + "heladera-utn-lugano"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Plaza Italia",
        dirPlazaItalia,
        70,
        new RangoTemperatura(5.0, -3.0),
        61,
        baseTopic + "heladera-plaza-italia"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Plaza Serrano",
        dirPlazaSerrano,
        70,
        new RangoTemperatura(3.0, -2.0),
        55,
        baseTopic + "heladera-plaza-serrano"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Abasto Shopping",
        dirAbastoShopping,
        80,
        new RangoTemperatura(3.0, -4.0),
        76,
        baseTopic + "heladera-abasto-shopping"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera UTN Medrano",
        dirUtnMedrano,
        85,
        new RangoTemperatura(3.0, -4.0),
        66,
        baseTopic + "heladera-utn-medrano"));

    commitTransaction();
  }

  private void withIncidentes() {
    final Heladera heladeraUtnMedrano = heladeraRepository
        .buscarPorNombre("Heladera UTN Medrano").orElseThrow();

    final Heladera heladeraAbastoShopping = heladeraRepository
        .buscarPorNombre("Heladera Abasto Shopping").orElseThrow();

    heladeraUtnMedrano.setEstado(EstadoHeladera.INACTIVA);
    heladeraAbastoShopping.setEstado(EstadoHeladera.INACTIVA);

    beginTransaction();
    incidenteRepository.guardar(Incidente.fallaConexion(
        heladeraAbastoShopping,
        LocalDateTime.of(2024, 1, 15, 10, 30)));

    incidenteRepository.guardar(Incidente.fallaTemperatura(
        heladeraAbastoShopping,
        LocalDateTime.of(2024, 5, 20, 8, 0)));

    incidenteRepository.guardar(Incidente.fallaTecnica(
        heladeraUtnMedrano,
        LocalDateTime.of(2024, 8, 12, 14, 5),
        colaboradorRepository.buscarPorEmail("melperez@frba.utn.edu.ar").orElseThrow(),
        "No funciona el lector de tarjeta.",
        new Imagen("image-test.png")));

    heladeraRepository.actualizar(heladeraAbastoShopping);
    heladeraRepository.actualizar(heladeraUtnMedrano);
    commitTransaction();
  }

  private void withVarianteDePuntos() {
    VarianteDePuntos variante = new VarianteDePuntos(
        LocalDate.now(), 0.5, 1.0, 1.5, 2.0, 5.0);

    withTransaction(() -> varianteDePuntosRepository.guardar(variante));
  }

  private void withOfertas() {
    Colaborador melperez = colaboradorRepository
        .buscarPorEmail("melperez@frba.utn.edu.ar").orElseThrow();

    Imagen img = new Imagen("image-test.png");

    beginTransaction();
    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 1", 1, RubroOferta.ELECTRONICA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 2", 30, RubroOferta.GASTRONOMIA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 3", 100, RubroOferta.HOGAR, img));
    commitTransaction();
  }

  private void withTecnicos() {
    Contacto contacto = Contacto.conEmail("leoneljuncossmieres@gmail.com");

    Usuario mmieres = Usuario.con(
        "Xx_mieres_xX",
        "1111",
        "leoneljuncossmieres@gmail.com",
        TipoRol.TECNICO);

    Area areaDeCoberura = Area.con(
        new Ubicacion(-34.6037, -58.3816),
        100,
        new Barrio("Palermo")
    );

    final Tecnico t1 = Tecnico.con(
        mmieres,
        "Matias",
        "Mieres",
        Documento.con(TipoDocumento.DNI, "45234468"),
        "27-45234468-3",
        contacto,
        areaDeCoberura);

    beginTransaction();
    contactoRepository.guardar(contacto);
    usuarioRepository.guardar(mmieres);
    tecnicoRepository.guardar(t1);
    commitTransaction();
  }

  private void withTarjetas() {
    TarjetaColaborador t1 = TarjetaColaborador.de(
        "1234567890123456",
        colaboradorRepository.buscarPorEmail("jhsu@frba.utn.edu.ar").orElseThrow(),
        true
    );

    TarjetaColaborador t2 = TarjetaColaborador.de(
        "123456we890123456",
        colaboradorRepository.buscarPorEmail("melperez@frba.utn.edu.ar").orElseThrow(),
        false
    );

    TarjetaColaboradorRepository tarjetaColaboradorRepository = new TarjetaColaboradorRepository();

    beginTransaction();
    tarjetaColaboradorRepository.guardar(t1);
    tarjetaColaboradorRepository.guardar(t2);
    commitTransaction();
  }

  private void initializeMqttSubscribers() {
    ServiceLocator.instanceOf(SuscriptorSensorService.class).initialize();
  }

  private void cleanupDatabase() {
    withTransaction(() -> {
    });
  }
}