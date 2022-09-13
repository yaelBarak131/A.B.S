package component.CustomerBox;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import data.*;
public class BasicCustomerData implements CustomerData{

    protected SimpleStringProperty name;
    protected SimpleDoubleProperty balance;


    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public double getBalance() {
        return this.balance.get();
    }

    @Override
    public void setBalance(double balance) {
        this.balance.set(balance);

    }
    @Override
    public void initCustomerData(String name, double balance){
        this.name.set(name);
        this.balance.set(balance);

    }

}