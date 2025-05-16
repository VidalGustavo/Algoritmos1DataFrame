import java.util.ArrayList;
import column.Column;
import celda.Celda;

public class DataFrame<T> {
    //private ArrayList <Rows> rows ;
    private ArrayList<Column<Celda<T>>> columns;
    private int numRow;
    private int numCol ;
    

    public DataFrame() {
        this.columns = new ArrayList<Column<CeldaString>>();
        this.numRow = 0;
        this.numCol = 0;
    }
    public DataFrame(ArrayList<Column<T>> lista)  {
        // Empecemos con las validaciones
        // Validar que los largos de los arrays son iguales

        
        this.numCol = columns.size();
        this.numRow = columns.get(0).getSize();

    }    

    public DataFrame(ArrayList<ArrayList<T>>){

    }
    
}