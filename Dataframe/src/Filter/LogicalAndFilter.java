package Filter;

import DataFrame.DataFrame;

/**
 * Implementa la operación lógica AND entre dos condiciones de filtro
 */
public class LogicalAndFilter implements FilterCondition {
    private FilterCondition left;
    private FilterCondition right;

    public LogicalAndFilter(FilterCondition left, FilterCondition right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(DataFrame df, int rowIndex) {
        return left.evaluate(df, rowIndex) && right.evaluate(df, rowIndex);
    }
}