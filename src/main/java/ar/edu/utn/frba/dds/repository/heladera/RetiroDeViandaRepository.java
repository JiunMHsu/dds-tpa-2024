package ar.edu.utn.frba.dds.repository.heladera;

import ar.edu.utn.frba.dds.models.data.Barrio;
import ar.edu.utn.frba.dds.models.heladera.Heladera;
import ar.edu.utn.frba.dds.models.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public class RetiroDeViandaRepository implements WithSimplePersistenceUnit {

    public void guardar(RetiroDeVianda retiroDeVianda) {
        withTransaction(()-> entityManager().persist(retiroDeVianda));
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
