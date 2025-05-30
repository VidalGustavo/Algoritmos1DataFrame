package interfaces;
import java.io.IOException;
import java.nio.file.Path;

public interface ToCSV {
    /**
     * Exporta el DataFrame a un archivo CSV
     * @param filePath Ruta completa del archivo (incluyendo nombre y extensión)
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    void exportToCSV(Path filePath) throws IOException;
    
    /**
     * Exporta el DataFrame a un archivo CSV incluyendo encabezados
     * @param filePath Ruta completa del archivo (incluyendo nombre y extensión)
     * @param includeHeaders Indica si se deben incluir los encabezados
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    void exportToCSV(Path filePath, boolean includeHeaders) throws IOException;
    
    /**
     * Exporta el DataFrame a un archivo CSV con opciones avanzadas
     * @param filePath Ruta completa del archivo
     * @param includeHeaders Incluir encabezados
     * @param delimiter Carácter delimitador
     * @param encoding Codificación del archivo (ej: "UTF-8")
     * @throws IOException Si ocurre un error al escribir el archivo
     */
    void exportToCSV(Path filePath, boolean includeHeaders, String delimiter, String encoding) throws IOException;
    
    /**
     * Retorna el contenido del DataFrame como String CSV
     * @param includeHeaders Indica si se deben incluir encabezados
     * @return String con formato CSV
     */
    String toCSVString(boolean includeHeaders);
    
    /**
     * Retorna el contenido del DataFrame como String CSV con opciones
     * @param includeHeaders Incluir encabezados
     * @param delimiter Carácter delimitador
     * @return String con formato CSV
     */
    String toCSVString(boolean includeHeaders, String delimiter);
}
    