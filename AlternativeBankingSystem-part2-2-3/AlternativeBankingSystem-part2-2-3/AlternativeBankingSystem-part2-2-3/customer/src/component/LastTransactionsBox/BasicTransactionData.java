package component.LastTransactionsBox;

import data.PrintTransaction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


public class BasicTransactionData {

    protected IntegerProperty yaz;
    protected SimpleDoubleProperty amountOfTransaction;
    protected SimpleStringProperty action; // change?
    protected SimpleDoubleProperty before;
    protected SimpleDoubleProperty after;


    public void initTransactionData(PrintTransaction customerTransaction){

        this.yaz.set(customerTransaction.getYazAction());
        this.amountOfTransaction.set(customerTransaction.getAmount());
        this.action.set("" + customerTransaction.getType());
        this.before.set(customerTransaction.getBalanceBefore());
        this.after.set(customerTransaction.getBalanceAfter());

    }



}
