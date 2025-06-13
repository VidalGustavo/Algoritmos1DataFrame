package Filter;

import DataFrame.DataFrame;

/**
 * Implementa la operación lógica OR entre dos condiciones de filtro
 */
public class LogicalOrFilter implements FilterCondition {
    private FilterCondition left;
    private FilterCondition right;

    public LogicalOrFilter(FilterCondition left, FilterCondition right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(DataFrame df, int rowIndex) {
        return left.evaluate(df, rowIndex) || right.evaluate(df, rowIndex);
    }
}