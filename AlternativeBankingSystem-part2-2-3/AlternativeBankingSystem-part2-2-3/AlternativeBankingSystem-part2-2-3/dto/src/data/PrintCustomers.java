package data;


import java.util.ArrayList;
import java.util.List;

public class PrintCustomers {

    List<String> customersName; // names of all customers in system
    List<PrintCustomer> customers;


    public void addCustomer(PrintCustomer customer){
        customers.add(customer);
        customersName.add(customer.getName());
    }
    public PrintCustomer getSpecificCustomer(String name) {

        for(PrintCustomer customer:customers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }
    public List<PrintCustomer> getCustomers() {
        return customers;
    }

    public int printAllCustomers() {
        int len = customersName.size();
        for (int i = 0; i < len; i++)
            System.out.println((i + 1) + ". " + customersName.get(i));

        return len;
    }

/*   public PrintCustomers getAllCustomers(Customers customers, Loans loans) {

        this.customers = new ArrayList<>(customers.getCustomers().size());

        for (Customer customer : customers.getCustomers().values()) {
            customer.updateAllBorrowerLoan(loans);
            customer.updateAllLenderLoan(loans);

            PrintCustomer temp = new PrintCustomer(customer.getName(), customer.getAccount(), customer.getBorrowerLoans(), customer.getLenderLoans(),customer.getBalance());
            this.customers.add(temp);
        }
        return this;
    }*/

    public String getCustomer(int ind){
        return customersName.get(ind);
    }

    public List<String> getAllCustomersName(){return this.customersName;}
    @Override
    public String toString() {
        return "customers{" +
                customers +
                '}';
    }


}
