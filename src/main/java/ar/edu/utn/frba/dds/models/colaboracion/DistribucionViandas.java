package ar.edu.utn.frba.dds.models.colaboracion;

import ar.edu.utn.frba.dds.models.heladera.Heladera;
import java.time.LocalDateTime;
import java.util.*;
import ar.edu.utn.frba.dds.models.usuario.Persona;
import ar.edu.utn.frba.dds.models.vianda.Vianda;


public class DistribucionVianda {
    private Heladera origen;
    private Heladera destino;
    private List<Vianda> viandasAMover;
    private String motivo;
    private LocalDateTime fechaDistribucion;
    private Persona colaboradorHumano;
}
