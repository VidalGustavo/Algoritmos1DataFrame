package DataFrame;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.Set;

import Archivos.LectorCSV;
import Celda.Celda;
import Column.Column;

import java.util.*;

public class DataFrame {
    //private ArrayList <Rows> rows ;
    private ArrayList<Column<Celda<?>>> columns;
    private int numRow;
    private int numCol;
    private Map<String, Integer> colLabel;
    private Map<String, Integer> rowLabel;


    public DataFrame() {
        columns = new ArrayList<Column<Celda<?>>>();
        numRow = 0;
        numCol = 0;
        colLabel = new HashMap<>();
        rowLabel = new HashMap<>();
    }

    public DataFrame(int numRow, int numCol) {
        columns = new ArrayList<Column<Celda<?>>>();
        this.numRow = numRow;
        this.numCol = numCol;
        colLabel = new HashMap<>();
        rowLabel = new HashMap<>();
    }

    public DataFrame(ArrayList<Column<?>> lista) {
        // Empecemos con las validaciones
        // Validar que los largos de los arrays son iguales
        columns = new ArrayList<Column<Celda<?>>>();
        colLabel = new HashMap<>();
        rowLabel = new HashMap<>();

        // Add columns and update colLabel map
        for (int i = 0; i < lista.size(); i++) {
            Column<?> col = lista.get(i);
            columns.add((Column<Celda<?>>) col);
            colLabel.put(col.getName(), i);
        }

        numCol = columns.size();
        numRow = columns.isEmpty() ? 0 : columns.get(0).getSize();
    }

    // public DataFrame(ArrayList<ArrayList<T>>){

    //         }


    // public DataFrame (String) {

    // }

    public void addColumn(Column column) {
        columns.add(column);
        colLabel.put(column.getName(), columns.size() - 1);
        numCol = columns.size();
    }

    protected int colLabelToIndex(String columnLabel) {
        if (colLabel.containsKey(columnLabel)) {
            return colLabel.get(columnLabel);
        }
        throw new IllegalArgumentException("Column label not found: " + columnLabel);
    }

    protected int rowLabelToIndex(String rowLabelStr) {
        if (rowLabel.containsKey(rowLabelStr)) {
            return rowLabel.get(rowLabelStr);
        }

        if (columns.isEmpty()) {
            throw new IllegalStateException("DataFrame is empty");
        }

        Column<Celda<?>> firstColumn = columns.get(0);
        for (int i = 0; i < firstColumn.getSize(); i++) {
            String value = String.valueOf(firstColumn.getList().get(i).getValue());
            if (value.equals(rowLabelStr)) {
                // Add to map for future lookups
                rowLabel.put(rowLabelStr, i);
                return i;
            }
        }
        throw new IllegalArgumentException("Row label not found: " + rowLabelStr);
    }

    //getter de una columna si se le ingresa el número de columna
    public Column getColumn(int column) {
        //Verifico que el índice sean válidos
        if (column < 0 || column >= numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido: " + column);
        }
        return columns.get(column);
    }

    //getter de una columna si se le ingresa el nombre de la columna
    public Column getColumn(String column) {
        int colIndex = colLabelToIndex(column);
        return columns.get(colIndex);
    }

    //getter de una fila si el índice es numérico:
    public ArrayList<Celda<?>> getRow(int row) {
        //Verifico que el índice sean válidos
        if (row < 0 || row >= numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }

        ArrayList<Celda<?>> fila = new ArrayList<>();
        for (int i = 0; i < numCol; i++) {
            Celda<?> celda = getCelda(row, i);
            fila.add(celda);
        }

        return fila;
    }

    //getter de una fila si el índice es un string:
    public ArrayList<Celda<?>> getRow(String row) {
        int rowIndex = rowLabelToIndex(row);
        return getRow(rowIndex);
    }


    //getter de celdas si se le ingresan ambos parámetros como ints
    public Celda getCelda(int row, int column) {
        //Verifico que los índices sean válidos
        if (column < 0 || column >= numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        if (row < 0 || row >= numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        //Busco la columna y luego la celda
        Column<?> columna = columns.get(column);
        Celda<?> celda = columna.getList().get(row);
        return celda;
    }

    //getter de celdas si se le ingresa la fila como int y la columna como string
    public Celda getCelda(int row, String column) {
        int colIndex = colLabelToIndex(column);
        return getCelda(row, colIndex);
    }

    //getter de celdas si se le ingresa la fila como string y la columna como string
    public Celda getCelda(String row, int column) {
        int rowIndex = rowLabelToIndex(row);
        return getCelda(rowIndex, column);
    }

    //getter de celdas si se le ingresan ambos parámetros como strings
    public Celda getCelda(String row, String column) {
        int rowIndex = rowLabelToIndex(row);
        int colIndex = colLabelToIndex(column);
        return getCelda(rowIndex, colIndex);
    }

    //setter de celda si ambos labels son índices numéricos
    public void setCelda(int row, int column, Object value) {
        //Verifico que los índices sean válidos
        if (column < 0 || column >= numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        if (row < 0 || row >= numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(column).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        getCelda(row, column).setValue(value);
    }

    //setter de celda si el label de la fila es un índice numérico y el de la columna un string
    public void setCelda(int row, String column, Object value) {
        //Verifico que el índice sea válido
        if (row < 0 || row >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        int colIndex = colLabelToIndex(column);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(colIndex).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        getCelda(row, column).setValue(value);
    }

    //setter de celda si el label de la columna es un índice numérico y el de la fila un string
    public void setCelda(String row, int column, Object value) {
        //Verifico que el índice sea válido
        if (column < 0 || column >= this.numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        int rowIndex = rowLabelToIndex(row);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(column).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        getCelda(row, column).setValue(value);
    }

    //setter de celda si ambos labels son strings
    public void setCelda(String row, String column, Object value) {
        int colIndex = colLabelToIndex(column);
        int rowIndex = rowLabelToIndex(row);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(colIndex).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        getCelda(row, column).setValue(value);
    }


    // métodos que cree para que funcione el expo
    public List<Column<Celda<?>>> getColumns() {
        return columns;
    }

    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    //Métodos de copias:
    //Copia superficial:
    public DataFrame shallowCopy() {
        DataFrame copia = new DataFrame();
        copia.columns = columns;
        copia.numRow = numRow;
        copia.numCol = numCol;

        return copia;
    }

    //Copia profunda:
    public DataFrame copy() {
        DataFrame copia = new DataFrame();

        //Recorro el dataframe por columnas:
        for (int i = 0; i < numCol; i++) {
            Column colOriginal = getColumn(i);

            Column colCopia = colOriginal.copy();
            copia.addColumn(colCopia);
        }

        copia.numRow = numRow;
        return copia;
    }

    public void select(ArrayList<?> rowLabels, ArrayList<?> colLabels) {
        Seleccionador.select(this, rowLabels, colLabels);
    }

    public static DataFrame leerCSV(String ruta, boolean encabezado) {
        LectorCSV lector = new LectorCSV();
        DataFrame dataframe = lector.leer(ruta, encabezado);
        return dataframe;
    }

    public static DataFrame leerCSV(String ruta, String separador, boolean encabezado) {
        LectorCSV lector = new LectorCSV();
        DataFrame dataframe = lector.leer(ruta, separador, encabezado);
        return dataframe;
    }

    // metodos que estoy creando porque faltaban
    public void head(int cant) {
        if (cant < 0 || cant > this.numRow) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // 1. Imprimir encabezados (nombres de columnas) si existen
        for (Column<Celda<?>> columna : columns) {
            System.out.print(columna.getName() + "\t"); // Usa tabulación o formato fijo
        }
        System.out.println(); // Salto de línea después de los encabezados

        // 2. Imprimir las filas solicitadas
        for (int i = 0; i < cant; i++) {
            for (Column<Celda<?>> columna : columns) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t"); // Asume que hay un método getValor()
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    public void head() {
        // Sobrecarga. Por defecto mostramos las primeras cinco filas
        // encabezados
        for (Column<Celda<?>> columna : columns) {
            System.out.print(columna.getName() + "\t"); // Usa tabulación o formato fijo
        }
        System.out.println(); // Salto de línea después de los encabezados

        // 2. Imprimir las filas solicitadas
        for (int i = 0; i < 5; i++) {
            for (Column<Celda<?>> columna : columns) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t");
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    public void tail(int cant) {
        if (cant < 0 || cant > this.numRow) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // Calcular el índice de inicio (últimas 'cant' filas)
        int startRow = this.numRow - cant;

        // Imprimo encabezados (igual que en head())
        for (Column<Celda<?>> columna : columns) {
            System.out.print(columna.getName() + "\t");
        }
        System.out.println();

        // itero desde startRow hasta el final
        for (int i = startRow; i < this.numRow; i++) {
            for (Column<Celda<?>> columna : columns) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t");
            }
            System.out.println();
        }
    }

    public void tail() {

        // Calculao del índice de inicio (últimas 'cant' filas)
        int startRow = (this.numRow - 5 >= 0) ? this.numRow - 5 : 0; // Por defecto, mostrar las últimas 5 filas

        // encabezados
        for (Column<Celda<?>> columna : columns) {
            System.out.print(columna.getName() + "\t");
        }
        System.out.println();

        // filas
        for (int i = startRow; i < this.numRow; i++) {
            for (Column<Celda<?>> columna : columns) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t");
            }
            System.out.println(); // salto de línea
        }
    }

    public void shape() {
        System.out.println("[" + numRow + " x " + numCol + "]");

    }

    public void showRow(int rowIndex) {
        // Verifica si el índice de fila es válido
        if (rowIndex < 0 || rowIndex >= numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido: " + rowIndex);
        }
        // Imprime los valores de la fila
        for (Column<Celda<?>> columna : columns) {
            Celda<?> celda = columna.getList().get(rowIndex);
            System.out.print(celda.getValue() + "\t");
        }
        System.out.println(); // Salto de línea al final
    }

    public void showColumn(Column<Celda<?>> name) {
        // Verifica si la columna existe
        if (!columns.contains(name)) {
            throw new IllegalArgumentException("Columna no encontrada: " + name.getName());
        }
        // Imprime el nombre de la columna
        System.out.println("Columna: " + name.getName());
        // Imprime los valores de la columna
        for (Celda<?> celda : name.getList()) {
            System.out.print(celda.getValue() + "\t");
        }
        System.out.println(); // Salto de línea al final
    }

    public void showColumns() {
        // Imprime los nombres de las columnas
        for (Column<Celda<?>> columna : columns) {
            System.out.print(columna.getName() + "\t");
        }
        System.out.println(); // Salto de línea al final
    }

    public void numCol(){
        // Imprime el número de columnas
        System.out.println("Número de columnas: " + numCol);
    }

    public void numRow(){
        // Imprime el número de filas
        System.out.println("Número de filas: " + numRow);
    }

    public void setNumRow(int numRow){
        this.numRow = numRow;
    }
    
    public void deleteRow(int rowIndex) {
        // Verifico que el índice sea válido
        if (rowIndex < 0 || rowIndex >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido: " + rowIndex);
        }
        
        // Elimino la celda correspondiente en cada columna
        for (Column<Celda<?>> columna : columns) {
            columna.getList().remove(rowIndex);
        }
        
        // Actualizo el contador de filas
        this.numRow--;
        
        // Actualizo los índices de las filas en el mapa rowLabel
        Map<String, Integer> newRowLabel = new HashMap<>();
        for (Map.Entry<String, Integer> entry : rowLabel.entrySet()) {
            int idx = entry.getValue();
            if (idx > rowIndex) {
                newRowLabel.put(entry.getKey(), idx - 1);
            } else if (idx < rowIndex) {
                newRowLabel.put(entry.getKey(), idx);
            }
            // Si idx == rowIndex, no lo incluimos en el nuevo mapa
        }
        rowLabel = newRowLabel;
    }
    
    public void deleteRow(String rowLabel) {
        // Convierto la etiqueta a índice
        int rowIndex = rowLabelToIndex(rowLabel);
        
        // Utilizo el método que elimina por índice
        deleteRow(rowIndex);
    }
    
    public void deleteColumn(int colIndex) {
        // Verifico que el índice sea válido
        if (colIndex < 0 || colIndex >= this.numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido: " + colIndex);
        }
        
        // Elimino la columna de la lista de columnas
        this.columns.remove(colIndex);
        
        // Actualizo el contador de columnas
        this.numCol--;
        
        // Actualizo los índices de las columnas en el mapa colLabel
        Map<String, Integer> newColLabel = new HashMap<>();
        for (Map.Entry<String, Integer> entry : colLabel.entrySet()) {
            int idx = entry.getValue();
            if (idx > colIndex) {
                newColLabel.put(entry.getKey(), idx - 1);
            } else if (idx < colIndex) {
                newColLabel.put(entry.getKey(), idx);
            }
            // Si idx == colIndex, no lo incluimos en el nuevo mapa
        }
        colLabel = newColLabel;
    }
    
    public void deleteColumn(String colLabel) {
        // Convierto la etiqueta a índice
        int colIndex = colLabelToIndex(colLabel);
        
        // Utilizo el método que elimina por índice
        deleteColumn(colIndex);
    }

    public Set<String> getRowLabels() {
        return rowLabel.keySet();
    }

    public Set<String> getColumnLabels() {
        return colLabel.keySet();
    }

    public void renameCols(String[] newNames) {
        if (newNames.length != numCol) {
            throw new IllegalArgumentException("Cantidad de nombres deb ser igual a la cantidad de columnas");
        }

        Map<String, Integer> newColLabel = new HashMap<>();

        for (int i = 0; i < numCol; i++) {
            String oldName = columns.get(i).getName();
            String newName = newNames[i];

            columns.get(i).setName(newName);

            newColLabel.put(newName, i);
        }

        colLabel = newColLabel;
    }

    public void renameRows(String[] newNames) {
        if (newNames.length != numRow) {
            throw new IllegalArgumentException("Cantidad de nombres debe ser igual a la cantidad de filas");
        }

        Map<String, Integer> newRowLabel = new HashMap<>();

        for (int i = 0; i < numRow; i++) {
            String newName = newNames[i];
            newRowLabel.put(newName, i);
        }

        rowLabel = newRowLabel;
    }
 

    /////////////////////////////////////////////
    //              Ordenamiento              //
    ///////////////////////////////////////////
    public DataFrame orderBy(int columnIndex, boolean ascending) {
        // La idea es crear una lista de indices (enteros de 0 a Column.size-1) e ir moviendola con el mismo proceso que el de ordenamiento.
        // Asi luego de tener la columna deseada ordenada, se puede aplicar el orden a las otras columnas.
        if (columnIndex < 0 || columnIndex >= numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido: " + columnIndex);
        }
        // Por simpleza trabajo con una copia de la columna referida, luego la ordeno junto al resto de las columnas.
        Column referedColumn = getColumn(columnIndex).copy();
        ArrayList<Integer> indices = new ArrayList<>();
        indices.addAll(IntStream.range(0, referedColumn.getSize()).boxed().toList()); // llena array con int de 0 a Column.size-1
        // Ordenar en si.
        //TODO: optimizar el ordenamiento.
        int length = referedColumn.getSize();
        for (int i = 0; i < length - 1; i++) {
            int minIndex = i;
            Celda minCell = (Celda) referedColumn.getList().get(i);
            for (int j = i + 1; j < length; j++) {
                Celda currentCell = (Celda) referedColumn.getList().get(j);
                
                if (currentCell.compareTo(minCell) < 0) {
                    minIndex = j;
                    minCell = currentCell;
                }
            }
            Celda tempCell = (Celda) referedColumn.getList().get(minIndex);
            referedColumn.getList().set(minIndex, referedColumn.getList().get(i));
            referedColumn.getList().set(i, tempCell);
            int tempIndex = indices.get(minIndex);
            indices.set(minIndex, indices.get(i));
            indices.set(i, tempIndex);
        }

        if (!ascending) {
            // Invertir el orden.
            for (int i = 0; i < (int) indices.size() / 2; i++) {
                int temp = indices.get(i);
                int mirroredPosition = indices.size() - 1 - i;
                indices.set(i, indices.get(mirroredPosition));
                indices.set(mirroredPosition, temp);
            }
        }

        // Crear un nuevo DataFrame con las columnas ordenadas.
        DataFrame orderedDataFrame = new DataFrame();
        orderedDataFrame.numRow = this.numRow;
        orderedDataFrame.numCol = this.numCol;
        for (int i = 0; i < numCol; i++) {
            Column<Celda<?>> tempColumna = this.getColumn(i).copy();

            ArrayList<Celda<?>> orderedList = new ArrayList<>();
            for (int index : indices) {
                // Agregar las celdas en el orden especificado por indices
                orderedList.add(tempColumna.getList().get(index));
            }
            Column orderedColumn = new Column(tempColumna.getName(), tempColumna.getTipoCelda(), orderedList);
            orderedDataFrame.addColumn(orderedColumn);
        }
        
        return orderedDataFrame;    
    }
    //TODO: Descomentar cuando se implemente el método colLabelToIndex
    // public DataFrame orderBy(String columnName, boolean ascending) {
    //     int colIndex = colLabelToIndex(columnName);
    //     return orderBy(colIndex, ascending);
    // }

}
