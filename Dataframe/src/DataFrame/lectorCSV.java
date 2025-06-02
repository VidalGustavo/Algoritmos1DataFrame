package DataFrame;

import Celda.Celda;
import Celda.CeldaBoolean;
import Celda.CeldaNumber;
import Celda.CeldaString;
import Column.Column;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class lectorCSV {
    protected static List<String> leerLineas(String nombreArchivo){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            List<String> lineas = new ArrayList<>();
            //Mientras la linea no sea nula, la agrego a la lista
            while ((linea = bufferedReader.readLine()) != null) {
                lineas.add(linea);
            }
            return lineas;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Esto trabaja con filas, no con columnas.
    //Todos los datos son strings
    protected static String[][] parsearLineas(List<String> lineas, String sep){
        int filas = lineas.size();
        String[][] celdas = null;
        for(int i = 0; i < filas; i++){
            String linea = lineas.get(i);
            String[] campos = linea.split(sep);
            //Si celda sigue siendo null (es la primer linea), creo la matriz con los largos del archivo:
            if(celdas == null){
                celdas = new String[filas][campos.length];
            }
            if (campos.length != celdas[0].length) {
                int lineaFalla = i+1;
                //Esto tal vez podría ser una excepción personalizada:
                throw new RuntimeException("A la fila " + lineaFalla + " le faltan datos.");
            }
            //Por cada celda de la linea, la agrego en la coordenada que le corresponde:
            for(int j = 0; j < campos.length; j++){
                celdas[i][j] = campos[j];
            }
        }
        return celdas;
    }

    //Transforma cada celda en una Celda de su tipo de dato
    //Pasa de trabajar en filas a trabajar en columnas
    protected static DataFrame<Column> crearDataframe(String[][] celdas){
        int cantColumnas = celdas[0].length;
        int cantFilas = celdas.length;

        DataFrame<Column> dataframe = new DataFrame(cantFilas, cantColumnas);
        List<Celda> listaCeldas = new ArrayList<Celda>();

        //Busco los tipos de datos y creo las celdas correspondientes:
        for(String[] fila : celdas){
            for(String celda : fila){
                //Si es una celda booleana:
                if(celdas.equals("True") || celda.equals("true") ||
                        celda.equals("False") || celda.equals("false")){
                    CeldaBoolean celdaBoolean = new CeldaBoolean(Boolean.valueOf(celda));
                    listaCeldas.add(celdaBoolean);
                }
                //Si es una celda number:
                //*Primero pruebo para ints:
                try{
                    CeldaNumber celdaNumber = new CeldaNumber(Integer.valueOf(celda));
                    listaCeldas.add(celdaNumber);
                } catch (NumberFormatException e) {
                    //*Luego pruebo para floats:
                    try{
                        CeldaNumber celdaNumber = new CeldaNumber(Float.valueOf(celda));
                        listaCeldas.add(celdaNumber);
                        //Sino es string:
                    } catch (NumberFormatException ex) {
                        CeldaString celdaString = new CeldaString(celda);
                        listaCeldas.add(celdaString);
                    }
                }
            }
        }

        for(int j = 0; j < cantColumnas; j++){
            ArrayList<Celda<Celda>> listaCeldasAux = new ArrayList<Celda<Celda>>();
            for (int i = j; i < listaCeldas.size(); i += cantColumnas){
                listaCeldasAux.add(listaCeldas.get(i));
            }
            String nombreCol = String.valueOf(listaCeldasAux.get(0).getValue());
            String tipoCol = String.valueOf(listaCeldasAux.get(1).getTipo().toUpperCase());

            Column<Celda> columnaAux = new Column<Celda>(listaCeldasAux);
            dataframe.addColumn(columnaAux);
        }

        return dataframe;
    }

    public static DataFrame<Column> leerCSV(String rutaArchivo){
        List<String> lineasLeidas = leerLineas(rutaArchivo);
        String[][] celdas;
        try {
            celdas = parsearLineas(lineasLeidas, ",");
            return crearDataframe(celdas);
        } catch (RuntimeException e) {
            System.out.println("");
            return null;
        }
    }

    protected static DataFrame<Column> leerCSV(String rutaArchivo, String separador){
        List<String> lineasLeidas = leerLineas(rutaArchivo);
        String[][] celdas;
        try {
            celdas = parsearLineas(lineasLeidas, separador);
            return crearDataframe(celdas);
        } catch (RuntimeException e) {
            System.out.println(e);
            return null;
        }
    }
}
