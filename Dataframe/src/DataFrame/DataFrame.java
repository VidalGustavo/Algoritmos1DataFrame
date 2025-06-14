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
import Filter.FilterPipeline;

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

    public  void tail() {
        Seleccionador.tail(this);
    }

    public void tail(int cant) {
        Seleccionador.tail(this, cant); 
    }

    public void head() {
        Seleccionador.head(this);
    }

    public static void head(DataFrame dataframe, int cant) {
        Seleccionador.head(dataframe, cant);
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
    

    

    

    

    public void shape() {
        System.out.println("[" + numRow + " x " + numCol + "]");
    }

    /**
     * Creates a new filter pipeline for this DataFrame
     * @return A FilterPipeline object that can be used to build and apply filters
     */
    public FilterPipeline filter() {
        return new FilterPipeline();
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

 
    public DataFrame concatColumns(DataFrame other) {
        if (this.numRow != other.numRow) {
            throw new IllegalArgumentException("DataFrames must have the same number of rows to concatenate columns");
        }

        DataFrame result = this.copy();
        
        // Agrego todas las columnas del otro DataFrame
        for (Column<Celda<?>> col : other.getColumns()) {
            result.addColumn(col.copy());
        }

        return result;
    }

   
    public DataFrame concatArray(Object[] array, String columnName) {
        if (array.length != this.getNumRow()) {
            throw new IllegalArgumentException("Array length must match DataFrame row count");
        }

        // Creo un DataFrame con una sola columna a partir del array
        DataFrame singleColumnDF = new DataFrame();
        
        ArrayList<Celda<?>> cells = new ArrayList<>();
        for (Object value : array) {
            TipoDatos tipoDato;
            if (value instanceof String) {
                tipoDato = TipoDatos.STRING;
            } else if (value instanceof Number) {
                tipoDato = TipoDatos.NUMBER;
            } else if (value instanceof Boolean) {
                tipoDato = TipoDatos.BOOLEAN;
            } else {
                throw new IllegalArgumentException("El valor " + value.toString() +" tiene Tipo de dato no soportado: " + value.getClass().getSimpleName());
            }
            Celda<?> cell = new Celda<>(value);
            cells.add(cell);
        }
        
        Column<Celda<?>> newColumn = new Column<>(columnName, cells.get(0).getClass(), cells);
        singleColumnDF.addColumn(newColumn);
        
        // Uso el método concatColumns para combinar los DataFrames
        return this.concatColumns(singleColumnDF);
    }

    public DataFrame concatArrays(Object[][] arrays, String[] columnNames) {
        if (columnNames.length != arrays.length) {
            throw new IllegalArgumentException("Number of column names must match number of arrays");
        }

        DataFrame result = this.copy();
        
        // Proceso cada array usando concatArray
        for (int i = 0; i < arrays.length; i++) {
            result = result.concatArray(arrays[i], columnNames[i]);
        }

        return result;
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

    public void display() {
        // Verifico si el DataFrame está vacío usando los getters existentes
        if (getNumCol() == 0 || getNumRow() == 0) {
            System.out.println("DataFrame Vacío");
            return;
        }

        // Calculo el ancho máximo necesario para cada columna
        int[] columnWidths = calculateColumnWidths();

        // Imprimo el separador del encabezado
        printSeparator(columnWidths);

        // Imprimo los nombres de las columnas con formato
        printFormattedColumnNames(columnWidths);
        System.out.println("|");

        // Imprimo el separador después de los encabezados
        printSeparator(columnWidths);

        // Imprimo cada fila con formato
        printFormattedRows(columnWidths);

        // Imprimo el separador inferior
        printSeparator(columnWidths);

        // Imprimo las dimensiones del DataFrame usando el método shape()
        System.out.print("\n");
        shape();
    }
    
    // Método auxiliar para calcular el ancho máximo necesario para cada columna
    private int[] calculateColumnWidths() {
        int[] columnWidths = new int[numCol];
        for (int i = 0; i < numCol; i++) {
            Column<Celda<?>> column = getColumn(i);
            // Inicializo con la longitud del nombre de la columna
            columnWidths[i] = column.getName().length();
            
            // Verifico la longitud de cada valor
            for (int j = 0; j < numRow; j++) {
                String value = String.valueOf(getCelda(j, i).getValue());
                columnWidths[i] = Math.max(columnWidths[i], value.length());
            }
        }
        return columnWidths;
    }
    
    // Método auxiliar para imprimir los nombres de las columnas con formato adecuado
    private void printFormattedColumnNames(int[] columnWidths) {
        for (int i = 0; i < numCol; i++) {
            String name = getColumn(i).getName();
            System.out.print("| " + String.format("%-" + columnWidths[i] + "s", name) + " ");
        }
    }
    
    // Método auxiliar para imprimir todas las filas con formato adecuado
    private void printFormattedRows(int[] columnWidths) {
        for (int i = 0; i < numRow; i++) {
            ArrayList<Celda<?>> row = getRow(i);
            for (int j = 0; j < numCol; j++) {
                String value = String.valueOf(row.get(j).getValue());
                System.out.print("| " + String.format("%-" + columnWidths[j] + "s", value) + " ");
            }
            System.out.println("|");
        }
    }


    // Método auxiliar para imprimir líneas separadoras en la tabla
    private void printSeparator(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+-" + "-".repeat(width) + "-");
        }
        System.out.println("+");
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


//###############################################

    public void addColumnFromList(List<?> list, String name) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        // Verifica que el tamaño de la lista coincida con el número de filas del DataFrame
        if (list.size() != numRow) {
            throw new IllegalArgumentException("El tamaño de la lista debe coincidir con el número de filas del DataFrame");
        }
        // Verifica que el nombre de la columna no exista ya
        if (colLabel.containsKey(name)) {
            throw new IllegalArgumentException("Ya existe una columna con el nombre: " + name);
        }
        // Verifica que todos los elementos de la lista sean del mismo tipo
        if (list.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista no puede contener valores nulos");
        }
        // Determina el tipo de dato de la primera celda
        Class<?> firstElementClass = list.get(0).getClass();
        // Verifica que todos los elementos de la lista sean del mismo tipo
        if (!list.stream().allMatch(element -> element.getClass().equals(firstElementClass))) {
            throw new IllegalArgumentException("Todos los elementos de la lista deben ser del mismo tipo");
        
        
        }

        
        // Crea una lista de celdas a partir de la lista proporcionada
        ArrayList<Celda<?>> celdas = new ArrayList<>();
        for (Object element : list) {
            TipoDatos tipoDato;
            if (element instanceof String) {
                tipoDato = TipoDatos.STRING;
            } else if (element instanceof Number) {
                        tipoDato = TipoDatos.NUMBER;
            } else if (element instanceof Boolean) {
                        tipoDato = TipoDatos.BOOLEAN;
            } else {
                throw new IllegalArgumentException("El valor " + element.toString() +" tiene Tipo de dato no soportado: " + element.getClass().getSimpleName());
            }
            
            
            Celda<?> celda = new Celda<>(element, tipoDato);
            celdas.add(celda);
        
        }
        
    }
    





    public void renameCol (String oldName, String newName) {
        if (!colLabel.containsKey(oldName)) {
            throw new IllegalArgumentException("Columna no encontrada: " + oldName);
        }
        
        int index = colLabel.get(oldName);
        columns.get(index).setName(newName);
        if (colLabel.containsKey(newName)) {
            throw new IllegalArgumentException("El nuevo nombre de columna ya existe: " + newName);
        }
        colLabel.remove(oldName);
        colLabel.put(newName, index);
    }


    public void renameCol (int index, String newName) {
        if (index < 0 || index >= numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido: " + index);
        }
        
        String oldName = columns.get(index).getName();
        columns.get(index).setName(newName);
        if (colLabel.containsKey(newName)) {
            throw new IllegalArgumentException("El nuevo nombre de columna ya existe: " + newName);
        
        }
        colLabel.remove(oldName);
        colLabel.put(newName, index);
    }






}
