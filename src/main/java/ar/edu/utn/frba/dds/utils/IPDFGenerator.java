package ar.edu.utn.frba.dds.utils;

import java.util.Map;

public interface IPDFGenerator {
    String generateDocument(String title, Map<String, Integer> data);
}
