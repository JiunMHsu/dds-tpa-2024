package ar.edu.utn.frba.dds.models.stateless;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorDeMails {

    // TODO - Hacer la validacion
    // Por ahora dejo esto (validar el formato del mail)
    // No se si era eso lo que se refiere el TDO o si validar que realmente exista el mail cort posta

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static Boolean esValido(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
}
