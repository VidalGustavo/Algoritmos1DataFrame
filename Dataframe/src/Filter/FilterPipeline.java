package Filter;

import DataFrame.DataFrame;
import java.util.ArrayList;

/**
 * Implementa un pipeline para filtrar filas de DataFrame basado en condiciones
 */
public class FilterPipeline {
    private ArrayList<FilterCondition> conditions;

    public FilterPipeline() {
        this.conditions = new ArrayList<>();
    }

    public FilterPipeline where(String columnName, String operator, Object value) {
        conditions.add(new ComparisonFilter(columnName, operator, value));
        return this;
    }

    public FilterPipeline and(String columnName, String operator, Object value) {
        if (conditions.isEmpty()) {
            return where(columnName, operator, value);
        }
        
        FilterCondition previous = conditions.remove(conditions.size() - 1);
        FilterCondition newCondition = new ComparisonFilter(columnName, operator, value);
        conditions.add(new LogicalAndFilter(previous, newCondition));
        return this;
    }


    public FilterPipeline or(String columnName, String operator, Object value) {
        if (conditions.isEmpty()) {
            return where(columnName, operator, value);
        }
        
        FilterCondition previous = conditions.remove(conditions.size() - 1);
        FilterCondition newCondition = new ComparisonFilter(columnName, operator, value);
        conditions.add(new LogicalOrFilter(previous, newCondition));
        return this;
    }

    
    public DataFrame apply(DataFrame df) {
        if (conditions.isEmpty()) {
            return df.copy();
        }

        DataFrame result = new DataFrame();
        
        // Agrega todas las columnas al DataFrame resultado
        for (int i = 0; i < df.getNumCol(); i++) {
            result.addColumn(df.getColumn(i).copy());
        }

        // Solo mantiene las filas que cumplen todas las condiciones
        for (int i = 0; i < df.getNumRow(); i++) {
            boolean rowMatches = true;
            for (FilterCondition condition : conditions) {
                if (!condition.evaluate(df, i)) {
                    rowMatches = false;
                    break;
                }
            }
            if (!rowMatches) {
                // Elimina la fila de todas las columnas si no cumple las condiciones
                for (int j = 0; j < df.getNumCol(); j++) {
                    result.getColumn(j).getList().remove(result.getNumRow() - 1);
                }
            }
        }

        return result;
    }
}