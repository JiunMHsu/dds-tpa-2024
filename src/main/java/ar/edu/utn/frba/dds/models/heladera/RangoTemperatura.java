package ar.edu.utn.frba.dds.models.heladera;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class RangoTemperatura {

    @Column(name = "temperatura_maxima", nullable = false)
    private Double maxima;

    @Column(name = "temperatura_minima", nullable = false)
    private Double minima;

    public RangoTemperatura(Double maxima, Double minima) {
        this.maxima = maxima;
        this.minima = minima;
    }

    public RangoTemperatura() {
        this.maxima = null;
        this.minima = null;
    }

    public Boolean incluye(Double temperatura) {
        return (maxima != null)
                && (minima != null)
                && (temperatura < maxima)
                && (temperatura > minima);
    }
}
