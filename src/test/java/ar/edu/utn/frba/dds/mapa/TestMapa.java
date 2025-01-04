package ar.edu.utn.frba.dds.mapa;


import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.models.entities.data.Barrio;
import ar.edu.utn.frba.dds.models.entities.data.Calle;
import ar.edu.utn.frba.dds.models.entities.data.Direccion;
import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import ar.edu.utn.frba.dds.models.entities.heladera.Heladera;
import ar.edu.utn.frba.dds.models.entities.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.models.repositories.heladera.HeladeraRepository;
import ar.edu.utn.frba.dds.services.mapa.MapService;
import org.junit.jupiter.api.Test;

public class TestMapa {

  @Test
  public void testMapa() {

    MapService mapService = new MapService(ServiceLocator.instanceOf(HeladeraRepository.class));


    Ubicacion u1 = new Ubicacion(-34.65980101272007, -58.46812313140898); // UTN Lugano
    Ubicacion u2 = new Ubicacion(-34.60400600198791, -58.38577280188418); // Guerrin
    Ubicacion u3 = new Ubicacion(-34.59861794351904, -58.420114964305405); // UTN Medrano
    Ubicacion u4 = new Ubicacion(-34.64428344483126, -58.453748279907344); // Hospital General de Agudos Parmenio Piñero
    Ubicacion u5 = new Ubicacion(-34.619033507138035, -58.44661616661939); // Ferro
    Ubicacion u6 = new Ubicacion(-34.562359819003255, -58.45626500682845); // Cabildo y Juramento (Liena D)
    Ubicacion u7 = new Ubicacion(-34.60286430113411, -58.41129164421226); // Abasto Shopping
    Ubicacion u8 = new Ubicacion(-34.58884555335712, -58.43025865436272); // Plaza Serrano
    Ubicacion u9 = new Ubicacion(-34.59453003815602, -58.45045997071465); // Atlanta
    Ubicacion u10 = new Ubicacion(-34.60802625811091, -58.37230040014313); // Plaza de Mayo
    Ubicacion u11 = new Ubicacion(-34.6393172975206, -58.36271557071799); // Caminito de la Boca
    Ubicacion u12 = new Ubicacion(-34.58138694779834, -58.42103364660331); // Plaza Italia
    Ubicacion u13 = new Ubicacion(-34.58329462979221, -58.39133076001772); // Facultad de Derecho
    Ubicacion u14 = new Ubicacion(-34.60385381907532, -58.382421802790795); // Obelisco (por Av. Corrientes)
    Ubicacion u15 = new Ubicacion(-34.556405019747, -58.451607904832194); // Barrio Chino (Arribeños)

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

    Direccion d1 = new Direccion(b1, c3, 2300, u1);  // UTN Lugano
    Direccion d2 = new Direccion(b2, c14, 1368, u2);  // Guerrin
    Direccion d3 = new Direccion(b3, c5, 951, u3);   // UTN Medrano
    Direccion d4 = new Direccion(b4, c4, 1301, u4);  // Hospital General de Agudos Parmenio Piñero
    Direccion d5 = new Direccion(b5, c9, 260, u5);   // Ferro
    Direccion d6 = new Direccion(b12, c7, 2061, u6);  // Cabildo y Juramento
    Direccion d7 = new Direccion(b6, c1, 611, u7);  // Abasto Shopping
    Direccion d8 = new Direccion(b7, c8, 1595, u8);  // Plaza Serrano
    Direccion d9 = new Direccion(b8, c11, 457, u9);  // Atlanta
    Direccion d10 = new Direccion(b9, c2, 360, u10); // Plaza de Mayo
    Direccion d11 = new Direccion(b10, c6, 2005, u11); // Caminito de la Boca
    Direccion d12 = new Direccion(b7, c12, 4138, u12); // Plaza Italia
    Direccion d13 = new Direccion(b11, c13, 52, u13);  // Facultad de Derecho
    Direccion d14 = new Direccion(b2, c14, 1113, u14);  // Obelisco
    Direccion d15 = new Direccion(b12, c10, 2290, u15); // Barrio Chino

    HeladeraRepository heladeraRepository = new HeladeraRepository();

    heladeraRepository.beginTransaction();
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
    heladeraRepository.commitTransaction();

    try {
      mapService.crearGeoJson();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}