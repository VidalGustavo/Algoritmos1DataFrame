package Operaciones;

import DataFrame.Celda;
import DataFrame.Column;
import DataFrame.DataFrame;

import java.util.ArrayList;

public class Visualizador {
    public static void display(DataFrame df) {
        // Verifico si el DataFrame está vacío usando los getters existentes
        if (df.getNumCol() == 0 || df.getNumRow() == 0) {
            System.out.println("DataFrame Vacío");
            return;
        }

        // Calculo el ancho máximo necesario para cada columna
        int[] columnWidths = calculateColumnWidths(df);

        // Imprimo el separador del encabezado
        printSeparator(columnWidths);

        // Imprimo los nombres de las columnas con formato
        printFormattedColumnNames(columnWidths, df);
        System.out.println("|");

        // Imprimo el separador después de los encabezados
        printSeparator(columnWidths);

        // Imprimo cada fila con formato
        printFormattedRows(columnWidths, df);

        // Imprimo el separador inferior
        printSeparator(columnWidths);

        // Imprimo las dimensiones del DataFrame usando el método shape()
        System.out.print("\n");
        df.shape();
    }

    private static int[] calculateColumnWidths(DataFrame df) {
        int[] columnWidths = new int[df.getNumCol()];
        for (int i = 0; i < df.getNumCol(); i++) {
            Column<Celda<?>> column = df.getColumn(i);
            // Inicializo con la longitud del nombre de la columna
            columnWidths[i] = column.getName().length();

            // Verifico la longitud de cada valor
            for (int j = 0; j < df.getNumRow(); j++) {
                String value = String.valueOf(df.getCelda(j, i).getValue());
                columnWidths[i] = Math.max(columnWidths[i], value.length());
            }
        }
        return columnWidths;
    }

    private static void printFormattedColumnNames(int[] columnWidths, DataFrame df) {
        for (int i = 0; i < df.getNumCol(); i++) {
            String name = df.getColumn(i).getName();
            System.out.print("| " + String.format("%-" + columnWidths[i] + "s", name) + " ");
        }
    }

    private static void printFormattedRows(int[] columnWidths, DataFrame df) {
        for (int i = 0; i < df.getNumRow(); i++) {
            ArrayList<Celda<?>> row = df.getRow(i);
            for (int j = 0; j < df.getNumCol(); j++) {
                String value = String.valueOf(row.get(j).getValue());
                System.out.print("| " + String.format("%-" + columnWidths[j] + "s", value) + " ");
            }
            System.out.println("|");
        }
    }

    private static void printSeparator(int[] columnWidths) {
        for (int width : columnWidths) {
            System.out.print("+-" + "-".repeat(width) + "-");
        }
        System.out.println("+");
    }
}
