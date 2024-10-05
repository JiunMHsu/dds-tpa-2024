package ar.edu.utn.frba.dds.utils;

import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

public class Initializer implements WithSimplePersistenceUnit {

    public static void init(Runnable datasetInitializer) {
        Initializer instance = new Initializer();

        if (datasetInitializer == null) {
            return;
        }

        instance.cleanupDatabase();
        datasetInitializer.run();
    }

    public static void withSuperUser() {
        
    }

    private void cleanupDatabase() {
        withTransaction(() -> {
        });
    }
}
