package Archivos;

import DataFrame.DataFrame;

public interface LectorArchivos {
    DataFrame leer(String rutaArchivo, boolean encabezado);

    DataFrame leer(String rutaArchivo, String separador, boolean encabezado);
}
