package data;

import java.util.ArrayList;
import java.util.List;

public class RelventLoansData {

      String name;
      int amount;
      List<String> categories;
      int interest;
      int minYaz;
      int maxLoan;

      public RelventLoansData(){}
    public RelventLoansData(String name, int amount, List<String> categories, int interest, int minYaz, int maxLoan) {
        this.name = new String(name);
        this.amount = amount;
        this.categories =new ArrayList<>(categories);
        this.interest = interest;
        this.minYaz = minYaz;
        this.maxLoan = maxLoan;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getInterest() {
        return interest;
    }

    public int getMinYaz() {
        return minYaz;
    }

    public int getMaxLoan() {
        return maxLoan;
    }

    @Override
    public String toString() {
        return "RelventLoansData{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", categories=" + categories +
                ", interest=" + interest +
                ", minYaz=" + minYaz +
                ", maxLoan=" + maxLoan +
                '}';
    }
}
