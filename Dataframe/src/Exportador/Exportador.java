package Exportador;
import DataFrame.DataFrame;
// me tira error porque uso Dataframe T 
// y dataframe no es generico, pero entiendo 
// que se solucionará en breve eso. 
public interface Exportador {
    void expo(DataFrame dataframe, String nombreArchivo);
}