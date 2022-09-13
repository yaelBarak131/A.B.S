package data;

import java.util.Set;

public class CustomerScreenData {
    PrintLoans loans;
    Set<String> categories;
    PrintCustomer customer;
    boolean fileOnSystem = false;
    public CustomerScreenData(PrintLoans loans, Set<String> categories,PrintCustomer customer,boolean isFileInSystem) {
        this.loans = loans;
        this.categories = categories;
        this.customer=customer;
        this.fileOnSystem=isFileInSystem;
    }

    public boolean isFileOnSystem() {
        return fileOnSystem;
    }

    public PrintLoans getLoans() {
        return loans;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public PrintCustomer getCustomer() {
        return customer;
    }
}
