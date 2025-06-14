package Filter;

import Celda.Celda;
import DataFrame.DataFrame;

/**
 * Interfaz para condiciones de filtro que pueden ser aplicadas a filas de DataFrame
 */
public interface FilterCondition {
    /**
     * Evalúa si una fila en el DataFrame cumple la condición de filtro
     */
    boolean evaluate(DataFrame df, int rowIndex);
}