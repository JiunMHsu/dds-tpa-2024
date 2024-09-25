package ar.edu.utn.frba.dds.puntoIdeal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.models.entities.data.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestPuntoIdeal {

    IAdapterPuntoIdeal adapterPuntoIdeal;
    PuntoIdeal puntoIdeal;
    Ubicacion unaUbicacion, otraUbicacion;
    List<Ubicacion> puntosRecomendados;

    @BeforeEach
    public void setup() {
        unaUbicacion = new Ubicacion(761.0, 345.0);
        otraUbicacion = new Ubicacion(84.0, 198.0);
        puntosRecomendados = new ArrayList<>();
        puntosRecomendados.add(unaUbicacion);
        puntosRecomendados.add(otraUbicacion);

        adapterPuntoIdeal = mock(IAdapterPuntoIdeal.class);
        when(adapterPuntoIdeal.puntoIdeal(976.0, 987.0, 5.0)).thenReturn(puntosRecomendados);

        puntoIdeal = new PuntoIdeal(adapterPuntoIdeal);
    }

    @Test
    @DisplayName("Se puede consultar Ubicaciones recomendadas.")
    public void consultaUbicacionesRecomendadas() {
        Ubicacion unaUbicacion = new Ubicacion(976.0, 987.0);
        Assertions.assertIterableEquals(puntosRecomendados, puntoIdeal.puntosIdeales(unaUbicacion, 5.0));
    }
}
