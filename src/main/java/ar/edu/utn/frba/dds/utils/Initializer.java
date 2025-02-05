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
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.TipoRol;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.VarianteDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.contacto.ContactoRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
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

    final Usuario u2 = Usuario.con(
        "abrilnimo", "1111", "adomingueznimo@frba.utn.edu.ar", TipoRol.COLABORADOR);

    final Usuario u3 = Usuario.con(
        "leoojuncos", "1111", "mjuncosmieres@frba.utn.edu.ar", TipoRol.COLABORADOR);

    final Usuario u4 = Usuario.con(
        "Melselep", "1111", "melperez@frba.utn.edu.ar", TipoRol.COLABORADOR);

    final Usuario u5 = Usuario.con(
        "joaquingandola", "1111", "jgandola@frba.utn.edu.ar", TipoRol.COLABORADOR);

    final Direccion direccion = new Direccion(
        new Barrio("Almagro"),
        new Calle("Medrano"),
        951,
        new Ubicacion(-34.59857981526152, -58.420110294464294)
    );

    final List<TipoColaboracion> colabHumana1 = List.of(
        TipoColaboracion.DISTRIBUCION_VIANDAS,
        TipoColaboracion.DONACION_DINERO);

    final List<TipoColaboracion> colabHumana2 = List.of(
        TipoColaboracion.DISTRIBUCION_VIANDAS,
        TipoColaboracion.REPARTO_DE_TARJETAS,
        TipoColaboracion.DONACION_VIANDAS);

    final List<TipoColaboracion> colabJuridica1 = List.of(
        TipoColaboracion.DONACION_DINERO,
        TipoColaboracion.HACERSE_CARGO_HELADERA);

    final List<TipoColaboracion> colabJuridica2 = List.of(
        TipoColaboracion.HACERSE_CARGO_HELADERA,
        TipoColaboracion.OFERTA_DE_PRODUCTOS,
        TipoColaboracion.DONACION_DINERO);

    final List<Contacto> contactos = List.of(Contacto.conWhatsApp("whatsapp:+5491132420699"));

    beginTransaction();
    usuarioRepository.guardar(u1);
    usuarioRepository.guardar(u2);
    usuarioRepository.guardar(u3);
    usuarioRepository.guardar(u4);
    usuarioRepository.guardar(u5);

    contactoRepository.guardar(contactos);

    colaboradorRepository.guardar(Colaborador.humana(
        u1,
        "Jiun Ming",
        "Hsu",
        null,
        LocalDate.of(2003, 2, 19),
        new ArrayList<>(),
        direccion,
        new ArrayList<>(colabHumana1),
        new Puntos(2039, true, null)));

    colaboradorRepository.guardar(Colaborador.humana(
        u2,
        "Abril",
        "Nimo Dominguez",
        null,
        LocalDate.of(2004, 1, 8),
        new ArrayList<>(),
        direccion,
        new ArrayList<>(colabHumana2),
        new Puntos(0, false, null)));

    colaboradorRepository.guardar(Colaborador.humana(
        u3,
        "Matías Leonel",
        "Juncos Mieres",
        null,
        LocalDate.of(2003, 12, 1),
        contactos,
        direccion,
        new ArrayList<>(colabHumana1),
        new Puntos(0, false, null)));

    colaboradorRepository.guardar(Colaborador.juridica(
        u4,
        "MELSELEP SRL",
        TipoRazonSocial.EMPRESA,
        "Música",
        new ArrayList<>(),
        direccion,
        new ArrayList<>(colabJuridica2),
        new Puntos(0, false, null)));

    colaboradorRepository.guardar(Colaborador.juridica(
        u5,
        "JOACO SA",
        TipoRazonSocial.EMPRESA,
        "Tecnología",
        new ArrayList<>(),
        direccion,
        new ArrayList<>(colabJuridica1),
        new Puntos(0, false, null)));

    commitTransaction();
  }

  /**
   * Crea las heladeras y las guarda en la base de datos.
   * Asocia cada heladera con una dirección y configura su temperatura.
   */
  public void withHeladeras() {

    Ubicacion utnLugano = new Ubicacion(-34.65980, -58.46812);
    Ubicacion guerrin = new Ubicacion(-34.60400, -58.38577);
    Ubicacion utnMedrano = new Ubicacion(-34.59862, -58.42011);
    Ubicacion hospitalPiniero = new Ubicacion(-34.64428, -58.45375);
    Ubicacion ferro = new Ubicacion(-34.61903, -58.44662);
    Ubicacion lineaD = new Ubicacion(-34.56236, -58.45627);
    Ubicacion abastoShopping = new Ubicacion(-34.60286, -58.41129);
    Ubicacion plazaSerrano = new Ubicacion(-34.58885, -58.43026);
    Ubicacion atlanta = new Ubicacion(-34.59453, -58.45046);
    Ubicacion plazaDemayo = new Ubicacion(-34.60803, -58.37230);
    Ubicacion caminito = new Ubicacion(-34.63932, -58.36272);
    Ubicacion plazaItalia = new Ubicacion(-34.58139, -58.42103);
    Ubicacion facultadDeDerecho = new Ubicacion(-34.58329, -58.39133);
    Ubicacion obelisco = new Ubicacion(-34.60385, -58.38242);
    Ubicacion barrioChino = new Ubicacion(-34.55641, -58.45161);

    Barrio b1 = new Barrio("Villa Lugano");
    Barrio b2 = new Barrio("San Nicolás");
    Barrio b3 = new Barrio("Almagro");
    Barrio b4 = new Barrio("Flores");
    Barrio b5 = new Barrio("Caballito");
    Barrio b6 = new Barrio("Balvanera");
    Barrio b7 = new Barrio("Palermo");
    Barrio b8 = new Barrio("Villa Crespo");
    Barrio b9 = new Barrio("Monserrat");
    Barrio b10 = new Barrio("La Boca");
    Barrio b11 = new Barrio("Recoleta");
    Barrio b12 = new Barrio("Belgrano");

    Calle c1 = new Calle("Agüero");
    Calle c2 = new Calle("Avenida Rivadavia");
    Calle c3 = new Calle("Mozart");
    Calle c4 = new Calle("Avenida Varela");
    Calle c5 = new Calle("Avenida Medrano");
    Calle c6 = new Calle("Caminito");
    Calle c7 = new Calle("Avenida Cabildo");
    Calle c8 = new Calle("Serrano");
    Calle c9 = new Calle("Gral. Marin de Gainza");
    Calle c10 = new Calle("Arribeños");
    Calle c11 = new Calle("Avenida Dorrego");
    Calle c12 = new Calle("Avenida Santa Fe");
    Calle c13 = new Calle("Julio Victor González");
    Calle c14 = new Calle("Avenida Corrientes");

    final Direccion dirUtnLugano = new Direccion(b1, c3, 2300, utnLugano);
    final Direccion dirGuerrin = new Direccion(b2, c14, 1368, guerrin);
    final Direccion dirUtnMedrano = new Direccion(b3, c5, 951, utnMedrano);
    final Direccion dirHospitalPiniero = new Direccion(b4, c4, 1301, hospitalPiniero);
    final Direccion dirFerro = new Direccion(b5, c9, 260, ferro);
    final Direccion dirLineaD = new Direccion(b12, c7, 2061, lineaD);
    final Direccion dirAbastoShopping = new Direccion(b6, c1, 611, abastoShopping);
    final Direccion dirPlazaSerrano = new Direccion(b7, c8, 1595, plazaSerrano);
    final Direccion dirAtlanta = new Direccion(b8, c11, 457, atlanta);
    final Direccion dirPlazaDeMayo = new Direccion(b9, c2, 360, plazaDemayo);
    final Direccion dirCaminitoDeLaBoca = new Direccion(b10, c6, 2005, caminito);
    final Direccion dirPlazaItalia = new Direccion(b7, c12, 4138, plazaItalia);
    final Direccion dirFacultadDeDerecho = new Direccion(b11, c13, 52, facultadDeDerecho);
    final Direccion dirObelisco = new Direccion(b2, c14, 1113, obelisco);
    final Direccion dirBarrioChino = new Direccion(b12, c10, 2290, barrioChino);

    String baseTopic = AppProperties.getInstance().propertyFromName("BASE_TOPIC") + "/heladeras/";

    beginTransaction();

    heladeraRepository.guardar(Heladera.con(
        "Heladera Plaza de Mayo",
        dirPlazaDeMayo,
        80,
        new RangoTemperatura(5.0, -5.0),
        75,
        baseTopic + "heladera-plaza-de-mayo"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Ferro",
        dirFerro,
        60,
        new RangoTemperatura(5.0, -4.0),
        52,
        baseTopic + "heladera-ferro"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Atlanta",
        dirAtlanta,
        90,
        new RangoTemperatura(4.0, -4.0),
        67,
        baseTopic + "heladera-atlanta"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera UTN Lugano",
        dirUtnLugano,
        80,
        new RangoTemperatura(3.0, -5.0),
        58,
        baseTopic + "heladera-utn-lugano"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Obelisco",
        dirObelisco,
        65,
        new RangoTemperatura(5.0, -5.0),
        46,
        baseTopic + "heladera-obelisco"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Caminito de la Boca",
        dirCaminitoDeLaBoca,
        85,
        new RangoTemperatura(3.0, -4.0),
        47,
        baseTopic + "heladera-caminito-de-la-boca"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Plaza Italia",
        dirPlazaItalia,
        70,
        new RangoTemperatura(5.0, -3.0),
        61,
        baseTopic + "heladera-plaza-italia"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Barrio Chino",
        dirBarrioChino,
        80,
        new RangoTemperatura(3.0, -3.0),
        80,
        baseTopic + "heladera-barrio-chino"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Facultad de Derecho",
        dirFacultadDeDerecho,
        95,
        new RangoTemperatura(2.0, -4.0),
        83,
        baseTopic + "heladera-facultad-de-derecho"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Hospital Piñero",
        dirHospitalPiniero,
        55, new RangoTemperatura(3.0, -4.0),
        35,
        baseTopic + "heladera-hospital-piniero"));

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
        "Heladera Güerrín",
        dirGuerrin,
        70,
        new RangoTemperatura(2.0, -3.0),
        42,
        baseTopic + "heladera-guerrin"));

    heladeraRepository.guardar(Heladera.con(
        "Heladera Línea D",
        dirLineaD,
        60,
        new RangoTemperatura(4.0, -4.0),
        44,
        baseTopic + "heladera-linea-d"));

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
    final Heladera heladeraHospitalPiniero = heladeraRepository
        .buscarPorNombre("Heladera Hospital Piñero").orElseThrow();

    final Heladera heladeraObelisco = heladeraRepository
        .buscarPorNombre("Heladera Obelisco").orElseThrow();

    final Heladera heladeraLineaD = heladeraRepository
        .buscarPorNombre("Heladera Línea D").orElseThrow();

    final Heladera heladeraCaminito = heladeraRepository
        .buscarPorNombre("Heladera Caminito de la Boca").orElseThrow();

    final Heladera heladeraFerro = heladeraRepository
        .buscarPorNombre("Heladera Ferro").orElseThrow();

    heladeraHospitalPiniero.setEstado(EstadoHeladera.INACTIVA);
    heladeraObelisco.setEstado(EstadoHeladera.INACTIVA);
    heladeraLineaD.setEstado(EstadoHeladera.INACTIVA);
    heladeraCaminito.setEstado(EstadoHeladera.INACTIVA);
    heladeraFerro.setEstado(EstadoHeladera.INACTIVA);

    beginTransaction();
    incidenteRepository.guardar(Incidente.fallaConexion(
        heladeraHospitalPiniero,
        LocalDateTime.of(2024, 1, 15, 10, 30)));

    incidenteRepository.guardar(Incidente.fallaTemperatura(
        heladeraObelisco,
        LocalDateTime.of(2024, 5, 20, 8, 0)));

    incidenteRepository.guardar(Incidente.fallaConexion(
        heladeraLineaD,
        LocalDateTime.of(2024, 6, 10, 13, 25)));

    incidenteRepository.guardar(Incidente.fraude(
        heladeraCaminito,
        LocalDateTime.of(2024, 7, 23, 18, 50)));

    incidenteRepository.guardar(Incidente.fallaTecnica(
        heladeraLineaD,
        LocalDateTime.of(2024, 8, 12, 14, 5),
        colaboradorRepository.buscarPorEmail("adomingueznimo@frba.utn.edu.ar").orElseThrow(),
        "No funca el lector de tarjeta.",
        new Imagen("image-test.png")));

    incidenteRepository.guardar(Incidente.fallaTecnica(
        heladeraCaminito,
        LocalDateTime.of(2024, 9, 17, 19, 40),
        colaboradorRepository.buscarPorEmail("adomingueznimo@frba.utn.edu.ar").orElseThrow(),
        "La vianda no sale",
        new Imagen("image-test.png")));

    incidenteRepository.guardar(Incidente.fallaTecnica(
        heladeraFerro,
        LocalDateTime.of(2024, 12, 1, 7, 55),
        colaboradorRepository.buscarPorEmail("jgandola@frba.utn.edu.ar").orElseThrow(),
        "Ni idea lo que paso",
        new Imagen("image-test.png")));

    heladeraRepository.actualizar(heladeraHospitalPiniero);
    heladeraRepository.actualizar(heladeraObelisco);
    heladeraRepository.actualizar(heladeraLineaD);
    heladeraRepository.actualizar(heladeraCaminito);
    heladeraRepository.actualizar(heladeraFerro);
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

    Colaborador jgandola = colaboradorRepository
        .buscarPorEmail("jgandola@frba.utn.edu.ar").orElseThrow();

    Imagen img = new Imagen("image-test.png");

    beginTransaction();
    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 1", 1, RubroOferta.ELECTRONICA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 2", 20, RubroOferta.GASTRONOMIA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 3", 50, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 4", 60, RubroOferta.GASTRONOMIA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 5", 35, RubroOferta.ELECTRONICA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 6", 65, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 7", 40, RubroOferta.ELECTRONICA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 8", 70, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 9", 20, RubroOferta.GASTRONOMIA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 10", 45, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 11", 65, RubroOferta.GASTRONOMIA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 12", 50, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 13", 30, RubroOferta.ELECTRONICA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 14", 40, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 15", 35, RubroOferta.ELECTRONICA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 16", 75, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 17", 25, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 18", 80, RubroOferta.GASTRONOMIA, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(melperez, "Producto 19", 20, RubroOferta.HOGAR, img));

    ofertaDeProductosRepository.guardar(
        OfertaDeProductos.por(jgandola, "Producto 20", 30, RubroOferta.ELECTRONICA, img));
    commitTransaction();
  }

  private void withTecnicos() {
    Contacto contacto = Contacto.conTelegram("+5491132420699");

    Usuario blopez = Usuario.con("Tecnico1", "1111", "blopez@gmail.com", TipoRol.TECNICO);
    Area areaDeCoberura = Area.con(
        new Ubicacion(-34.6037, -58.3816),
        100,
        new Barrio("Palermo")
    );
    Documento unDocumento = Documento.con(TipoDocumento.DNI, "00019283");
    final Tecnico t1 = Tecnico.con(
        blopez,
        "Bautista",
        "López",
        unDocumento,
        "20-00019283-1",
        contacto,
        areaDeCoberura);

    beginTransaction();
    contactoRepository.guardar(contacto);
    usuarioRepository.guardar(blopez);
    tecnicoRepository.guardar(t1);
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