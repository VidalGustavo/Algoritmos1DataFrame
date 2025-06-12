import DataFrame.DataFrame;
import DataFrame.LectorCSV;

public static void main(String[] args) throws Exception {
        // Usar para probar pero tratar de dejar vacio hasta que hagamos tests
        // o metamos alguna estructura basica para simplificar pruebas.
    DataFrame iris = LectorCSV.leerCSV("Dataframe/src/IRIS.csv");
    System.out.println(iris.getCelda(1,0).getValue());
}

