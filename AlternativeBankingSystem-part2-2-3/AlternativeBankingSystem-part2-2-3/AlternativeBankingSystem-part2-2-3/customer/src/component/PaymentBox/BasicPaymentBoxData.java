package component.PaymentBox;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BasicPaymentBoxData {

    protected SimpleStringProperty id;
    protected SimpleIntegerProperty yazTime;
    protected SimpleIntegerProperty amount;

    public String getId() {
        return this.id.get();
    }

    public void setName(String id) {
        this.id.set(id);
    }

    public int getYazTime() { return yazTime.getValue();}

    public void setYazTime( int yaz) {
        this.yazTime.set(yaz);
    }

    public int getAmount() {return amount.getValue();}

    public void setAmount(int payment) {
        this.amount.set(payment);
    }

    public void initPaymentData(String id, int yaz, int payment){
        this.id.set(id);
        this.yazTime.set(yaz);
        this.amount.set(payment);

    }


}
