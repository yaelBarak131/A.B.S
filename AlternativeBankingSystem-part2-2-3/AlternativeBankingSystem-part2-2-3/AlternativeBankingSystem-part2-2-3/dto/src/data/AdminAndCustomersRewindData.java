package data;

import java.util.ArrayList;
import java.util.List;

public class AdminAndCustomersRewindData {
    //admin rewind data:
    List<PrintCustomerAndBalance> customers;
    List<PrintLoans> loans;

    //customers rewind data
    List<CustomersRewindData> customersRewindData;

    public AdminAndCustomersRewindData() {
        this.customers = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.customersRewindData = new ArrayList<>();
    }

    public void addLoans(PrintLoans printLoans, int currYaz) {
        if (this.loans == null)
            this.loans = new ArrayList<PrintLoans>();

        if (currYaz == loans.size()) {
            if (loans.size() == 0) {
                loans.clear();
                loans.add(printLoans);
            } else {
                int i = currYaz - 1;
                List<PrintLoans> newList = new ArrayList<>();
                for (int j = 0; j < loans.size(); j++) {
                    if (j == i)
                        newList.add(printLoans);
                    else
                        newList.add(loans.get(j));
                }
                loans = newList;
            }
        } else {
            this.loans.add(printLoans);

        }
    }

    public PrintLoans getLoansInThisYaz(int yaz) {
        if (yaz == 0)
            return null;

        int i = yaz - 1;
        return this.loans.get(i);
    }

    public void addCustomers(PrintCustomerAndBalance customerAndBalance, int currYaz) {
        if (customers == null)
            customers = new ArrayList<>();


        if (currYaz == customers.size()) {
            if (customers.size() == 0) {
                customers.clear();
                customers.add(customerAndBalance);
            } else {
                int i = currYaz - 1;
                List<PrintCustomerAndBalance> newList = new ArrayList<>();
                for (int j = 0; j < customers.size(); j++) {
                    if (j == i)
                        newList.add(customerAndBalance);
                    else
                        newList.add(customers.get(j));
                }
                customers = newList;
            }
        } else {
            customers.add(customerAndBalance);
        }
    }

    public void addCustomersInLoginYaz(int loginYaz, String customerName) {

        List<String> customerData = new ArrayList<>();

        customerData.add(customerName);
        customerData.add("0");
        List<List<String>> customerAndBalance = new ArrayList<>();
        customerAndBalance.add(customerData);

        PrintCustomerAndBalance temp = new PrintCustomerAndBalance(customerAndBalance);
        int i = loginYaz - 1;

        if (customers.size() == 0)
            customers.add(temp);
        else
            customers.get(i).getCustomers().add(customerData);

    }

    public PrintCustomerAndBalance getCustomerAndBalanceThisYaz(int yaz) {
        if (yaz == 0)
            return null;
        int i = yaz - 1;

        return this.customers.get(i);
    }
    public CustomersRewindData getCustomersRewindThisYaz(int yaz) {
        if (yaz == 0)
            return null;
        int i = yaz - 1;

        return this.customersRewindData.get(i);
    }
    public void updateCustomerBalance(String customerName, double balance, int currYaz) {
        int i = currYaz - 1;
        List<List<String>> temp = customers.get(i).getCustomers();

        for (List<String> miniList : temp) {
            if (miniList.get(0).equals(customerName)) {
                miniList.remove(miniList.get(1));
                miniList.add(Double.toString(balance));
            }

        }

    }

    public void saveCustomersData(CustomersRewindData customersToSave, int currYaz) {
        if (this.customersRewindData == null)
            this.customersRewindData = new ArrayList<>();

        if (currYaz == customersRewindData.size()) {
            if (customersRewindData.size() == 0) {
                customersRewindData.clear();
                customersRewindData.add(customersToSave);
            } else {
                int i = currYaz - 1;
                List<CustomersRewindData> newList = new ArrayList<>();
                for (int j = 0; j < customersRewindData.size(); j++) {
                    if (j == i)
                        newList.add(customersToSave);
                    else
                        newList.add(customersRewindData.get(j));
                }
                customersRewindData = newList;
            }
        } else {
            this.customersRewindData.add(customersToSave);

        }

    }

    public PrintLoansToPay payInThisYazRewindData(String customerName, int currYaz) {
        return getLoansToPayActiveAndInRisk(customerName, currYaz, true);

    }
    private PrintLoansToPay getLoansToPayActiveAndInRisk(String customerName, int currYaz, boolean type) {
        PrintLoans temp = new PrintLoans();

        for (PrintLoan loan : loans.get(currYaz).getLoans()) {

            if ((loan.getStatus().getStatus().equals(PrintStatus.Statuses.Active) || loan.getStatus().getStatus().equals(PrintStatus.Statuses.InRisk) )&& loan.getOwner().equals(customerName)) // check if this method works well
                temp.addLoan(loan);
        }
        temp.getLoans().removeIf(a -> a.getStatus().getNextYazPayment() != currYaz && a.getStatus().getStatus().equals(PrintStatus.Statuses.Active)); // is this check is enough

        return new PrintLoansToPay(temp.getLoans(),type);
    }

    public PrintLoansToPay closeLoansRewindData(String customerName, int currYaz) {
        return getLoansToPayActiveAndInRisk(customerName, currYaz, false);
    }
}
