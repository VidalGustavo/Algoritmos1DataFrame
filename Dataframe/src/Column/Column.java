package Column;

import java.util.ArrayList;
import celda.Celda;

public class Column<Celda> {
    private ArrayList<Celda> list;
    private String name;
    private int size;
    private static enum TIPOSCELDA {
        NUMBER, STRING, BOOLEAN
    }
    private TIPOSCELDA tipoCelda;

    public String getName() {
        // TODO: revisar si corresponde inmutabilidad
        String name = new String(this.name);
        return name;
    }
    public int getSize() {
        return this.size;
    }
    public ArrayList<Celda> getList() {
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
    };

}
