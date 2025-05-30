import celda.Celda;
import celda.CeldaString;

public class TestCeldaString {
    public static void main(String[] args) {
        CeldaString celda = new CeldaString("hola mundo");

        // a) Ver su valor
        System.out.println("Valor actual: " + celda.getValue());

        // b) Corregir su valor
        celda.setValue("hola, como andamio?");
        

        // c) Imprimir su valor
        System.out.print("Imprimir valor: ");
        celda.imprimirValor();

        // d) Ver su tipo
        System.out.println("Tipo de celda: " + celda.getTipo());

        // Probar cambiar a NA
        celda.setValue(null);
        System.out.print("Luego de setValue(null), el valor de la celda es:  ");
        celda.imprimirValor();
        System.out.println("¿Es NA?: " + celda.getIsNA());
    }
}