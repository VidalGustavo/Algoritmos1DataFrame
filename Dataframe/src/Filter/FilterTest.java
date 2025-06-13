package Filter;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import DataFrame.DataFrame;
import Column.Column;
import Celda.Celda;
import java.util.ArrayList;

public class FilterTest {
    private DataFrame df;

    @Before
    public void setUp() {
        df = new DataFrame();
        
        // Create test data
        ArrayList<Celda<?>> ages = new ArrayList<>();
        ages.add(new Celda<>(25));
        ages.add(new Celda<>(30));
        ages.add(new Celda<>(35));
        ages.add(new Celda<>(40));
        ages.add(new Celda<>(45));
        
        ArrayList<Celda<?>> names = new ArrayList<>();
        names.add(new Celda<>("Alice"));
        names.add(new Celda<>("Bob"));
        names.add(new Celda<>("Charlie"));
        names.add(new Celda<>("David"));
        names.add(new Celda<>("Eve"));

        ArrayList<Celda<?>> scores = new ArrayList<>();
        scores.add(new Celda<>(85.5));
        scores.add(new Celda<>(90.0));
        scores.add(new Celda<>(78.5));
        scores.add(new Celda<>(92.5));
        scores.add(new Celda<>(88.0));

        df.addColumn(new Column<>("age", Integer.class, ages));
        df.addColumn(new Column<>("name", String.class, names));
        df.addColumn(new Column<>("score", Double.class, scores));
    }

    @Test
    public void testSimpleComparison() {
        // Test equals operator
        DataFrame result = df.filter()
            .where("age", "=", 30)
            .apply(df);
        assertEquals(1, result.getNumRow());
        assertEquals(30, result.getCelda(0, "age").getValue());
        assertEquals("Bob", result.getCelda(0, "name").getValue());

        // Test greater than operator
        result = df.filter()
            .where("age", ">", 35)
            .apply(df);
        assertEquals(2, result.getNumRow());
        assertTrue((Integer)result.getCelda(0, "age").getValue() > 35);
        assertTrue((Integer)result.getCelda(1, "age").getValue() > 35);
    }

    @Test
    public void testLogicalOperators() {
        // Test AND operator
        DataFrame result = df.filter()
            .where("age", ">", 30)
            .and("score", ">=", 90.0)
            .apply(df);
        assertEquals(1, result.getNumRow());
        assertEquals("David", result.getCelda(0, "name").getValue());

        // Test OR operator
        result = df.filter()
            .where("age", "<=", 30)
            .or("score", ">", 90.0)
            .apply(df);
        assertEquals(3, result.getNumRow());
    }

    @Test
    public void testComplexFilter() {
        DataFrame result = df.filter()
            .where("age", ">", 30)
            .and("score", ">=", 85.0)
            .or("name", "=", "Bob")
            .apply(df);
        assertEquals(4, result.getNumRow());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperator() {
        df.filter()
            .where("age", "!=", 30)
            .apply(df);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidColumn() {
        df.filter()
            .where("invalid_column", "=", 30)
            .apply(df);
    }
}