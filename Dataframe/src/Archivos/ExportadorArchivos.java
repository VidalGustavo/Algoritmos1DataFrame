package Archivos;
import DataFrame.DataFrame;
// me tira error porque uso Dataframe T 
// y dataframe no es generico, pero entiendo 
// que se solucionará en breve eso. 
public interface ExportadorArchivos {
    void exportar(DataFrame dataframe, String nombreArchivo);
}