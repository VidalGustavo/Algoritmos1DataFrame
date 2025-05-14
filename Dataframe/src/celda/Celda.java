package celda;

public abstract class Celda<T> {
    private T value;
    private Boolean isNA; // Representa si la celda esta vacia
    private static final String NA = "#N/A"; // Representa el valor a mostrar en la celda vacia.

    public Celda(T value) {
    }

    public void switchNA(){
        if (isNA) {
            this.isNA = false;
        } else {
            this.isNA = true;
        }
    }
}
