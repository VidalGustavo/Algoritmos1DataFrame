package interfaces;
import java.io.IOException;
import java.nio.file.Path;

public interface ToCSV {
    /**
     * Exporta el DataFrame a un archivo CSV
     * @param filePath Ruta completa del archivo (incluyendo nombre y extensión)
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    default void exportToCSV(Path filePath) throws IOException {
        // Implementación por defecto que llama a la sobrecarga con encabezados incluidos
        exportToCSV(filePath, true);
    }

    /**
     * Exporta el DataFrame a un archivo CSV incluyendo encabezados
     * @param filePath Ruta completa del archivo (incluyendo nombre y extensión)
     * @param includeHeaders Indica si se deben incluir los encabezados
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    default void exportToCSV(Path filePath, boolean includeHeaders) throws IOException {
        // Implementación por defecto que llama a la sobrecarga con delimitador y codificación por defecto
        exportToCSV(filePath, includeHeaders, ",", "UTF-8");
    }

    /**
     * Exporta el DataFrame a un archivo CSV con opciones avanzadas
     * @param filePath Ruta completa del archivo
     * @param includeHeaders Incluir encabezados
     * @param delimiter Carácter delimitador
     * @param encoding Codificación del archivo (ej: "UTF-8")
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    default void exportToCSV(Path filePath, boolean includeHeaders, String delimiter, String encoding) throws IOException {
        // Implementación por defecto que llama a la sobrecarga sin encabezados
        exportToCSV(filePath, includeHeaders, delimiter, encoding);
    }

    /**
     * Retorna el contenido del DataFrame como String CSV
     * @param includeHeaders Indica si se deben incluir encabezados
     * @return String con formato CSV
     */
    default String toCSVString(boolean includeHeaders) {
        return toCSVString(includeHeaders, ",");
    }

    /**
     * Retorna el contenido del DataFrame como String CSV con opciones
     * @param includeHeaders Incluir encabezados
     * @param delimiter Carácter delimitador
     * @return String con formato CSV
     */
    default String toCSVString(boolean includeHeaders, String delimiter) {
        StringBuilder sb = new StringBuilder();
        if (includeHeaders) {
            // Agregar encabezados
            for (Column col : columns) {
                sb.append(col.getName()).append(delimiter);
            }
            sb.setLength(sb.length() - 1); // Eliminar último delimitador
            sb.append("\n");
        }
        // Agregar datos
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                sb.append(columns.get(j).getValue(i)).append(delimiter);
            }
            sb.setLength(sb.length() - 1); // Eliminar último delimitador
            sb.append("\n");
        }
        return sb.toString();
    }
}
