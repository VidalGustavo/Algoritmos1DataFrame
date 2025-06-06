package Exportador;
import DataFrame.DataFrame;
import Column.Column;
import Celda.Celda;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExpoCSV<T> implements Exportador<T> {
    
    @Override
    public void expo(DataFrame<T> dataframe, String nombreArchivo) {
        if (dataframe == null || dataframe.getColumns().isEmpty()) {
            System.err.println("El DataFrame está vacío o no tiene columnas.");
            return;
        }
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            System.err.println("El nombre del archivo no puede ser nulo o vacío.");
            return;
        }           
        nombreArchivo = nombreArchivo.endsWith(".csv") ? nombreArchivo : nombreArchivo + ".csv";
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            // Escribir encabezados (nombres de columnas)
            for (int i = 0; i < dataframe.getColumns().size(); i++) {
                Column<Celda<?>> columna = dataframe.getColumns().get(i);
                writer.write(columna.getName());
                if (i < dataframe.getColumns().size() - 1) {
                    writer.write(",");
                }
            }// se usa ? y no T porque no sabes que tipo de dato es
            // estamos parados en tiempo de compilación
            writer.write("\n");
            
            // Escribir datos
            for (int row = 0; row < dataframe.getNumRow(); row++) {
                for (int col = 0; col < dataframe.getColumns().size(); col++) {
                    Celda<?> celda = dataframe.getCelda(row, col);
                    // Escapar comas y comillas en el valor
                    String value = celda.getValue().toString()
                        .replace("\"", "\"\"")
                        .replace(",", "\\,");
                    
                    // Si el valor contiene comas o saltos de línea, lo encerramos en comillas
                    if (value.contains(",") || value.contains("\n")) {
                        value = "\"" + value + "\"";
                    }
                    
                    writer.write(value);
                    
                    if (col < dataframe.getColumns().size() - 1) {
                        writer.write(",");
                    }
                }
                writer.write("\n");
            }
            
            System.out.println("DataFrame exportado exitosamente a " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al exportar el DataFrame: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}