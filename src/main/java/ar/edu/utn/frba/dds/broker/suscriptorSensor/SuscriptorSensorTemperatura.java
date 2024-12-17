package ar.edu.utn.frba.dds.broker.suscriptorSensor;

import ar.edu.utn.frba.dds.config.ServiceLocator;
import ar.edu.utn.frba.dds.controllers.heladera.HeladeraController;
import ar.edu.utn.frba.dds.models.entities.sensor.Sensor;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SuscriptorSensorTemperatura extends SuscriptorSensor {

    private final long milisegundosEntreMensajes;
    private Instant tiempoUltimoMensaje;

    private SuscriptorSensorTemperatura(Sensor suscriptor,
                                        long intervaloDeMensajes,
                                        int frecuenciaDeVerificacion) {
        super(suscriptor);
        milisegundosEntreMensajes = intervaloDeMensajes;
        ScheduledExecutorService planificador = Executors.newScheduledThreadPool(1);
        planificador.scheduleAtFixedRate(this::verificarRetrasoMensaje, 0, frecuenciaDeVerificacion, TimeUnit.SECONDS);
    }

    public static SuscriptorSensorTemperatura para(Sensor suscriptor) {
        return new SuscriptorSensorTemperatura(suscriptor, 60000 * 5, 60);
    }

    public static SuscriptorSensorTemperatura para(Sensor suscriptor,
                                                   long intervaloEntreMensajes,
                                                   int frecuenciaDeVerificacion) {
        return new SuscriptorSensorTemperatura(suscriptor, intervaloEntreMensajes, frecuenciaDeVerificacion);
    }

    @Override
    public void recibirMensaje(String mensaje) {
        ServiceLocator.instanceOf(HeladeraController.class).recibirTemperatura(Double.parseDouble(mensaje), getSuscriptorSensor().getHeladera());
        tiempoUltimoMensaje = Instant.now();
    }

    public void manejarRetrasoMensaje() {
        ServiceLocator.instanceOf(HeladeraController.class).recibirFallaConexion(getSuscriptorSensor().getHeladera());
    }

    private void verificarRetrasoMensaje() {
        if (tiempoUltimoMensaje == null) {
            return;
        }

        Duration duracion = Duration.between(tiempoUltimoMensaje, Instant.now());
        if (duracion.toMillis() > milisegundosEntreMensajes) {
            System.out.println("No se recibio la temperatura por "
                    + duracion.toSeconds()
                    + " segundos");
            manejarRetrasoMensaje();
        }
    }
}
