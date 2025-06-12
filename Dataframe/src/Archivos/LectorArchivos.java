package Archivos;

import DataFrame.DataFrame;

public interface LectorArchivos {
    DataFrame leer(String rutaArchivo);

    DataFrame leer(String rutaArchivo, String separador);
}
