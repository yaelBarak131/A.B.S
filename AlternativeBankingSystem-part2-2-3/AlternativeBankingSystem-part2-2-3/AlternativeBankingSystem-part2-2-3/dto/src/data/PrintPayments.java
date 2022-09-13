package data;


import java.util.ArrayList;
import java.util.List;

public class PrintPayments {
    List<PrintPayment> payments;

    public PrintPayments(){
        payments=new ArrayList<>();
    }

    public PrintPayments(List<PrintPayment> payments) {
        this.payments = payments;
    }

    public List<PrintPayment> getPayments(){return payments;}

    public void setPayments(List<PrintPayment> payments){
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "PrintPayments{" +
                "payments=" + payments +
                '}';
    }
}
