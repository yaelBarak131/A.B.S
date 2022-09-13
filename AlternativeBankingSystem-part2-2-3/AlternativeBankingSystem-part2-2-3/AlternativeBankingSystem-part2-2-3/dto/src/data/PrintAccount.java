package data;

import java.util.ArrayList;
import java.util.List;

public class PrintAccount {

    private List<PrintTransaction> transactions = new ArrayList<>();

    public PrintAccount(List<PrintTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<PrintTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return "PrintAccount{" +
                "transactions=" + transactions +
                '}';
    }
}
