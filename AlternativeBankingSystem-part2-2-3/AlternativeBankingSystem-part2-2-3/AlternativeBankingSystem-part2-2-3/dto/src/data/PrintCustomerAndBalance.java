package data;

import java.util.ArrayList;
import java.util.List;

public class PrintCustomerAndBalance {

    List<List<String>> customers;

    public PrintCustomerAndBalance(){
        customers = new ArrayList<>();
    }
    public PrintCustomerAndBalance(List<List<String>> customers) {
        this.customers = customers;
    }

    public List<List<String>> getCustomers() {
        return customers;
    }

}
