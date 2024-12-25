package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.Puntos;
import ar.edu.utn.frba.dds.models.entities.canjeDePuntos.VarianteDePuntos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.entities.data.TipoRazonSocial;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.entities.incidente.Incidente;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.rol.TipoRol;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.canjeDePuntos.VarianteDePuntosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.incidente.IncidenteRepository;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.services.heladera.HeladeraService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Initializer implements WithSimplePersistenceUnit {

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

    // PDFGenerator pdfGenerator = new PDFGenerator(AppProperties.getInstance().propertyFromName("REPORT_DIR"));
    // ServiceLocator.instanceOf(ReporteService.class).generarReporteSemanal(pdfGenerator);
  }

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

  public void withColaboradores() {
    Usuario u1 = Usuario.con("JiunMHsu", "1111", "jhsu@frba.utn.edu.ar", TipoRol.COLABORADOR);
    Usuario u2 = Usuario.con("abrilnimo", "1111", "adomingueznimo@frba.utn.edu.ar", TipoRol.COLABORADOR);
    Usuario u3 = Usuario.con("leoojuncos", "1111", "mjuncosmieres@frba.utn.edu.ar", TipoRol.COLABORADOR);
    Usuario u4 = Usuario.con("Melselep", "1111", "melperez@frba.utn.edu.ar", TipoRol.COLABORADOR);
    Usuario u5 = Usuario.con("joaquingandola", "1111", "jgandola@frba.utn.edu.ar", TipoRol.COLABORADOR);

    Direccion direccion = new Direccion(
        new Barrio("Almagro"),
        new Calle("Medrano"),
        951,
        new Ubicacion(-34.59857981526152, -58.420110294464294)
    );

    List<TipoColaboracion> colabHumana1 = List.of(TipoColaboracion.DISTRIBUCION_VIANDAS, TipoColaboracion.DONACION_DINERO);
    List<TipoColaboracion> colabHumana2 = List.of(TipoColaboracion.DISTRIBUCION_VIANDAS, TipoColaboracion.REPARTO_DE_TARJETAS, TipoColaboracion.DONACION_VIANDAS);
    List<TipoColaboracion> colabJuridica1 = List.of(TipoColaboracion.DONACION_DINERO, TipoColaboracion.HACERSE_CARGO_HELADERA);
    List<TipoColaboracion> colabJuridica2 = List.of(TipoColaboracion.HACERSE_CARGO_HELADERA, TipoColaboracion.OFERTA_DE_PRODUCTOS, TipoColaboracion.DONACION_DINERO);

    Colaborador c1 = Colaborador.humana(u1, "Jiun Ming", "Hsu", null, LocalDate.of(2002, 2, 19), Contacto.vacio(), direccion, new ArrayList<>(colabHumana1), new Puntos(2039, true, null));
    Colaborador c2 = Colaborador.humana(u2, "Abril", "Nimo Dominguez", null, LocalDate.of(2004, 1, 8), Contacto.vacio(), direccion, new ArrayList<>(colabHumana2), new Puntos(0, false, null));
    Colaborador c3 = Colaborador.humana(u3, "Matías Leonel", "Juncos Mieres", null, LocalDate.of(2003, 12, 1), Contacto.vacio(), direccion, new ArrayList<>(colabHumana1), new Puntos(0, false, null));
    Colaborador c4 = Colaborador.juridica(u4, "MELSELEP SRL", TipoRazonSocial.EMPRESA, "Música", Contacto.vacio(), direccion, new ArrayList<>(colabJuridica2), new Puntos(0, false, null));
    Colaborador c5 = Colaborador.juridica(u5, "JOACO SA", TipoRazonSocial.EMPRESA, "Tecnología", Contacto.vacio(), direccion, new ArrayList<>(colabJuridica1), new Puntos(0, false, null));

    UsuarioRepository usuarioRepository = new UsuarioRepository();
    ColaboradorRepository colaboradorRepository = new ColaboradorRepository();

    withTransaction(() -> {
      usuarioRepository.guardar(u1);
      usuarioRepository.guardar(u2);
      usuarioRepository.guardar(u3);
      usuarioRepository.guardar(u4);
      usuarioRepository.guardar(u5);

      colaboradorRepository.guardar(c1);
      colaboradorRepository.guardar(c2);
      colaboradorRepository.guardar(c3);
      colaboradorRepository.guardar(c4);
      colaboradorRepository.guardar(c5);
    });
  }

  public void withHeladeras() {

    Ubicacion u1 = new Ubicacion(-34.6037, -58.3816);
    Ubicacion u2 = new Ubicacion(-34.6040, -58.3800);
    Ubicacion u3 = new Ubicacion(-34.6050, -58.3825);
    Ubicacion u4 = new Ubicacion(-34.6035, -58.3830);
    Ubicacion u5 = new Ubicacion(-34.6028, -58.3810);
    Ubicacion u6 = new Ubicacion(-34.6060, -58.3845);
    Ubicacion u7 = new Ubicacion(-34.6045, -58.3790);
    Ubicacion u8 = new Ubicacion(-34.6058, -58.3805);
    Ubicacion u9 = new Ubicacion(-34.6032, -58.3820);
    Ubicacion u10 = new Ubicacion(-34.6070, -58.3850);
    Ubicacion u11 = new Ubicacion(-34.6025, -58.3795);
    Ubicacion u12 = new Ubicacion(-34.6080, -58.3835);
    Ubicacion u13 = new Ubicacion(-34.6090, -58.3860);
    Ubicacion u14 = new Ubicacion(-34.6010, -58.3785);
    Ubicacion u15 = new Ubicacion(-34.6075, -58.3800);

    Barrio b1 = new Barrio("Palermo");
    Barrio b2 = new Barrio("Recoleta");
    Barrio b3 = new Barrio("San Telmo");
    Barrio b4 = new Barrio("Belgrano");
    Barrio b5 = new Barrio("La Boca");
    Barrio b6 = new Barrio("Almagro");
    Barrio b7 = new Barrio("Villa Urquiza");
    Barrio b8 = new Barrio("Caballito");
    Barrio b9 = new Barrio("Nuñez");
    Barrio b10 = new Barrio("Puerto Madero");

    Calle c1 = new Calle("Avenida Corrientes");
    Calle c2 = new Calle("Florida");
    Calle c3 = new Calle("Avenida 9 por Julio");
    Calle c4 = new Calle("Avenida Santa Fe");
    Calle c5 = new Calle("Avenida Medrano");
    Calle c6 = new Calle("Avenida por Mayo");
    Calle c7 = new Calle("Avenida Libertador");
    Calle c8 = new Calle("Lavalle");
    Calle c9 = new Calle("Avenida Pueyrredón");
    Calle c10 = new Calle("Avenida Alvear");

    Direccion d1 = new Direccion(b6, c2, 1765, u1);
    Direccion d2 = new Direccion(b7, c3, 1933, u2);
    Direccion d3 = new Direccion(b6, c5, 734, u3);
    Direccion d4 = new Direccion(b6, c2, 871, u4);
    Direccion d5 = new Direccion(b5, c9, 178, u5);
    Direccion d6 = new Direccion(b3, c1, 2330, u6);
    Direccion d7 = new Direccion(b1, c4, 1251, u7);
    Direccion d8 = new Direccion(b4, c6, 1765, u8);
    Direccion d9 = new Direccion(b2, c7, 1123, u9);
    Direccion d10 = new Direccion(b6, c8, 2915, u10);
    Direccion d11 = new Direccion(b10, c10, 3644, u11);
    Direccion d12 = new Direccion(b9, c2, 1131, u12);
    Direccion d13 = new Direccion(b8, c3, 3331, u13);
    Direccion d14 = new Direccion(b10, c4, 3242, u14);
    Direccion d15 = new Direccion(b10, c5, 3633, u15);

    HeladeraRepository heladeraRepository = new HeladeraRepository();

    beginTransaction();
    heladeraRepository.guardar(Heladera.con("Heladera DIEZ", d10, 80, new RangoTemperatura(5.0, -5.0), 75));
    heladeraRepository.guardar(Heladera.con("Heladera CINCO", d5, 60, new RangoTemperatura(5.0, -4.0), 52));
    heladeraRepository.guardar(Heladera.con("Heladera NUEVE", d9, 90, new RangoTemperatura(4.0, -4.0), 67));
    heladeraRepository.guardar(Heladera.con("Heladera UNO", d1, 80, new RangoTemperatura(3.0, -5.0), 58));
    heladeraRepository.guardar(Heladera.con("Heladera CATORCE", d14, 65, new RangoTemperatura(5.0, -5.0), 46));
    heladeraRepository.guardar(Heladera.con("Heladera ONCE", d11, 85, new RangoTemperatura(3.0, -4.0), 47));
    heladeraRepository.guardar(Heladera.con("Heladera DOCE", d12, 70, new RangoTemperatura(5.0, -3.0), 61));
    heladeraRepository.guardar(Heladera.con("Heladera QUINCE", d15, 80, new RangoTemperatura(3.0, -3.0), 80));
    heladeraRepository.guardar(Heladera.con("Heladera TRECE", d13, 95, new RangoTemperatura(2.0, -4.0), 83));
    heladeraRepository.guardar(Heladera.con("Heladera CUATRO", d4, 55, new RangoTemperatura(3.0, -4.0), 35));
    heladeraRepository.guardar(Heladera.con("Heladera OCHO", d8, 70, new RangoTemperatura(3.0, -2.0), 55));
    heladeraRepository.guardar(Heladera.con("Heladera SIETE", d7, 80, new RangoTemperatura(3.0, -4.0), 76));
    heladeraRepository.guardar(Heladera.con("Heladera DOS", d2, 70, new RangoTemperatura(2.0, -3.0), 42));
    heladeraRepository.guardar(Heladera.con("Heladera SEIS", d6, 60, new RangoTemperatura(4.0, -4.0), 44));
    heladeraRepository.guardar(Heladera.con("Heladera TRES", d3, 85, new RangoTemperatura(3.0, -4.0), 66));
    commitTransaction();
  }

  private void withIncidentes() {
    HeladeraRepository heladeraRepository = new HeladeraRepository();
    ColaboradorRepository colaboradorRepository = new ColaboradorRepository();

    Heladera h1 = heladeraRepository.buscarPorNombre("Heladera UNO").orElseThrow();
    Heladera h2 = heladeraRepository.buscarPorNombre("Heladera DOS").orElseThrow();
    Heladera h5 = heladeraRepository.buscarPorNombre("Heladera CINCO").orElseThrow();
    Heladera h6 = heladeraRepository.buscarPorNombre("Heladera SEIS").orElseThrow();
    Heladera h8 = heladeraRepository.buscarPorNombre("Heladera OCHO").orElseThrow();
    Heladera h10 = heladeraRepository.buscarPorNombre("Heladera DIEZ").orElseThrow();
    Heladera h12 = heladeraRepository.buscarPorNombre("Heladera DOCE").orElseThrow();
    Heladera h15 = heladeraRepository.buscarPorNombre("Heladera QUINCE").orElseThrow();

    h1.setEstado(EstadoHeladera.INACTIVA);
    h2.setEstado(EstadoHeladera.INACTIVA);
    h5.setEstado(EstadoHeladera.INACTIVA);
    h6.setEstado(EstadoHeladera.INACTIVA);
    h8.setEstado(EstadoHeladera.INACTIVA);
    h10.setEstado(EstadoHeladera.INACTIVA);
    h12.setEstado(EstadoHeladera.INACTIVA);
    h15.setEstado(EstadoHeladera.INACTIVA);

    Incidente i1 = Incidente.fallaTemperatura(h10, LocalDateTime.of(2024, 3, 19, 14, 3));
    Incidente i2 = Incidente.fallaConexion(h1, LocalDateTime.of(2024, 1, 15, 10, 30));
    Incidente i3 = Incidente.fallaTemperatura(h15, LocalDateTime.of(2024, 2, 29, 9, 45));
    Incidente i4 = Incidente.fraude(h8, LocalDateTime.of(2024, 4, 5, 16, 15));
    Incidente i5 = Incidente.fallaTemperatura(h2, LocalDateTime.of(2024, 5, 20, 8, 0));
    Incidente i6 = Incidente.fallaConexion(h5, LocalDateTime.of(2024, 6, 10, 13, 25));
    Incidente i7 = Incidente.fraude(h6, LocalDateTime.of(2024, 7, 23, 18, 50));

    Incidente i8 = Incidente.fallaTecnica(
        h5,
        LocalDateTime.of(2024, 8, 12, 14, 5),
        colaboradorRepository.buscarPorEmail("adomingueznimo@frba.utn.edu.ar").orElseThrow(),
        "No funca el lector por tarjeta.",
        new Imagen("image-test.png"));

    Incidente i9 = Incidente.fallaTecnica(
        h6,
        LocalDateTime.of(2024, 9, 17, 19, 40),
        colaboradorRepository.buscarPorEmail("adomingueznimo@frba.utn.edu.ar").orElseThrow(),
        "La vianda no sale",
        new Imagen("image-test.png"));

    Incidente i10 = Incidente.fallaTecnica(
        h12,
        LocalDateTime.of(2024, 12, 1, 7, 55),
        colaboradorRepository.buscarPorEmail("jgandola@frba.utn.edu.ar").orElseThrow(),
        "Ni idea lo que paso",
        new Imagen("image-test.png"));

    IncidenteRepository incidenteRepository = new IncidenteRepository();

    beginTransaction();
    incidenteRepository.guardar(i1);
    incidenteRepository.guardar(i2);
    incidenteRepository.guardar(i3);
    incidenteRepository.guardar(i4);
    incidenteRepository.guardar(i5);
    incidenteRepository.guardar(i6);
    incidenteRepository.guardar(i7);
    incidenteRepository.guardar(i8);
    incidenteRepository.guardar(i9);
    incidenteRepository.guardar(i10);

    heladeraRepository.actualizar(h1);
    heladeraRepository.actualizar(h2);
    heladeraRepository.actualizar(h5);
    heladeraRepository.actualizar(h6);
    heladeraRepository.actualizar(h8);
    heladeraRepository.actualizar(h10);
    heladeraRepository.actualizar(h12);
    heladeraRepository.actualizar(h15);

    commitTransaction();
  }

  private void withVarianteDePuntos() {
    VarianteDePuntos variante = new VarianteDePuntos(
        LocalDate.now(), 0.5, 1.0, 1.5, 2.0, 5.0);

    VarianteDePuntosRepository repository = new VarianteDePuntosRepository();
    withTransaction(() -> repository.guardar(variante));
  }

  private void withOfertas() {
    ColaboradorRepository colaboradorRepository = new ColaboradorRepository();
    Colaborador c1 = colaboradorRepository.buscarPorEmail("melperez@frba.utn.edu.ar").orElseThrow();
    Colaborador c2 = colaboradorRepository.buscarPorEmail("jgandola@frba.utn.edu.ar").orElseThrow();

    Imagen img = new Imagen("image-test.png");

    OfertaDeProductosRepository repository = new OfertaDeProductosRepository();
    beginTransaction();
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 1", 1, RubroOferta.ELECTRONICA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 2", 20, RubroOferta.GASTRONOMIA, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 3", 50, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 4", 60, RubroOferta.GASTRONOMIA, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 5", 35, RubroOferta.ELECTRONICA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 6", 65, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 7", 40, RubroOferta.ELECTRONICA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 8", 70, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 9", 20, RubroOferta.GASTRONOMIA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 10", 45, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 11", 65, RubroOferta.GASTRONOMIA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 12", 50, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 13", 30, RubroOferta.ELECTRONICA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 14", 40, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 15", 35, RubroOferta.ELECTRONICA, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 16", 75, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 17", 25, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 18", 80, RubroOferta.GASTRONOMIA, img));
    repository.guardar(OfertaDeProductos.por(c1, LocalDateTime.now(), "Producto 19", 20, RubroOferta.HOGAR, img));
    repository.guardar(OfertaDeProductos.por(c2, LocalDateTime.now(), "Producto 20", 30, RubroOferta.ELECTRONICA, img));
    commitTransaction();
  }

  private void withTecnicos() {
    Usuario u1 = Usuario.con("Tecnico1", "1111", "tecnico1@gmail.com", TipoRol.TECNICO);
    Tecnico t1 = Tecnico.con(u1, "Tecnico", "Uno", null, "20-00019283-1", Contacto.vacio(), MedioDeNotificacion.EMAIL, null);

    beginTransaction();
    new UsuarioRepository().guardar(u1);
    new TecnicoRepository().guardar(t1);
    commitTransaction();
  }

  private void initializeMqttSubscribers() {
    ServiceLocator.instanceOf(HeladeraService.class).iniciarSuscripciones();
  }

  private void cleanupDatabase() {
    withTransaction(() -> {
    });
  }
}
