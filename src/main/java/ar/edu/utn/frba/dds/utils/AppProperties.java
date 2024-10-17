package ar.edu.utn.frba.dds.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    private static AppProperties instance = null;
    private final Properties props;

    private AppProperties() {
        this.props = new Properties();
        this.readProperties();
    }

    public static AppProperties getInstance() {
        if (instance == null) {
            instance = new AppProperties();
        }
        return instance;
    }

    private void readProperties() {
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            this.props.load(input);
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo de configuración");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error de entrada salida");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String propertyFromName(String name) {
        return this.props.getProperty(name);
    }

    public Integer intPropertyFromName(String name) {
        return Integer.parseInt(propertyFromName(name));
    }

    public Boolean boolPropertyFromName(String name) {
        return Boolean.parseBoolean(propertyFromName(name));
    }
}
