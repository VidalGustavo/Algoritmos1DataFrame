package DataFrame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Set;

import Column.Column;
import Celda.Celda;

public class DataFrame<T> {
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

    public DataFrame(ArrayList<Column<T>> lista) {
        // Empecemos con las validaciones
        // Validar que los largos de los arrays son iguales
        columns = new ArrayList<Column<Celda<?>>>();
        colLabel = new HashMap<>();
        rowLabel = new HashMap<>();
        
        // Add columns and update colLabel map
        for (int i = 0; i < lista.size(); i++) {
            Column<T> col = lista.get(i);
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

    // Helper methods to convert labels to indices
    private int colLabelToIndex(String columnLabel) {
        // Use the colLabel map to find the index
        if (colLabel.containsKey(columnLabel)) {
            return colLabel.get(columnLabel);
        }
        throw new IllegalArgumentException("Column label not found: " + columnLabel);
    }

    private int rowLabelToIndex(String rowLabelStr) {
        // Use the rowLabel map to find the index
        if (rowLabel.containsKey(rowLabelStr)) {
            return rowLabel.get(rowLabelStr);
        }
        
        // Fallback to searching in the first column if not in map
        if (columns.isEmpty()) {
            throw new IllegalStateException("DataFrame is empty");
        }
        
        Column firstColumn = columns.get(0);
        for (int i = 0; i < firstColumn.getSize(); i++) {
            String value = firstColumn.getList().get(i).getValue().toString();
            if (value.equals(rowLabelStr)) {
                // Add to map for future lookups
                rowLabel.put(rowLabelStr, i);
                return i;
            }
        }
        throw new IllegalArgumentException("Row label not found: " + rowLabelStr);
    }

    //getter de celdas si se le ingresan ambos parámetros como ints
    public Celda getCelda(int row, int column) {
        //Verifico que los índices sean válidos
        if(column < 0 || column >= this.numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        if(row < 0 || row >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        //Busco la columna y luego la celda
        Column columna = columns.get(column);
        Celda celda = (Celda) columna.getList().get(row);
        return celda;
    }

    //getter de celdas si se le ingresa la fila como int y la columna como string
    public Celda getCelda(int row, String column) {
        //Verifico que el índice sea válido
        if(row < 0 || row >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        int colIndex = colLabelToIndex(column);
        //Busco la columna y luego la celda
        Column columna = columns.get(colIndex);
        Celda celda = (Celda) columna.getList().get(row);
        return celda;
    }

    //getter de celdas si se le ingresa la fila como string y la columna como string
    public Celda getCelda(String row, int column) {
        //Verifico que el índice sea válido
        if(column < 0 || column >= this.numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        int rowIndex = rowLabelToIndex(row);
        //Busco la columna y luego la celda
        Column columna = columns.get(column);
        Celda celda = (Celda) columna.getList().get(rowIndex);
        return celda;
    }

    //getter de celdas si se le ingresan ambos parámetros como strings
    public Celda getCelda(String row, String column) {
        int colIndex = colLabelToIndex(column);
        int rowIndex = rowLabelToIndex(row);
        //Busco la columna y luego la celda
        Column columna = columns.get(colIndex);
        Celda celda = (Celda) columna.getList().get(rowIndex);
        return celda;
    }

    //setter de celda si ambos labels son índices numéricos
    public void setCelda(int row, int column, T value) {
        //Verifico que los índices sean válidos
        if(column < 0 || column >= this.numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        if(row < 0 || row >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(column).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        columns.get(column).getList().get(row).setValue(value);
    }

    //setter de celda si el label de la fila es un índice numérico y el de la columna un string
    public void setCelda(int row, String column, T value) {
        //Verifico que el índice sea válido
        if(row < 0 || row >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        int colIndex = colLabelToIndex(column);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(colIndex).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        this.columns.get(colIndex).getList().get(row).setValue(value);
    }

    //setter de celda si el label de la columna es un índice numérico y el de la fila un string
    public void setCelda(String row, int column, T value) {
        //Verifico que el índice sea válido
        if(column < 0 || column >= this.numCol) {
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        int rowIndex = rowLabelToIndex(row);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(column).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        this.columns.get(column).getList().get(rowIndex).setValue(value);
    }

    //setter de celda si ambos labels son strings
    public void setCelda(String row, String column, T value) {
        int colIndex = colLabelToIndex(column);
        int rowIndex = rowLabelToIndex(row);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(colIndex).validateType(value)) {
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        this.columns.get(colIndex).getList().get(rowIndex).setValue(value);
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
}