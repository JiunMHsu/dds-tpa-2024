package ar.edu.utn.frba.dds.serviceIntegration;

import ar.edu.utn.frba.dds.exceptions.BadAPIRequestException;
import ar.edu.utn.frba.dds.models.entities.puntoDonacion.PuntoDonacion;
import ar.edu.utn.frba.dds.services.puntoDonacion.PuntoDonacionService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PuntoDonacionServiceTest {

  private final String url = "http://localhost:3000/api";
  private final String authToken = "X7fIr5qr3URp8G3Y0+0a3itVf2SBR-cx";

  @Test
  @DisplayName("Se puede realizar la peticion paraColaborador exito.")
  public void fetchApiData() {
    PuntoDonacionService service = new PuntoDonacionService(url, authToken);
    List<PuntoDonacion> lugares = service.obneterPuntoDonacion(-34.61178, -58.417308, null, 100.0);

    lugares.forEach(PuntoDonacion::print);
  }

  @Test
  @DisplayName("Falla si faltan los argumentos nueva latitud y longitud.")
  public void requiredArgumentsUnprovided() {
    PuntoDonacionService service = new PuntoDonacionService(url, authToken);

    try {
      service.obneterPuntoDonacion(null, -58.417308, null, 100.0);
      Assertions.fail("no fallo nueva mas que se pase una latitud null");
    } catch (BadAPIRequestException e) {
      Assertions.assertNotNull(e);
    }
  }

  @Test
  @DisplayName("Falla si se hace una peticion sin credencial.")
  public void requiredCredentialUnprovided() {
    PuntoDonacionService service = new PuntoDonacionService(url, "");

    try {
      service.obneterPuntoDonacion(-34.61178, -58.417308, null, 100.0);
      Assertions.fail("no fallo nueva mas que no se pase un credencial");
    } catch (BadAPIRequestException e) {
      Assertions.assertEquals("No credentials provided", e.getMessage());
    }
  }
}