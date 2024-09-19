package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.data.Calle;
import ar.edu.utn.frba.dds.models.data.Direccion;
import ar.edu.utn.frba.dds.models.data.Ubicacion;
import ar.edu.utn.frba.dds.models.heladera.EstadoHeladera;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.RangoTemperatura;
import ar.edu.utn.frba.dds.repository.heladera.HeladeraRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class PruebaDB implements WithSimplePersistenceUnit {

    private HeladeraRepository heladeraRepository;

    public static void main(String[] args) {
        PruebaDB instance = new PruebaDB();

        instance.heladeraRepository = new HeladeraRepository();
        instance.guardarHeladeras();
        instance.recuperarHeladeras();
    }

    private void guardarHeladeras() {
        Direccion direccion = new Direccion(
                new Barrio("Almagro"),
                new Calle("Medrano"),
                951,
                new Ubicacion(-34.59857981526152, -58.420110294464294)
        );

        Heladera heladera = Heladera.con(
                "UTN Medrano",
                direccion,
                20,
                new RangoTemperatura(5.0, -10.0)
        );

        heladera.setEstado(EstadoHeladera.INACTIVA);
        heladera.setViandas(0);

        heladeraRepository.agregar(heladera);
    }

    private void recuperarHeladeras() {
        Heladera heladeraRecuperada = heladeraRepository.buscarPorNombre("UTN Medrano");
        if (heladeraRecuperada != null) {
            System.out.println(
                    "Heladera: " + heladeraRecuperada.getNombre() + "\n" + "Id: " + heladeraRecuperada.getId().toString()
            );
        }
    }

    public void impactarEnBase() {
        withTransaction(() -> {
        });
    }
}
