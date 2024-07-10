package ar.edu.utn.frba.dds.models.convertidorArchivos;

import ar.edu.utn.frba.dds.models.colaboracion.TipoColaboracion;
import ar.edu.utn.frba.dds.models.data.Documento;
import ar.edu.utn.frba.dds.models.data.TipoDocumento;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CargadorColaboraciones {

  public List<ColaboradoresPrevios> convertirALista(Path path) {

    List<ColaboradoresPrevios> listaDeColaboradores = new ArrayList<>();

    try {
      CSVParser csvParser = CSVParser.parse(Files.newBufferedReader(path), CSVFormat.DEFAULT
          .withHeader("Tipo Doc",
              "Documento",
              "Nombre",
              "Apellido",
              "Mail",
              "Fecha de colaboración",
              "Forma de colaboración",
              "Cantidad")
          .withFirstRecordAsHeader());

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

      for (CSVRecord csvRecord : csvParser) {
        ColaboradoresPrevios colaborador = new ColaboradoresPrevios();
        Documento documento = new Documento(Integer.parseInt(csvRecord.get("Documento")),
            TipoDocumento.valueOf(csvRecord.get("Tipo Doc")));

        colaborador.setDocumento(documento);
        colaborador.setNombre(csvRecord.get("Nombre"));
        colaborador.setApellido(csvRecord.get("Apellido"));
        colaborador.setEmail(csvRecord.get("Mail"));
        colaborador.setFechaDeColaboracion(LocalDate.parse(csvRecord.get("Fecha de colaboración"), formatter).atStartOfDay());
        colaborador.setFormaDeColaboracion(TipoColaboracion.valueOf(csvRecord.get("Forma de colaboración")));
        colaborador.setCantidad(Integer.parseInt(csvRecord.get("Cantidad")));

        listaDeColaboradores.add(colaborador);
      }
    } catch (IOException error) {
      error.printStackTrace();
    }

    return listaDeColaboradores;
  }
}
