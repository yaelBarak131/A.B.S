package data;

import java.util.ArrayList;
import java.util.List;

public class PrintTransactions {

    List<PrintTransaction> transactions;


    public PrintTransactions(PrintAccount customerAccount) {

        this.transactions = new ArrayList<>();

        for( PrintTransaction a: customerAccount.getTransactions()) {
            this.transactions.add(new PrintTransaction(a.getYazAction(), a.getAmount(), a.getType(), a.getBalanceBefore(), a.getBalanceAfter()));
        }

    }

    public List<PrintTransaction> getTransactions() { return transactions;}

    @Override
    public String toString() {
        return "PrintTransactions{" +
                "customers=" + transactions +
                '}';
    }
}
