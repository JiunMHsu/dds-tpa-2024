package ar.edu.utn.frba.dds;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class PruebaDB implements WithSimplePersistenceUnit {
    public static void main(String[] args) {
        PruebaDB instance = new PruebaDB();
        instance.impactarEnBase();
    }

    public void impactarEnBase() {

        withTransaction(() -> {
        });
    }
}
