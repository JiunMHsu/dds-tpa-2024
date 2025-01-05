package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.entities.data.*;
import ar.edu.utn.frba.dds.models.entities.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.entities.usuario.Usuario;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TecnicoRepositoryTest implements SimplePersistenceTest {

  private TecnicoRepository tecnicoRepository;
  private Barrio almagro;
  private Barrio sanTelmo;

  @BeforeEach
  public void setUp() {
    tecnicoRepository = new TecnicoRepository();

    almagro = new Barrio("Almagro");
    sanTelmo = new Barrio("San Telmo");

    Tecnico unTecnico = Tecnico.con(
        Usuario.conEmail("lshdgf@lkdajg.com"), // es para que compile, obviamente falla el test
        "Matias Leonel",
        "Juncos Mieres",
        new Documento(TipoDocumento.DNI, "12345678"),
        "24-12345678-0",
        Contacto.conTelegram("7652931546"),
        MedioDeNotificacion.TELEGRAM,
        new Area(new Ubicacion(-34.60011743355092, -58.417371449916324), 500, almagro)
    );

    Tecnico otroTecnico = Tecnico.con(
        Usuario.conEmail("alskf@sdefgwe.com"),
        "Joaquín",
        "Gándola",
        new Documento(TipoDocumento.DNI, "82738291"),
        "22-82738291-1",
        Contacto.conWhatsApp("8881928172"),
        MedioDeNotificacion.WHATSAPP,
        new Area(new Ubicacion(-34.60711989660622, -58.414045825102896), 400, almagro)
    );


    tecnicoRepository.guardar(unTecnico);
    tecnicoRepository.guardar(otroTecnico);

  }

  @Test
  public void testBusquedaPorbarrio() {
    List<Tecnico> posiblesTecnicosEnAlmagro = tecnicoRepository.obtenerPorBarrio(almagro);
    Assertions.assertEquals(2, posiblesTecnicosEnAlmagro.size());

    List<Tecnico> posiblesTecnicosEnSanTelmo = tecnicoRepository.obtenerPorBarrio(sanTelmo);
    Assertions.assertTrue(posiblesTecnicosEnSanTelmo.isEmpty());
  }
}
