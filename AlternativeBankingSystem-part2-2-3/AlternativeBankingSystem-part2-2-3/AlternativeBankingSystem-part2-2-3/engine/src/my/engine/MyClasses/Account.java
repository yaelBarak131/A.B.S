package my.engine.MyClasses;

import java.util.ArrayList;
import java.util.List;

public class Account {    //מספיק שיהיה datamemebers של coustmer?
    private List<Transaction> transactions = new ArrayList<>();


    public void addTransaction(int yaz, double amount, char action, double balanceBefore, double balanceAfter) {
        Transaction temp = new Transaction(yaz, amount, action, balanceBefore, balanceAfter);
        transactions.add(temp);

    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getBalance() { return transactions.get(transactions.size()-1).getBalanceAfter();}
    @Override
    public String toString() {
        String res = new String();

        res += "Account { ";

        if (this == null)
            res += "No actions on the account yet.}";
        else {
            res += "transactions = " + transactions +
                    '}';
        }
        return res;
    }
}
