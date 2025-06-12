package Column;

import java.util.ArrayList;
import Celda.Celda;
import DataFrame.TipoDatos;

public class Column<T> {
    private ArrayList<Celda<T>> list;
    private String name;
    private int size;
    private TipoDatos tipoCelda;

    // Constructores
    public Column() {
        name = "EmptyStrings";
        tipoCelda = TipoDatos.STRING;
        list = new ArrayList<Celda<T>>();
        size = 0;
    }

    public Column(String name, TipoDatos tipoCelda, ArrayList<Celda<T>> list) {
        this.name = name;
        this.tipoCelda = tipoCelda;
        this.list = list;
        size = list.size();
    }

    public Column(ArrayList<Celda<T>> list) throws IllegalArgumentException {
        name = String.valueOf(list.get(0).getValue());

        // Define tipo celda.
        if (list.get(1).getTipoDato() == TipoDatos.STRING) {
            tipoCelda = TipoDatos.STRING;
        } else if (list.get(1).getTipoDato() == TipoDatos.NUMBER) {
            tipoCelda = TipoDatos.NUMBER;
        } else if (list.get(1).getTipoDato() == TipoDatos.BOOLEAN) {
            tipoCelda = TipoDatos.BOOLEAN;
        } else {
            throw new IllegalArgumentException("Tipo de celda no soportado");
        }
        // Valida celdas para el tipo de columna.
        for (int i=1; i < list.size(); i++) {
            if (!this.validateType(list.get(i).getValue())){
               throw new IllegalArgumentException("Tipo de celda en el indice " + i + " no coincide con el tipo de columna.");
            }
        }
        this.list = list;
        size = list.size()-1;
    }

    // Getters y Setters
    public String getName() {
        // TODO: revisar si corresponde inmutabilidad
        String name = new String(this.name);
        return name;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Celda<T>> getList() {
        // TODO: revisar si corresponde inmutabilidad
        return list;
    }

    public TipoDatos getTipoCelda() {
        // TODO: revisar si corresponde inmutabilidad
        return tipoCelda;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean validateType(Object value){
        //valida que un valor se pueda agregar a la columna, segun el tipo de este.
        if (value instanceof String && tipoCelda == TipoDatos.STRING) {
            return true;
        } else if (value instanceof Number && tipoCelda == TipoDatos.NUMBER) {
            return true;
        } else if (value instanceof Boolean && tipoCelda == TipoDatos.BOOLEAN) {
            return true;
        } else {
            return false; // Tipo no valido
        }
    }

    public void addCelda(Celda<T> celda){
        list.add(celda);
    }

    public Column<T> copy(){
        ArrayList<Celda<T>> celdasCopia = new ArrayList<>();
        for(Celda<T> celdaOriginal : list){
            Celda<T> celdaCopia = celdaOriginal.copy();
            celdasCopia.add(celdaCopia);
        }

        Column<T> copia = new Column(name, tipoCelda, celdasCopia);

        return copia;
    }

}
