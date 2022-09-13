package component.Admin;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Counter
{
    final private IntegerProperty value = new SimpleIntegerProperty();

    public IntegerProperty valueProperty(){
        return value;
    }
    public int getValue(){return value.get();}

    public void setValue(int i){
        value.set(i);
    }
}
