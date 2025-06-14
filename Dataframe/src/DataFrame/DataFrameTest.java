package DataFrame;

import Column.Column;
import Celda.Celda;
import Filter.FilterPipeline;
import java.util.ArrayList;
import java.util.Arrays;

public class DataFrameTest {
    private DataFrame df;
    
    private void setUp() {
        df = new DataFrame();
        
        // Crear datos de prueba con diferentes tipos de datos
        ArrayList<Celda<?>> ages = new ArrayList<>();
        ages.add(new Celda<>(25, TipoDatos.NUMBER));
        ages.add(new Celda<>(30, TipoDatos.NUMBER));
        ages.add(new Celda<>(35, TipoDatos.NUMBER));
        ages.add(new Celda<>(null, TipoDatos.NUMBER)); // Valor NA para prueba
        ages.add(new Celda<>(45, TipoDatos.NUMBER));
        
        ArrayList<Celda<?>> names = new ArrayList<>();
        names.add(new Celda<>("Alice", TipoDatos.STRING));
        names.add(new Celda<>("Bob", TipoDatos.STRING));
        names.add(new Celda<>("Charlie", TipoDatos.STRING));
        names.add(new Celda<>("David", TipoDatos.STRING));
        names.add(new Celda<>("Eve", TipoDatos.STRING));

        ArrayList<Celda<?>> active = new ArrayList<>();
        active.add(new Celda<>(true, TipoDatos.BOOLEAN));
        active.add(new Celda<>(false, TipoDatos.BOOLEAN));
        active.add(new Celda<>(true, TipoDatos.BOOLEAN));
        active.add(new Celda<>(true, TipoDatos.BOOLEAN));
        active.add(new Celda<>(false, TipoDatos.BOOLEAN));

        // Agregar columnas al DataFrame
        df.addColumn(new Column<>("age", TipoDatos.NUMBER, ages));
        df.addColumn(new Column<>("name", TipoDatos.STRING, names));
        df.addColumn(new Column<>("active", TipoDatos.BOOLEAN, active));
    }

    private void testConstructors() {
        // Probar constructor por defecto
        DataFrame df1 = new DataFrame();
        if (df1.getNumRow() != 0) {
            System.out.println("ERROR: El constructor por defecto debería crear un DataFrame con 0 filas");
        }
        if (df1.getNumCol() != 0) {
            System.out.println("ERROR: El constructor por defecto debería crear un DataFrame con 0 columnas");
        }
        
        // Probar constructor con dimensiones
        DataFrame df2 = new DataFrame(3, 2);
        if (df2.getNumRow() != 3) {
            System.out.println("ERROR: El constructor con dimensiones debería crear un DataFrame con 3 filas");
        }
        if (df2.getNumCol() != 2) {
            System.out.println("ERROR: El constructor con dimensiones debería crear un DataFrame con 2 columnas");
        }
        
        // Probar constructor con columnas
        ArrayList<Column<?>> columns = new ArrayList<>();
        
        ArrayList<Celda<?>> col1Data = new ArrayList<>();
        col1Data.add(new Celda<>(1, TipoDatos.NUMBER));
        col1Data.add(new Celda<>(2, TipoDatos.NUMBER));
        Column<Celda<?>> col1 = new Column<>("col1", TipoDatos.NUMBER, col1Data);
        
        ArrayList<Celda<?>> col2Data = new ArrayList<>();
        col2Data.add(new Celda<>("a", TipoDatos.STRING));
        col2Data.add(new Celda<>("b", TipoDatos.STRING));
        Column<Celda<?>> col2 = new Column<>("col2", TipoDatos.STRING, col2Data);
        
        columns.add(col1);
        columns.add(col2);
        
        DataFrame df3 = new DataFrame(columns);
        if (df3.getNumRow() != 2) {
            System.out.println("ERROR: El constructor con columnas debería crear un DataFrame con 2 filas");
        }
        if (df3.getNumCol() != 2) {
            System.out.println("ERROR: El constructor con columnas debería crear un DataFrame con 2 columnas");
        }
        if (!df3.getCelda(0, "col1").getValue().equals(1)) {
            System.out.println("ERROR: El valor en (0, col1) debería ser 1");
        }
        if (!df3.getCelda(1, "col2").getValue().equals("b")) {
            System.out.println("ERROR: El valor en (1, col2) debería ser 'b'");
        }
    }
    
    private void testBasicOperations() {
        // Probar dimensiones
        if (df.getNumRow() != 5) {
            System.out.println("ERROR: El DataFrame debería tener 5 filas");
        }
        if (df.getNumCol() != 3) {
            System.out.println("ERROR: El DataFrame debería tener 3 columnas");
        }

        // Probar acceso a columnas
        Column<?> ageCol = df.getColumn("age");
        if (!ageCol.getName().equals("age")) {
            System.out.println("ERROR: El nombre de la columna debería ser 'age'");
        }
        if (ageCol.getTipoCelda() != TipoDatos.NUMBER) {
            System.out.println("ERROR: El tipo de la columna debería ser NUMBER");
        }

        // Probar acceso a celdas por índice
        if (!df.getCelda(0, 0).getValue().equals(25)) {
            System.out.println("ERROR: El valor en (0, 0) debería ser 25");
        }
        if (!df.getCelda(1, "name").getValue().equals("Bob")) {
            System.out.println("ERROR: El valor en (1, name) debería ser 'Bob'");
        }
        if (!(Boolean)df.getCelda("Charlie", "active").getValue()) {
            System.out.println("ERROR: El valor en (Charlie, active) debería ser true");
        }

        // Probar manejo de valores NA
        if (!df.getCelda(3, "age").getIsNA()) {
            System.out.println("ERROR: El valor en (3, age) debería ser NA");
        }
    }

    private void testDataModification() {
        // Probar modificación de celdas
        df.setCelda(0, "age", 26);
        if (!df.getCelda(0, "age").getValue().equals(26)) {
            System.out.println("ERROR: El valor en (0, age) debería ser 26 después de la modificación");
        }

        // Probar renombrado de columnas
        df.renameCol("age", "years");
        try {
            df.getColumn("years");
        } catch (Exception e) {
            System.out.println("ERROR: La columna 'years' debería existir después de renombrarla");
        }
        
        // Probar eliminación de filas
        int initialRows = df.getNumRow();
        df.deleteRow(0);
        if (df.getNumRow() != initialRows - 1) {
            System.out.println("ERROR: El número de filas debería disminuir en 1 después de la eliminación");
        }
        if (!df.getCelda(0, "name").getValue().equals("Bob")) {
            System.out.println("ERROR: La primera fila debería contener 'Bob' después de la eliminación");
        }

        // Probar eliminación de columnas
        int initialCols = df.getNumCol();
        df.deleteColumn("active");
        if (df.getNumCol() != initialCols - 1) {
            System.out.println("ERROR: El número de columnas debería disminuir en 1 después de la eliminación");
        }
    }

    private void testFiltering() {
        // Probar filtro simple
        DataFrame filtered = df.filter()
            .where("age", ">", 30)
            .apply(df);
        if (filtered.getNumRow() <= 0) {
            System.out.println("ERROR: El DataFrame filtrado debería tener al menos una fila");
        }
        if ((Integer)filtered.getCelda(0, "age").getValue() <= 30) {
            System.out.println("ERROR: El DataFrame filtrado debería contener solo edades > 30");
        }

        // Probar filtro complejo con AND
        filtered = df.filter()
            .where("age", ">", 30)
            .and("active", "=", true)
            .apply(df);
        if (filtered.getNumRow() <= 0) {
            System.out.println("ERROR: El DataFrame filtrado debería tener al menos una fila");
        }
        if ((Integer)filtered.getCelda(0, "age").getValue() <= 30) {
            System.out.println("ERROR: El DataFrame filtrado debería contener solo edades > 30");
        }
        if (!(Boolean)filtered.getCelda(0, "active").getValue()) {
            System.out.println("ERROR: El DataFrame filtrado debería contener solo active = true");
        }

        // Probar filtro con OR
        filtered = df.filter()
            .where("name", "=", "Alice")
            .or("name", "=", "Bob")
            .apply(df);
        if (filtered.getNumRow() != 2) {
            System.out.println("ERROR: El DataFrame filtrado debería tener exactamente 2 filas");
        }
    }

    private void testCopying() {
        // Probar copia superficial
        DataFrame shallowCopy = df.shallowCopy();
        if (df.getNumRow() != shallowCopy.getNumRow()) {
            System.out.println("ERROR: La copia superficial debería tener el mismo número de filas");
        }
        if (df.getNumCol() != shallowCopy.getNumCol()) {
            System.out.println("ERROR: La copia superficial debería tener el mismo número de columnas");
        }

        // Probar copia profunda
        DataFrame deepCopy = df.copy();
        if (df.getNumRow() != deepCopy.getNumRow()) {
            System.out.println("ERROR: La copia profunda debería tener el mismo número de filas");
        }
        if (df.getNumCol() != deepCopy.getNumCol()) {
            System.out.println("ERROR: La copia profunda debería tener el mismo número de columnas");
        }
        
        // Modificar original y verificar que la copia profunda no cambia
        df.setCelda(0, "age", 99);
        if (deepCopy.getCelda(0, "age").getValue().equals(99)) {
            System.out.println("ERROR: La copia profunda no debería verse afectada por cambios en el original");
        }
    }

    private void testConcatenation() {
        // Probar concatenación de columnas
        DataFrame other = new DataFrame();
        ArrayList<Celda<?>> scores = new ArrayList<>();
        scores.add(new Celda<>(85.5, TipoDatos.NUMBER));
        scores.add(new Celda<>(90.0, TipoDatos.NUMBER));
        scores.add(new Celda<>(78.5, TipoDatos.NUMBER));
        scores.add(new Celda<>(92.5, TipoDatos.NUMBER));
        scores.add(new Celda<>(88.0, TipoDatos.NUMBER));
        other.addColumn(new Column<>("score", TipoDatos.NUMBER, scores));

        DataFrame combined = df.concatColumns(other);
        if (combined.getNumCol() != df.getNumCol() + other.getNumCol()) {
            System.out.println("ERROR: El DataFrame combinado debería tener la suma de columnas de ambos DataFrames");
        }
        if (combined.getNumRow() != df.getNumRow()) {
            System.out.println("ERROR: El DataFrame combinado debería tener el mismo número de filas que el original");
        }
        if (!combined.getCelda(0, "score").getValue().equals(85.5)) {
            System.out.println("ERROR: El valor en (0, score) debería ser 85.5");
        }
    }

    private void testExceptions() {
        // Probar acceso a columna inválida
        try {
            df.getColumn("invalid_column");
            System.out.println("ERROR: Debería lanzar una excepción para un nombre de columna inválido");
        } catch (IllegalArgumentException e) {
            // Excepción esperada
        }

        // Probar asignación de tipo de dato inválido
        try {
            df.setCelda(0, "age", "not a number");
            System.out.println("ERROR: Debería lanzar una excepción para un tipo de dato inválido");
        } catch (IllegalArgumentException e) {
            // Excepción esperada
        }

        // Probar acceso a fila inválida
        try {
            df.getCelda(99, "age");
            System.out.println("ERROR: Debería lanzar una excepción para un índice de fila inválido");
        } catch (IndexOutOfBoundsException e) {
            // Excepción esperada
        }
    }
    
    // Método principal para ejecutar todas las pruebas
    public static void main(String[] args) {
        DataFrameTest test = new DataFrameTest();
        
        try {
            test.setUp();
            System.out.println("Configuración completada con éxito");
            
            test.testConstructors();
            System.out.println("Pruebas de constructores completadas");
            
            test.testBasicOperations();
            System.out.println("Pruebas de operaciones básicas completadas");
            
            test.testDataModification();
            System.out.println("Pruebas de modificación de datos completadas");
            
            test.testFiltering();
            System.out.println("Pruebas de filtrado completadas");
            
            test.testCopying();
            System.out.println("Pruebas de copiado completadas");
            
            test.testConcatenation();
            System.out.println("Pruebas de concatenación completadas");
            
            test.testExceptions();
            System.out.println("Pruebas de excepciones completadas");
            
            System.out.println("\n¡Todas las pruebas completadas!");
        } catch (Exception e) {
            System.err.println("Error inesperado durante las pruebas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}