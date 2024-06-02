package ar.edu.utn.frba.dds.models.tarjeta;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeneradorDeCodigo {

    private List<Tarjeta> tarjetasRegistradas; // hardcodeado para los test

    public String generadorCodigo(RegistroTarjetas registroTarjetas) {
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String codigoTarjeta;

        do {
            StringBuilder codigoBuilder = new StringBuilder();
            for (int i = 0; i < 11; i++) {
                int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
                char caracterAleatorio = banco.charAt(indiceAleatorio);
                codigoBuilder.append(caracterAleatorio);
            }
            codigoTarjeta = codigoBuilder.toString();
        } while (comprobarCodigosRepetidos(codigoTarjeta, registroTarjetas));

        return codigoTarjeta;
    }

    public static int numeroAleatorioEnRango(int minimo, int maximo) {

        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    public static Boolean comprobarCodigosRepetidos(String codigo, RegistroTarjetas registroTarjetas) {
        List<Tarjeta> tarjetasRegistradas = registroTarjetas.getTarjetasRegistradas();
        for (Tarjeta tarjeta : tarjetasRegistradas) {
            if (tarjeta.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

}
