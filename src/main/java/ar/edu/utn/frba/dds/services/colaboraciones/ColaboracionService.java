package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.exceptions.CargaMasivaException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.ColaboracionPrevia;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DistribucionViandas;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionDinero;
import ar.edu.utn.frba.dds.models.entities.colaboracion.DonacionVianda;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RepartoDeTarjetas;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.mensaje.Mensaje;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DistribucionViandasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionDineroRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.DonacionViandaRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.HacerseCargoHeladeraRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.RepartoDeTarjetasRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.models.repositories.mensajeria.MensajeRepository;
import ar.edu.utn.frba.dds.models.repositories.usuario.UsuarioRepository;
import ar.edu.utn.frba.dds.models.stateless.GeneradorDeCredenciales;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.ISender;
import ar.edu.utn.frba.dds.models.stateless.mensajeria.MedioDeNotificacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import jakarta.mail.MessagingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 * Servicio de colaboraciones.
 */
public class ColaboracionService implements WithSimplePersistenceUnit {

  private final UsuarioRepository usuarioRepository;
  private final ColaboradorRepository colaboradorRepository;

  private final DonacionViandaRepository donacionViandaRepository;
  private final DonacionDineroRepository donacionDineroRepository;
  private final DistribucionViandasRepository distribucionViandasRepository;
  private final HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository;
  private final OfertaDeProductosRepository ofertaDeProductosRepository;
  private final RepartoDeTarjetasRepository repartoDeTarjetasRepository;

  private final ISender mailSender;
  private final MensajeRepository mensajeRepository;

  /**
   * Constructor.
   *
   * @param usuarioRepository              el repositorio de usuarios
   * @param colaboradorRepository          el repositorio de colaboradores
   * @param donacionViandaRepository       el repositorio de donaciones de viandas
   * @param donacionDineroRepository       el repositorio de donaciones de dinero
   * @param distribucionViandasRepository  el repositorio de distribuciones de viandas
   * @param hacerseCargoHeladeraRepository el repositorio de heladeras activas
   * @param ofertaDeProductosRepository    el repositorio de ofertas de productos
   * @param repartoDeTarjetasRepository    el repositorio de repartos de tarjetas
   * @param mailSender                     el servicio de envío de mensajes
   * @param mensajeRepository              el repositorio de mensajes
   */
  public ColaboracionService(UsuarioRepository usuarioRepository,
                             ColaboradorRepository colaboradorRepository,
                             DonacionViandaRepository donacionViandaRepository,
                             DonacionDineroRepository donacionDineroRepository,
                             DistribucionViandasRepository distribucionViandasRepository,
                             HacerseCargoHeladeraRepository hacerseCargoHeladeraRepository,
                             OfertaDeProductosRepository ofertaDeProductosRepository,
                             RepartoDeTarjetasRepository repartoDeTarjetasRepository,
                             ISender mailSender,
                             MensajeRepository mensajeRepository) {
    this.usuarioRepository = usuarioRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.donacionViandaRepository = donacionViandaRepository;
    this.donacionDineroRepository = donacionDineroRepository;
    this.distribucionViandasRepository = distribucionViandasRepository;
    this.hacerseCargoHeladeraRepository = hacerseCargoHeladeraRepository;
    this.ofertaDeProductosRepository = ofertaDeProductosRepository;
    this.repartoDeTarjetasRepository = repartoDeTarjetasRepository;
    this.mailSender = mailSender;
    this.mensajeRepository = mensajeRepository;
  }

  /**
   * Obtiene todas las colaboraciones.
   *
   * @return todas las colaboraciones
   */
  public List<Object> buscarTodas() {
    List<Object> colaboraciones = new ArrayList<>();
    colaboraciones.addAll(donacionViandaRepository.buscarTodos());
    colaboraciones.addAll(donacionDineroRepository.buscarTodos());
    colaboraciones.addAll(distribucionViandasRepository.buscarTodos());
    colaboraciones.addAll(hacerseCargoHeladeraRepository.buscarTodos());
    colaboraciones.addAll(ofertaDeProductosRepository.buscarTodos());
    colaboraciones.addAll(repartoDeTarjetasRepository.buscarTodos());
    return colaboraciones;
  }

  // TODO: Mapear a DTO

  /**
   * Obtiene todas las colaboraciones de un colaborador.
   *
   * @param colaborador el colaborador
   * @return todas las colaboraciones del colaborador
   */
  public List<Object> buscarTodasPorColaborador(Colaborador colaborador) {
    List<Object> colaboraciones = new ArrayList<>();
    colaboraciones.addAll(donacionViandaRepository.buscarPorColaborador(colaborador));
    colaboraciones.addAll(donacionDineroRepository.buscarPorColaborador(colaborador));
    colaboraciones.addAll(distribucionViandasRepository.buscarPorColaborador(colaborador));
    colaboraciones.addAll(hacerseCargoHeladeraRepository.buscarPorColaborador(colaborador));
    colaboraciones.addAll(ofertaDeProductosRepository.buscarPorColaborador(colaborador));
    colaboraciones.addAll(repartoDeTarjetasRepository.buscarPorColaborador(colaborador));
    return colaboraciones;
  }

  // TODO: Revisar carga masiva

  /**
   * Carga masiva de colaboraciones.
   *
   * @param csv el archivo CSV
   * @throws CargaMasivaException en caso de error al cargar las colaboraciones
   */
  public void cargarColaboraciones(InputStream csv) throws CargaMasivaException {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(csv, StandardCharsets.UTF_8)
      );
      CSVParser csvParser = new CSVParser(reader, CSVFormat.Builder.create().setHeader().build());

      beginTransaction();
      for (CSVRecord csvRecord : csvParser) {
        Documento documento = new Documento(
            TipoDocumento.valueOf(csvRecord.get("Tipo Doc")),
            csvRecord.get("Documento")
        );

        ColaboracionPrevia colaboracionPrevia = ColaboracionPrevia.por(
            documento,
            csvRecord.get("Nombre"),
            csvRecord.get("Apellido"),
            csvRecord.get("Mail"),
            LocalDate.parse(csvRecord.get("Fecha de colaboración"), formatter).atStartOfDay(),
            csvRecord.get("Forma de colaboración"),
            Integer.parseInt(csvRecord.get("Cantidad"))
        );

        Colaborador colaborador = colaboradorRepository
            .buscarPorEmail(colaboracionPrevia.getEmail())
            .orElse(generarColaborador(colaboracionPrevia.getNombre(),
                colaboracionPrevia.getApellido(),
                colaboracionPrevia.getDocumento(),
                colaboracionPrevia.getEmail()));

        Mensaje mensaje = mensajeCredencial(colaborador);
        mailSender.enviarMensaje(mensaje);
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensajeRepository.guardar(mensaje);

        this.registrarColaboracion(colaboracionPrevia, colaborador);
      }
      commitTransaction();
    } catch (IOException | IllegalArgumentException | MessagingException e) {
      rollbackTransaction();
      throw new CargaMasivaException(
          "Error al cargar las colaboraciones. Error: " + e.getMessage()
      );
    }
  }

  private Colaborador generarColaborador(String nombre,
                                         String apellido,
                                         Documento documento,
                                         String email) {
    Usuario usuario = GeneradorDeCredenciales.generarUsuario(nombre, email);
    Colaborador colaborador = Colaborador.humanaConDocumento(usuario, nombre, apellido, documento);
    Contacto contacto = Contacto.conEmail(email);
    colaborador.agregarContacto(contacto);

    // TODO: guardar contacto
    usuarioRepository.guardar(usuario);
    colaboradorRepository.guardar(colaborador);
    return colaborador;
  }

  private Mensaje mensajeCredencial(Colaborador colaborador) {
    Usuario usuario = colaborador.getUsuario();

    String asunto = "Credencial de usuario";
    String cuerpo = "Esta es la credencial:"
        + " - Nombre de usuario provicional: " + usuario.getNombre()
        + " - Contrasenia de usuario provicional: " + usuario.getContrasenia();

    Mensaje mensaje = Mensaje.con(colaborador.getContacto(MedioDeNotificacion.EMAIL).get(), asunto, cuerpo);
    return mensaje;
  }

  private void registrarColaboracion(ColaboracionPrevia colaboracionPrevia, Colaborador colaborador) {
    switch (colaboracionPrevia.getFormaDeColaboracion()) {
      case "DINERO":
        DonacionDinero donacionDinero = DonacionDinero
            .por(colaborador, colaboracionPrevia.getFechaHora(), colaboracionPrevia.getCantidad());
        donacionDineroRepository.guardar(donacionDinero);
        break;

      case "DONACION_VIANDAS":
        for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
          DonacionVianda donacionVianda = DonacionVianda
              .por(colaborador, colaboracionPrevia.getFechaHora());
          donacionViandaRepository.guardar(donacionVianda);
        }
        break;

      case "REDISTRIBUCION_VIANDAS":
        DistribucionViandas distribucionViandas = DistribucionViandas.por(
            colaborador, colaboracionPrevia.getFechaHora(), colaboracionPrevia.getCantidad());
        distribucionViandasRepository.guardar(distribucionViandas);
        break;

      case "ENTREGA_TARJETAS":
        for (int i = 0; i < colaboracionPrevia.getCantidad(); i++) {
          RepartoDeTarjetas repartoDeTarjetas = RepartoDeTarjetas
              .por(colaborador, colaboracionPrevia.getFechaHora());
          repartoDeTarjetasRepository.guardar(repartoDeTarjetas);
        }
        break;

      default:
        break;
    }
  }
}
