package ar.edu.utn.frba.dds.models.data;

import lombok.Getter;

@Getter
public class Documento {

    private Integer numero;

    private TipoDocumento tipo;

    public Documento(Integer numero, TipoDocumento tipo) {
        this.numero = numero;
        this.tipo = tipo;
    }
}