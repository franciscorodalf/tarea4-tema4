package es.ies.puerto.file.tres;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileArmasCsv {

    private final String archivoCSV = "src/main/resources/tres.csv";
    File file = new File(archivoCSV);

    public List<Arma> obtenerArmas() {
        
        List<Arma> armas = new ArrayList<>();
        File file = new File(archivoCSV);

        if (!file.exists()) {
            return armas;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] columnas = linea.split(", ");
                for (int i = 0; i < columnas.length; i++) {
                    columnas[i] = (columnas[i].trim());
                }

                if (columnas.length == 5) {
                    String id = columnas[0];
                    String nombre = columnas[1];
                    String descripcion = columnas[2];
                    String origen = columnas[3];
                    int fuerza = Integer.parseInt(columnas[4]);

                    armas.add(new Arma(id, nombre, descripcion, origen, fuerza));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return armas;
    }

    public Arma obtenerArma(Arma arma) {
        List<Arma> armas = obtenerArmas();
        for (Arma armaBuscar : armas) {
            if (armaBuscar.getId().equals(arma.getId())) {
                return armaBuscar;
            }
        }
        return null;
    }

    public void addArma(Arma arma) {
        List<Arma> armas = obtenerArmas();

        for (Arma armaExistente : armas) {
            if (armaExistente.getId().equals(arma.getId())) {
                System.err.println("El arma con ID " + arma.getId() + " ya existe.");
                return;
            }
        }

        armas.add(arma);
        updateFile(armas, file);
        System.out.println("Arma añadida exitosamente.");
    }

    public void deleteArma(Arma arma) {

    }

    public void updateArma(Arma arma) {

    }

    protected boolean updateFile(List<Arma> armas, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Arma arma : armas) {

                writer.write(String.format(
                        arma.getId(), arma.getNombre(),
                        arma.getDescripcion(), arma.getOrigen(),
                        arma.getFuerza()));
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al actualizar el fichero de armas: " + e.getMessage());
            return false;
        }

    }

}
