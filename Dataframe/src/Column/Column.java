package column;

import java.util.ArrayList;
import celda.Celda;
import celda.CeldaString;
import celda.CeldaNumber;
import celda.CeldaBoolean;

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

    protected boolean validateType(T value){
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
    };

}
