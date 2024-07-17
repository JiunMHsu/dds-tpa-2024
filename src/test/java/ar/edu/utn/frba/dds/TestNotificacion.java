package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.mensajeria.INotificador;

import ar.edu.utn.frba.dds.models.colaborador.Colaborador;
import ar.edu.utn.frba.dds.models.tecnico.Tecnico;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

public class TestNotificacion {

    INotificador notificador;

    Colaborador colaborador;

    Tecnico tecnico;

    @BeforeEach
    public void setup() {

        colaborador = new Colaborador();
        tecnico = new Tecnico("", "", "", );

        notificador = mock(INotificador.class);



    }

}
