package ar.edu.utn.frba.dds.services.colaboraciones;

import ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos.CreateOfertaDeProductosDTO;
import ar.edu.utn.frba.dds.dtos.colaboraciones.ofertaDeProductos.OfertaDeProductosDTO;
import ar.edu.utn.frba.dds.exceptions.InvalidFormParamException;
import ar.edu.utn.frba.dds.exceptions.ResourceNotFoundException;
import ar.edu.utn.frba.dds.models.entities.colaboracion.OfertaDeProductos;
import ar.edu.utn.frba.dds.models.entities.colaboracion.RubroOferta;
import ar.edu.utn.frba.dds.models.entities.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.entities.data.Imagen;
import ar.edu.utn.frba.dds.models.repositories.colaboracion.OfertaDeProductosRepository;
import ar.edu.utn.frba.dds.models.repositories.colaborador.ColaboradorRepository;
import ar.edu.utn.frba.dds.services.images.ImageService;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.UploadedFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de Oferta de Productos y Servicios.
 */
public class OfertaProductosServiciosService implements WithSimplePersistenceUnit {

  private final OfertaDeProductosRepository ofertaDeProductosRepository;
  private final ColaboradorRepository colaboradorRepository;
  private final ImageService imageService;

  /**
   * Constructor.
   *
   * @param ofertaDeProductosRepository OfertaDeProductosRepository
   * @param colaboradorRepository       ColaboradorRepository
   * @param imageService                ImageService
   */
  public OfertaProductosServiciosService(OfertaDeProductosRepository ofertaDeProductosRepository,
                                         ColaboradorRepository colaboradorRepository,
                                         ImageService imageService) {
    this.ofertaDeProductosRepository = ofertaDeProductosRepository;
    this.colaboradorRepository = colaboradorRepository;
    this.imageService = imageService;
  }

  /**
   * Registra una oferta de productos.
   *
   * @param colaborador Colaborador que registra la oferta.
   * @param nuevaOferta DTO con los datos de la oferta.
   */
  public void registrarOferta(Colaborador colaborador, CreateOfertaDeProductosDTO nuevaOferta)
      throws InvalidFormParamException, IOException {

    UploadedFile imagenSubida = nuevaOferta.getImagen();
    if (imagenSubida == null) {
      throw new InvalidFormParamException();
    }

    String pathImagen = imageService
        .guardarImagen(imagenSubida.content(), imagenSubida.extension());

    OfertaDeProductos oferta = OfertaDeProductos.por(
        colaborador,
        LocalDateTime.now(),
        nuevaOferta.getNombre(),
        nuevaOferta.getPuntosNecesarios(),
        RubroOferta.valueOf(nuevaOferta.getRubro().toUpperCase()),
        new Imagen(pathImagen)
    );

    colaborador.invalidarPuntos();

    beginTransaction();
    this.ofertaDeProductosRepository.guardar(oferta);
    this.colaboradorRepository.actualizar(colaborador);
    commitTransaction();
  }

  public void eliminar(OfertaDeProductos oferta) {
    this.ofertaDeProductosRepository.eliminar(oferta);
  }

  /**
   * Busca una oferta de productos por su id.
   *
   * @param id Id de la oferta de productos.
   * @return Devuelve la oferta de productos en DTO.
   * @throws ResourceNotFoundException Si no se encuentra la oferta de productos.
   */
  public OfertaDeProductosDTO buscarPorId(String id) {
    return OfertaDeProductosDTO.fromColaboracion(
        this.ofertaDeProductosRepository.buscarPorId(id).orElseThrow(ResourceNotFoundException::new)
    );
  }

  /**
   * Busca todas las ofertas de productos.
   * TODO: Revisar si hace falta (unused)
   *
   * @return Devuelve una lista de ofertas de productos en DTO.
   */
  public List<OfertaDeProductosDTO> buscarTodos() {
    return this.ofertaDeProductosRepository.buscarTodos()
        .stream().map(OfertaDeProductosDTO::fromColaboracion).toList();
  }
}
