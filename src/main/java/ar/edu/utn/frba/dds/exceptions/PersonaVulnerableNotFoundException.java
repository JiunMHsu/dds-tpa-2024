package ar.edu.utn.frba.dds.exceptions;

public class PersonaVulnerableNotFoundException extends RuntimeException {
    public PersonaVulnerableNotFoundException(String message) {
        super(message);
    }
}
