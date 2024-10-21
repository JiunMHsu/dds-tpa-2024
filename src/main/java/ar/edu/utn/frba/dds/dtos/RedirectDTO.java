package ar.edu.utn.frba.dds.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedirectDTO {
    private String url;
    private String label;
}
