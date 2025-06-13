package Filter;

import Celda.Celda;
import DataFrame.DataFrame;

/**
 * Implementa operaciones de comparación para filtrado de DataFrame
 */
public class ComparisonFilter implements FilterCondition {
    private String columnName;
    private String operator;
    private Object value;

    public ComparisonFilter(String columnName, String operator, Object value) {
        if (!isValidOperator(operator)) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
        this.columnName = columnName;
        this.operator = operator;
        this.value = value;
    }

    private boolean isValidOperator(String op) {
        return op.equals("=") || op.equals("<") || op.equals(">") || 
               op.equals(">=") || op.equals("<=");
    }

    @Override
    public boolean evaluate(DataFrame df, int rowIndex) {
        Celda<?> cell = df.getCelda(rowIndex, columnName);
        if (cell == null || cell.getValue() == null) {
            return false;
        }

        if (!(cell.getValue() instanceof Comparable)) {
            throw new IllegalArgumentException("Column values must be comparable");
        }

        Comparable cellValue = (Comparable) cell.getValue();
        Comparable compareValue = (Comparable) value;

        switch (operator) {
            case "=":
                return cellValue.compareTo(compareValue) == 0;
            case "<":
                return cellValue.compareTo(compareValue) < 0;
            case ">":
                return cellValue.compareTo(compareValue) > 0;
            case ">=":
                return cellValue.compareTo(compareValue) >= 0;
            case "<=":
                return cellValue.compareTo(compareValue) <= 0;
            default:
                return false;
        }
    }
}