package Celda;

import DataFrame.TipoDatos;

public class Celda<T> {
    protected T value;
    protected Boolean isNA; // Representa si la celda está vacía
    private static final String NA = "#N/A"; // Valor a mostrar en celda vacía
    private TipoDatos tipoDato;

    // Constructor
    public Celda(T value, TipoDatos tipoDato) {
        this.value = value;
        isNA = (value == null);
        this.tipoDato = tipoDato;
    }

    // a) Ver el valor
    public T getValue() {
        return value;
    }

    // b) Corregir el valor
    public void setValue(T value) {
        this.value = value;
        this.isNA = (value == null);
    }

    // c) Imprimir el valor
    public void imprimirValor() {
        if (isNA) {
            System.out.println(NA);
        } else {
            System.out.println(value);
        }
    }

    // d) Ver el tipo
    public String getTipo() {
        if (value == null) return "NA";
        return value.getClass().getSimpleName();
    }

    // e) Ver si la celda está vacía
    public Boolean getIsNA() {
        return isNA;
    }

    // f) Cambiar el estado NA
    public void switchNA(){
        this.isNA = !this.isNA;
        if (this.isNA) this.value = null;
    }

    public TipoDatos getTipoDato(){
        return tipoDato;
    }
}