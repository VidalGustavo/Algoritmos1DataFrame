package Column;

import java.util.ArrayList;
import Celda.Celda;
import Celda.CeldaString;
import Celda.CeldaNumber;
import Celda.CeldaBoolean;

public class Column<T> {
    private ArrayList<Celda<T>> list;
    private String name;
    private int size;
    private static enum TIPOSCELDA {
        NUMBER, STRING, BOOLEAN
    }
    private TIPOSCELDA tipoCelda;

    // Constructores
    public Column() {
        this.name = "EmptyStrings";
        this.tipoCelda = TIPOSCELDA.STRING;
        this.list = new ArrayList<CeldaString<String>();
        this.size = 0;
    }

    public Column(String name, TIPOSCELDA tipoCelda, ArrayList<Celda<T>> list) {
        this.name = name;
        this.tipoCelda = tipoCelda;
        this.list = list;
        this.size = list.size();
    }

    public Column(ArrayList<Celda<T>> list) throws IllegalArgumentException {
        name = String.valueOf(list.get(0).getValue());

        // Define tipo celda.
        if (list.get(1) instanceof CeldaString) {
            tipoCelda = TIPOSCELDA.STRING;
        } else if (list.get(1) instanceof CeldaNumber) {
            tipoCelda = TIPOSCELDA.NUMBER;
        } else if (list.get(1) instanceof CeldaBoolean) {
            tipoCelda = TIPOSCELDA.BOOLEAN;
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
        return this.size;
    }
    public ArrayList<Celda<T>> getList() {
        // TODO: revisar si corresponde inmutabilidad
        return this.list;
    }
    public String getTipoCelda() {
        // TODO: revisar si corresponde inmutabilidad
        String tipo = new String(this.tipoCelda.name());
        return tipo;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean validateType(Object value){
        //valida que un valor se pueda agregar a la columna, segun el tipo de este.
        if (value instanceof String && this.tipoCelda == TIPOSCELDA.STRING) {
            return true;
        } else if (value instanceof Number && this.tipoCelda == TIPOSCELDA.NUMBER) {
            return true;
        } else if (value instanceof Boolean && this.tipoCelda == TIPOSCELDA.BOOLEAN) {
            return true;
        } else {
            return false; // Tipo no valido
        }
    }
    

}
