import java.util.ArrayList;
import java.util.stream.IntStream;

import DataFrame.DataFrame;

public class App {
public static void main(String[] args) throws Exception {

    //Crear DF
    DataFrame iris = DataFrame.leerCSV("Dataframe/src/IRIS.csv", true);

    System.out.println("\n Empecemos con un Shape \n");
    iris.shape();

    // Crear lo que vamos a agregar
    ArrayList<Integer> indices = new ArrayList<>();
    indices.addAll(IntStream.range(0, 150).boxed().toList()); 

    
    System.out.println("\nVamos a agregar una columna de numeros \n");
    iris.addColumnFromList(indices, "Numeritos");

    iris.shape();
    
    System.out.println("\nAhora veamos que podemos cambiar una celda en particular \n");
    iris.head(3);
    iris.setCelda(0, "species", "PLANTITA");
    iris.head(3);

    System.out.println("\nVeamos los encabezados...\n");
    iris.showColumns();
    System.out.println("\nNo me gusta 'Numeritos' digamosle 'Pepe' \n");
    iris.renameCol("Numeritos", "Pepe");
    iris.showColumns();

    System.out.println("\nVamor a reordenar el DF... en funcion de sepal_length\n");
    DataFrame irisOrdenado = iris.orderBy("sepal_length", false);
    irisOrdenado.head(3);


    System.out.println("\nNos perdimos a PLANTITA! vamos a filtrarlo y buscarlo...\n");
    DataFrame irisFiltrado = irisOrdenado.filter().where("species", "=", "PLANTITA").apply(irisOrdenado);
    irisFiltrado.head();

    System.out.println("\nVamos a guardarlo...\n");
    irisFiltrado.exportarCSV("IRIS_FILTRADO.csv");

    System.out.println("\n Display... \n");
    irisFiltrado.display();




    


    // System.out.println("\n Ahora Veamos Head y Tail: \n");
    // iris.head();
    // iris.tail(3);

    // iris.display();
    // System.out.println("Ahora printeamos una nuestra random \n");
    // iris.randomSample(0.5).display();;


        
}

}

