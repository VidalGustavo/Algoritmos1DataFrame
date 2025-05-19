package DataFrame;

import java.util.ArrayList;

import Column.Column;
import celda.Celda;
import column.Column;

public class DataFrame {
    //private ArrayList <Rows> rows ;
    private ArrayList<Column> columns;
    private int numRow;
    private int numCol ;
    


    public DataFrame(ArrayList lista)  {
        // Si inicializa vacio asume que es una columna     
        this.numCol = numCol;
        this.columns = columns;
    }
    public DataFrame(ArrayList <ArrayList <Object> ){

            }
    

    public DataFrame (String) {

    }

    //getter de celdas si se le ingresan ambos parámetros como ints
    public Celda getCelda(int row, int column){
        //Verifico que los índices sean válidos
        if(column < 0 || column >= this.numCol){
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        if(row < 0 || row >= this.numRow){
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        //Busco la columna y luego la celda
        Column columna = columns.get(column);
        Celda celda = columna.getList().get(row);
        return celda;
    }

    //getter de celdas si se le ingresa la fila como int y la columna como string
    public Celda getCelda(int row, String column){
        //Verifico que el índice sea válido
        if(row < 0 || row >= this.numRow){
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        int colIndex = colLabelToIndex(column);
        //Busco la columna y luego la celda
        Column columna = columns.get(colIndex);
        Celda celda = columna.getList().get(row);
        return celda;
    }

    //getter de celdas si se le ingresa la fila como string y la columna como string
    public Celda getCelda(String row, int column){
        //Verifico que el índice sea válido
        if(column < 0 || column >= this.numCol){
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        int rowIndex = rowLabelToIndex(row);
        //Busco la columna y luego la celda
        Column columna = columns.get(column);
        Celda celda = columna.getList().get(rowIndex);
        return celda;
    }

    //getter de celdas si se le ingresan ambos parámetros como strings
    public Celda getCelda(String row, String column){
        int colIndex = colLabelToIndex(column);
        int rowIndex = rowLabelToIndex(row);
        //Busco la columna y luego la celda
        Column columna = columns.get(colIndex);
        Celda celda = columna.getList().get(rowIndex);
        return celda;
    }

    //setter de celda si ambos labels son índices numéricos
    public void setCelda(int row, int column, Object value){
        //Verifico que los índices sean válidos
        if(column < 0 || column >= this.numCol){
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        if(row < 0 || row >= this.numRow) {
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(column).validateType(value)){
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
        }
        //Busco la celda y le cambio el valor
        this.columns.get(column).getList().get(row).setValor(value);
    }

    //setter de celda si el label de la fila es un índice numérico y el de la columna un string
    public void setCelda(int row, String column, Object value){
        //Verifico que el índice sea válido
        if(row < 0 || row >= this.numRow){
            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
        }
        int colIndex = colLabelToIndex(column);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(colIndex).validateType(value)){
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
        }
        //Busco la celda y le cambio el valor
        this.columns.get(colIndex).getList().get(row).setValor(value);
    }

    //setter de celda si el label de la columna es un índice numérico y el de la fila un string
    public void setCelda(String row, int column, Object value){
        //Verifico que el índice sea válido
        if(column < 0 || column >= this.numCol){
            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
        }
        int rowIndex = rowLabelToIndex(row);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(column).validateType(value)){
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
        }
        //Busco la celda y le cambio el valor
        this.columns.get(column).getList().get(rowIndex).setValor(value);
    }

    //setter de celda si ambos labels son strings
    public void setCelda(String row, int column, Object value){
        int colIndex = colLabelToIndex(column);
        int rowIndex = rowLabelToIndex(row);
        //Verifico que el tipo del objeto sea válido para esta columna
        if (!this.columns.get(colIndex).validateType(value)){
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
        }
        //Busco la celda y le cambio el valor
        this.columns.get(colIndex).getList().get(rowIndex).setValor(value);
    }

}