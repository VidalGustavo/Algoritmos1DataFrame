package DataFrame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import Column.Column;
import Celda.Celda;
import Archivos.LectorCSV;

public class DataFrame {
    //private ArrayList <Rows> rows ;
    private ArrayList<Column<Celda<?>>> columns;
    private int numRow;
    private int numCol ;
    

    public DataFrame() {
        columns = new ArrayList<Column<Celda<?>>>();
        numRow = 0;
        numCol = 0;
    }

    public DataFrame(int numRow, int numCol) {
        columns = new ArrayList<Column<Celda<?>>>();
        this.numRow = numRow;
        this.numCol = numCol;
    }

    public DataFrame(ArrayList<Column<?>> lista)  {
        // Empecemos con las validaciones
        // Validar que los largos de los arrays son iguales

        
        numCol = columns.size();
        numRow = columns.get(0).getSize();

    }    

    // public DataFrame(ArrayList<ArrayList<T>>){

    //         }
    

    // public DataFrame (String) {

    // }

    public void addColumn(Column column){
        columns.add(column);
        numCol++;
    }

    //getter de una columna si se le ingresa el número de columna
    public Column getColumn(int column){
        if(column < 0 || column >= this.numCol){
            throw new IndexOutOfBoundsException("Índice de columna inválido: " + column);
        }
        return columns.get(column);
    }

//    //getter de una columna si se le ingresa el nombre de la columna
//    public Column getColumn(String column){
//        int colIndex = colLabelToIndex(column);
//        return columns.get(colIndex);
//    }

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
        Celda celda = (Celda) columna.getList().get(row);
        return celda;
    }

//    //getter de celdas si se le ingresa la fila como int y la columna como string
//    public Celda getCelda(int row, String column){
//        //Verifico que el índice sea válido
//        if(row < 0 || row >= this.numRow){
//            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
//        }
//        int colIndex = colLabelToIndex(column);
//        //Busco la columna y luego la celda
//        Column columna = columns.get(colIndex);
//        Celda celda = (Celda) columna.getList().get(row);
//        return celda;
//    }
//
//    //getter de celdas si se le ingresa la fila como string y la columna como string
//    public Celda getCelda(String row, int column){
//        //Verifico que el índice sea válido
//        if(column < 0 || column >= this.numCol){
//            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
//        }
//        int rowIndex = rowLabelToIndex(row);
//        //Busco la columna y luego la celda
//        Column columna = columns.get(column);
//        Celda celda = (Celda) columna.getList().get(rowIndex);
//        return celda;
//    }
//
//    //getter de celdas si se le ingresan ambos parámetros como strings
//    public Celda getCelda(String row, String column){
//        int colIndex = colLabelToIndex(column);
//        int rowIndex = rowLabelToIndex(row);
//        //Busco la columna y luego la celda
//        Column columna = columns.get(colIndex);
//        Celda celda = (Celda) columna.getList().get(rowIndex);
//        return celda;
//    }

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
            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.");
        }
        //Busco la celda y le cambio el valor
        getCelda(row, column).setValue(value);
    }

//    //setter de celda si el label de la fila es un índice numérico y el de la columna un string
//    public void setCelda(int row, String column, Object value){
//        //Verifico que el índice sea válido
//        if(row < 0 || row >= this.numRow){
//            throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
//        }
//        int colIndex = colLabelToIndex(column);
//        //Verifico que el tipo del objeto sea válido para esta columna
//        if (!this.columns.get(colIndex).validateType(value)){
//            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
//        }
//        //Busco la celda y le cambio el valor
//        getCelda(row, column).setValue(value);
//    }
//
//    //setter de celda si el label de la columna es un índice numérico y el de la fila un string
//    public void setCelda(String row, int column, Object value){
//        //Verifico que el índice sea válido
//        if(column < 0 || column >= this.numCol){
//            throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
//        }
//        int rowIndex = rowLabelToIndex(row);
//        //Verifico que el tipo del objeto sea válido para esta columna
//        if (!this.columns.get(column).validateType(value)){
//            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
//        }
//        //Busco la celda y le cambio el valor
//        getCelda(row, column).setValue(value);
//    }
//
//    //setter de celda si ambos labels son strings
//    public void setCelda(String row, int column, Object value){
//        int colIndex = colLabelToIndex(column);
//        int rowIndex = rowLabelToIndex(row);
//        //Verifico que el tipo del objeto sea válido para esta columna
//        if (!this.columns.get(colIndex).validateType(value)){
//            throw new IllegalArgumentException("Tipo de valor inválido para esta columna.")
//        }
//        //Busco la celda y le cambio el valor
//        getCelda(row, column).setValue(value);
//    }


    // métodos que cree para que funcione el expo 
    public List<Column<Celda<?>>> getColumns() {
        return columns;
    }

    public int getNumRow() {
        return numRow;
    }

    //Métodos de copias:
    //Copia superficial:
    public DataFrame shallowCopy(){
        DataFrame copia = new DataFrame();
        copia.columns = columns;
        copia.numRow = numRow;
        copia.numCol = numCol;

        return copia;
    }

    //Copia profunda:
    public DataFrame copy(){
        DataFrame copia = new DataFrame();

        //Recorro el dataframe por columnas:
        for(int i = 0; i < numCol; i++){
            Column colOriginal = getColumn(i);

            Column colCopia = colOriginal.copy();
            copia.addColumn(colCopia);
        }

        copia.numRow = numRow;
        return copia;
    }

    public static DataFrame leerCSV(String ruta){
        LectorCSV lector = new LectorCSV();
        DataFrame dataframe = lector.leer(ruta);
        return dataframe;
    }

    public static DataFrame leerCSV(String ruta, String separador){
        LectorCSV lector = new LectorCSV();
        DataFrame dataframe = lector.leer(ruta, separador);
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
    

        if (cant < 0 || cant > this.numRow) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // Encabezados
        for (Column<Celda<?>> columna : columns) {
            System.out.print(columna.getName() + "\t"); // Usa tabulación o formato fijo
        }
        System.out.println(); // Salto de línea 

        // Filas
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
        int startRow = (this.numRow -5 >= 0) ? this.numRow -5 : 0; // Por defecto, mostrar las últimas 5 filas

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
        
    public void shape(){
        System.out.println("[" + numRow + " x " + numCol +"]");
        
    }
    public void getCol(Column <Celda<?>> name) {
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
    
    public void getRow (int rowIndex) {
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

    public void columns() {
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

