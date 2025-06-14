package Operaciones;

import DataFrame.Celda;
import DataFrame.Column;
import DataFrame.DataFrame;

import java.util.ArrayList;
import java.util.Random;

public class MuestreadorRandom {
    private static ArrayList<Integer> generateRandomIndexes(int maxIndexes, int numIndexes){
        //Verifico que no se pidan más indices de los máximos disponibles:
        if(numIndexes > maxIndexes){
            throw new IllegalArgumentException("No se pueden generar más índices únicos que la cantidad total disponible.");
        }

        ArrayList<Integer> randomIndexes = new ArrayList<>();
        Random random = new Random();

        while(randomIndexes.size() < numIndexes){
            int randomIndex = random.nextInt(maxIndexes);

            if(!randomIndexes.contains(randomIndex)){
                randomIndexes.add(randomIndex);
            }
        }

        return randomIndexes;
    }

    public static DataFrame sample(DataFrame df, double p){
        //Verifico que el valor de porcentaje esté dentro del rango esperado:
        if(p < 0 || p > 1){
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 1.");
        }

        int numRows = df.getNumRow();
        int numSampleRows = (int) (numRows * p);
        DataFrame sample = new DataFrame();

        ArrayList<Integer> randomIndexes = generateRandomIndexes(numRows, numSampleRows);

        for(int i = 0; i < df.getNumCol(); i++){
            Column<Celda<?>> sampleColumn = new Column<Celda<?>>(df.getColumn(i).getTipoCelda());

            //Agrego el nombre de la columna:
            String nombre = df.getColumn(i).getName();
            sampleColumn.setName(nombre);

            //Agrego las celdas de la muestra a la columna:
            for(int rowIndex : randomIndexes){
                Celda celda = df.getCelda(rowIndex, i);
                sampleColumn.addCelda(celda);
            }

            //Agrego la columna a la muestra:
            sample.addColumn(sampleColumn);
            sample.setNumRow(numSampleRows);
        }

        return sample;
    }

    public static DataFrame sample(DataFrame df){
        double porcentaje = Math.random();
        return sample(df, porcentaje);
    }
}
