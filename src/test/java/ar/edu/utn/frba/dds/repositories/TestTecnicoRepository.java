package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.mensajeria.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.entities.data.Area;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Contacto;
import ar.edu.utn.frba.dds.models.entities.data.Documento;
import ar.edu.utn.frba.dds.models.entities.data.TipoDocumento;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.tecnico.Tecnico;
import ar.edu.utn.frba.dds.models.repositories.tecnico.TecnicoRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTecnicoRepository implements SimplePersistenceTest {

    private TecnicoRepository tecnicoRepository;
    private Barrio almagro;
    private Barrio sanTelmo;

    @BeforeEach
    public void setUp() {
        tecnicoRepository = new TecnicoRepository();

        almagro = new Barrio("Almagro");
        sanTelmo = new Barrio("San Telmo");

        Tecnico unTecnico = Tecnico.con(
                "Matias Leonel",
                "Juncos Mieres",
                new Documento("12345678", TipoDocumento.DNI),
                "24-12345678-0",
                Contacto.conTelegram("7652931546"),
                MedioDeNotificacion.TELEGRAM,
                new Area(new Ubicacion(-34.60011743355092, -58.417371449916324), 500.0, almagro)
        );

        Tecnico otroTecnico = Tecnico.con(
                "Joaquín",
                "Gándola",
                new Documento("82738291", TipoDocumento.DNI),
                "22-82738291-1",
                Contacto.conWhatsApp("8881928172"),
                MedioDeNotificacion.WHATSAPP,
                new Area(new Ubicacion(-34.60711989660622, -58.414045825102896), 400.0, almagro)
        );

        // guardar los tecnicos
        withTransaction(() -> {
            tecnicoRepository.guardar(unTecnico);
            tecnicoRepository.guardar(otroTecnico);
        });

    }

    @Test
    public void testBusquedaPorbarrio() {
        List<Tecnico> posiblesTecnicosEnAlmagro = tecnicoRepository.obtenerPorBarrio(almagro);
        Assertions.assertEquals(2, posiblesTecnicosEnAlmagro.size());

        List<Tecnico> posiblesTecnicosEnSanTelmo = tecnicoRepository.obtenerPorBarrio(sanTelmo);
        Assertions.assertTrue(posiblesTecnicosEnSanTelmo.isEmpty());
    }
}
