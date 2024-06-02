package ar.edu.utn.frba.dds.models.tarjeta;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Setter
@Getter

public class GeneradorDeCodigo {

    private List<Tarjeta> tarjetasRegistradas; // hardcodeado para los test

    public String generadorCodigo(List<Tarjeta> tarjetas) {
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String codigoTarjeta;

        do {
            StringBuilder codigoBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int indiceAleatorio = numeroAleatorioEnRango(0, banco.length() - 1);
                char caracterAleatorio = banco.charAt(indiceAleatorio);
                codigoBuilder.append(caracterAleatorio);
            }
            codigoTarjeta = codigoBuilder.toString();
        } while (comprobarCodigosRepetidos(codigoTarjeta, this.tarjetasRegistradas));

        return codigoTarjeta;
    }

    private int numeroAleatorioEnRango(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }

    private boolean comprobarCodigosRepetidos(String codigo, List<Tarjeta> tarjetas) {
        for (Tarjeta tarjeta : tarjetasRegistradas) {
            if (tarjeta.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

}
