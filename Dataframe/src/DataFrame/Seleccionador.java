package DataFrame;

import Column.Column;

import javax.xml.crypto.Data;

import Celda.Celda;

import java.util.ArrayList;
import java.util.List;

public class Seleccionador {
    private static void selectPorLabels(DataFrame dataFrame, List<Integer> rowLabels, List<Integer> colLabels){
        ArrayList<Column<?>> columnas = new ArrayList<Column<?>>();
        String labels = "| ";

        for(int column : colLabels){
            //Verifico que el índice exista:
            if(column < 0 || column >= dataFrame.getNumCol()){
                throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
            }

            Column<?> colAux = dataFrame.getColumn(column);
            columnas.add(colAux);
            labels += colAux.getName() + " | ";
        }

        System.out.println(labels);

        for(int row : rowLabels){
            //Verifico que el índice exista:
            if(row < 0 || row >= dataFrame.getNumRow()) {
                throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
            }

            String mostrar = "| ";
            for(Column<?> columna : columnas){
                mostrar += columna.getList().get(row).getValue() + " | ";
            }
            System.out.println(mostrar);
        }
    }

    private static void selectColumnsPorLabels(DataFrame dataFrame, List<Integer> colLabels){
        ArrayList<Column<?>> columnas = new ArrayList<Column<?>>();
        String labels = "| ";

        for(int column : colLabels){
            //Verifico que el índice exista:
            if(column < 0 || column >= dataFrame.getNumCol()){
                throw new IndexOutOfBoundsException("Índice de columna inválido:" + column);
            }

            Column<?> colAux = dataFrame.getColumn(column);
            columnas.add(colAux);
            labels += colAux.getName() + " | ";
        }

        System.out.println(labels);

        int cantRows = dataFrame.getNumRow();
        for(int i = 1; i < cantRows; i++){//empieza en i=1 ya que i=0 es el label
            String mostrar = "| ";
            for(Column<?> columna : columnas){
                mostrar += columna.getList().get(i).getValue() + " | ";
            }
            System.out.println(mostrar);
        }
    }

    private static void selectColumns(DataFrame dataFrame, ArrayList<?> colLabels){
        if(colLabels.get(0) instanceof Integer){
            ArrayList<Integer> cols = (ArrayList<Integer>) colLabels;

            selectColumnsPorLabels(dataFrame, cols);
        }else if(colLabels.get(0) instanceof String){
            ArrayList<String> cols = (ArrayList<String>) colLabels;

            List<Integer> colIndexs = new ArrayList<>();
            for(String label : cols){
                int colIndex = dataFrame.colLabelToIndex(label);
                colIndexs.add(colIndex);
            }

            selectColumnsPorLabels(dataFrame, colIndexs);
        }
    }

    private static void selectRowsPorLabels(DataFrame dataFrame, List<Integer> rowLabels){
        String labels = "| ";

        int cantCol = dataFrame.getNumCol();
        for(int i = 0; i < cantCol; i++){
            Column<?> colAux = dataFrame.getColumn(i);
            labels += colAux.getName() + " | ";
        }

        System.out.println(labels);

        for(int row : rowLabels){
            //Verifico que el índice exista:
            if(row < 0 || row >= dataFrame.getNumRow()) {
                throw new IndexOutOfBoundsException("Índice de fila inválido:" + row);
            }

            String mostrar = "| ";
            for(Column<?> columna : dataFrame.getColumns()){
                mostrar += columna.getList().get(row).getValue() + " | ";
            }
            System.out.println(mostrar);
        }
    }

    private static void selectRows(DataFrame dataFrame, ArrayList<?> rowLabels){
        if(rowLabels.get(0) instanceof Integer){
            ArrayList<Integer> rows = (ArrayList<Integer>) rowLabels;

            selectRowsPorLabels(dataFrame, rows);

        }else if(rowLabels.get(0) instanceof String){
            ArrayList<String> rows = (ArrayList<String>) rowLabels;

            List<Integer> rowIndexs = new ArrayList<>();
            for(String label : rows){
                int rowIndex = dataFrame.rowLabelToIndex(label);
                rowIndexs.add(rowIndex);
            }

            selectRowsPorLabels(dataFrame, rowIndexs);
        }
    }

    public static void select(DataFrame dataFrame, ArrayList<?> rowLabels, ArrayList<?> colLabels){
        if(rowLabels.isEmpty() && colLabels.isEmpty()){
            throw new RuntimeException("No hay filas ni columnas seleccionadas.");
        }


        if(rowLabels.isEmpty()){ //Muestra todas las filas de algunas columnas
            selectColumns(dataFrame, colLabels);
            return;
        }else if(colLabels.isEmpty()){ //Muestra todas las columnas de algunas filas
            selectRows(dataFrame, rowLabels);
            return;
        }

        if(rowLabels.get(0) instanceof Integer && colLabels.get(0) instanceof Integer){
            ArrayList<Integer> rows = (ArrayList<Integer>) rowLabels;
            ArrayList<Integer> cols = (ArrayList<Integer>) colLabels;

            selectPorLabels(dataFrame, rows, cols);

        }else if(rowLabels.get(0) instanceof Integer && colLabels.get(0) instanceof String){
            ArrayList<Integer> rows = (ArrayList<Integer>) rowLabels;
            ArrayList<String> cols = (ArrayList<String>) colLabels;

            List<Integer> colIndexs = new ArrayList<>();
            for(String label : cols){
                int colIndex = dataFrame.colLabelToIndex(label);
                colIndexs.add(colIndex);
            }

            selectPorLabels(dataFrame, rows, colIndexs);

        }else if(rowLabels.get(0) instanceof String && colLabels.get(0) instanceof Integer){
            ArrayList<String> rows = (ArrayList<String>) rowLabels;
            ArrayList<Integer> cols = (ArrayList<Integer>) colLabels;

            List<Integer> rowIndexs = new ArrayList<>();
            for(String label : rows){
                int rowIndex = dataFrame.rowLabelToIndex(label);
                rowIndexs.add(rowIndex);
            }

            selectPorLabels(dataFrame, rowIndexs, cols);

        }else if(rowLabels.get(0) instanceof String && colLabels.get(0) instanceof String){
            ArrayList<String> rows = (ArrayList<String>) rowLabels;
            ArrayList<String> cols = (ArrayList<String>) colLabels;

            List<Integer> colIndexs = new ArrayList<>();
            for(String label : cols){
                int colIndex = dataFrame.colLabelToIndex(label);
                colIndexs.add(colIndex);
            }

            List<Integer> rowIndexs = new ArrayList<>();
            for(String label : rows){
                int rowIndex = dataFrame.rowLabelToIndex(label);
                rowIndexs.add(rowIndex);
            }

            selectPorLabels(dataFrame, rowIndexs, colIndexs);
        }
    }




    public void head(DataFrame dataframe, int cant) {
        if (cant < 0 || cant > dataframe.getNumRow()) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // 1. Imprimir encabezados (nombres de columnas) si existen
        for (Column<Celda<?>> columna : dataframe.getColumns()) {
            System.out.print(columna.getName() + "\t"); // Usa tabulación o formato fijo
        }
        System.out.println(); // Salto de línea después de los encabezados

        // 2. Imprimir las filas solicitadas
        for (int i = 0; i < cant; i++) {
            for (Column<Celda<?>> columna : dataframe.getColumns()) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t"); // Asume que hay un método getValor()
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }


    public void head(DataFrame dataframe) {
        // Sobrecarga. Por defecto mostramos las primeras cinco filas
        // encabezados
        for (Column<Celda<?>> columna : dataframe.getColumns()) {
            System.out.print(columna.getName() + "\t"); // Usa tabulación o formato fijo
        }
        System.out.println(); // Salto de línea después de los encabezados

        // 2. Imprimir las filas solicitadas
        for (int i = 0; i < 5; i++) {
            for (Column<Celda<?>> columna : dataframe.getColumns()) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t"); 
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }

    public void tail(DataFrame dataFrame, int cant) {
        if (cant < 0 || cant > dataFrame.getNumRow()) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // Calcular el índice de inicio (últimas 'cant' filas)
        int startRow = dataFrame.getNumRow() - cant;

        // Imprimo encabezados (igual que en head())
        for (Column<Celda<?>> columna : dataFrame.getColumns()) {
            System.out.print(columna.getName() + "\t");
        }
        System.out.println();

        // itero desde startRow hasta el final
        for (int i = startRow; i < dataFrame.getNumRow(); i++) {
            for (Column<Celda<?>> columna : dataFrame.getColumns()) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t");
            }
            System.out.println();
        }
    }
    public void tail(DataFrame dataFrame) {

        // Calcular el índice de inicio (últimas 'cant' filas)
        int startRow = (dataFrame.getNumRow() - 5 >= 0) ? dataFrame.getNumRow() - 5 : 0; // Por defecto, mostrar las últimas 5 filas

        // encabezados
        for (Column<Celda<?>> columna : dataFrame.getColumns()) {
            System.out.print(columna.getName() + "\t");
        }
        System.out.println();

        // filas
        for (int i = startRow; i < dataFrame.getNumRow(); i++) {
            for (Column<Celda<?>> columna : dataFrame.getColumns()) {
                Celda<?> celda = columna.getList().get(i);
                System.out.print(celda.getValue() + "\t");
            }
            System.out.println(); // salto de línea
        }
    }





}
