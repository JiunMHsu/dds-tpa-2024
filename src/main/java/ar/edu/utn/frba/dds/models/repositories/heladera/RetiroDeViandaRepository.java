package ar.edu.utn.frba.dds.models.repositories.heladera;

import ar.edu.utn.frba.dds.models.entities.heladera.RetiroDeVianda;
import ar.edu.utn.frba.dds.models.entities.personaVulnerable.PersonaVulnerable;
import ar.edu.utn.frba.dds.models.entities.tarjeta.TarjetaPersonaVulnerable;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;
import java.util.Optional;

public class RetiroDeViandaRepository implements IOperacionPorTarjetaRepository<RetiroDeVianda>, WithSimplePersistenceUnit {

    @Override
    public void guardar(RetiroDeVianda retiroDeVianda) {
        withTransaction(() -> entityManager().persist(retiroDeVianda));
    }

    // TODO
    @Override
    public void actualizar(RetiroDeVianda entidad) {

    }

    // TODO
    @Override
    public void eliminar(RetiroDeVianda entidad) {

    }

    // TODO
    @Override
    public Optional<RetiroDeVianda> buscarPorId(String id) {
        return Optional.empty();
    }

    // TODO
    @Override
    public List<RetiroDeVianda> buscarTodos() {
        return List.of();
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

    // TODO
    @Override
    public List<RetiroDeVianda> buscarPorTarjeta(String tarjeta) {
        return List.of();
    }

    // TODO
    @Override
    public Optional<RetiroDeVianda> buscarUltimoPorTarjeta(String tarjeta) {
        return Optional.empty();
    }
}
