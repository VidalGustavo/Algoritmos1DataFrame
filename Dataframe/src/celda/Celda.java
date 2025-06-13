package Celda;

import DataFrame.TipoDatos;

public class Celda<T> implements Comparable<Celda<T>> {
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

    /*
    Copia profunda
    Asume que T es inmutable (String, booleano o número)
     */
    public Celda<T> copy(){
        Celda copia = new Celda(value, tipoDato);
        return copia;
    }

    @Override
    public int compareTo(Celda<T> o) {
        // Fuera de las implementaciones de Celda vacia y tipoDato. Delega las restricciones de compareTo al tipo de dato.
        if (this.tipoDato != o.tipoDato) {throw new ClassCastException("No se puede comparar celdas de diferentes tipos de datos.");}
        if (this.isNA && o.isNA) {
            return 0; // Ambos son NA, se consideran iguales
        } else if (this.isNA) {
            return -1; // NA es menor que cualquier otro valor
        } else if (o.isNA) {
            return 1; // Cualquier otro valor es mayor que NA
        }
        
        if (this.value instanceof Comparable) {
            return ((Comparable<T>) this.value).compareTo(o.value);
        } else {
            throw new ClassCastException("El valor de la celda no es comparable. Los tipos de datos deben implementar Comparable.");
        }

    }
    
}