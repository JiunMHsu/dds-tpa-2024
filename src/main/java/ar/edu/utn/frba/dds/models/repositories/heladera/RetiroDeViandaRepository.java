package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class RetiroDeViandaRepository implements WithSimplePersistenceUnit {

    public void guardar(RetiroDeVianda retiroDeVianda) {
        withTransaction(() -> entityManager().persist(retiroDeVianda));
    }

    public List<RetiroDeVianda> obtenerPorTarjeta(TarjetaPersonaVulnerable tarjeta) {
        return entityManager()
                .createQuery("from RetiroDeVianda r where r.tarjetaPersonaVulnerable = :tarjeta", RetiroDeVianda.class)
                .setParameter("tarjeta", tarjeta)
                .getResultList();
    }

    public List<RetiroDeVianda> obtenerPorPersonaVulnerable(PersonaVulnerable persona) {
        return entityManager()
                .createQuery("from RetiroDeVianda r where r.tarjetaPersonaVulnerable.duenio = :persona", RetiroDeVianda.class)
                .setParameter("persona", persona)
                .getResultList();
    }

}
