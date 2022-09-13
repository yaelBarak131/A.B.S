package my.engine.MyClasses;

import my.engine.exception.*;
import my.engine.jaxb.generatedEX3.*;

public class Descriptor implements CheckAndCopy {

    private  Categories categories = new Categories();
    private  Loans loans = new Loans();
    private  Customers customers = new Customers();

    /**
     * Gets the value of the categories property.
     */
    public  Categories getCategories() {
        return categories;
    }

    /**
     * Sets the value of the absCategories property.
     */
    public void setCategories(Categories value) {
        this.categories = value;
    }

    /**
     * Gets the value of the loans property.
     */
    public Loans getLoans() {
        return loans;
    }

    /**
     * Sets the value of the loans property.
     */
    public void setAbsLoans(Loans value) {
        this.loans = value;
    }

    /**
     * Gets the value of the customers property.
     */
    public Customers getCustomers() {
        return customers;
    }

    /**
     * Sets the value of the customers property.
     */
    public void setCustomers(Customers value) {
        this.customers = value;
    }

    public Object copyJaxbClass(AbsDescriptor abs, String name) throws CategoryException, PaymentRateException, CustomerException, OwnerException, CloneNotSupportedException {

        this.categories = (Categories) categories.copyJaxbClass(abs,"");  //?

        Loans tempLoans = loans.copyJaxbClass(abs,name);
        this.loans.getLoans().addAll(tempLoans.getLoans()) ;

        //add new customer to the system
        //if is a new customer we need to add him to the customers list, else we need to upload his borrow list
        if(!this.customers.getCustomers().containsKey(name)) {
         throw new CustomerException(name);
          /*  Customer newCustomer = new Customer().copy(name);
            newCustomer.setBorrowerLoans(tempLoans);
            this.customers.getCustomers().put(name, newCustomer);*/
        } else {
            this.customers.getCustomers().get(name).setBorrowerLoans(tempLoans);
        }

        return this;
    }

    public void checkJaxbClass(AbsDescriptor abs) throws CategoryException, PaymentRateException, CustomerException, OwnerException, LoanIdException {
        categories.checkJaxbClass(abs);
        //customers.checkJaxbClass(abs);
        loans.checkJaxbClass(abs,this.categories.getCategories(),this.loans.getLoans());
    }

    public void addCustomer(String name){
        if(!this.customers.getCustomers().containsKey(name)) {
            Customer newCustomer = new Customer().copy(name);
            this.customers.getCustomers().put(name, newCustomer);
        }
    }

    public void addCategory(String category){
        if(!categories.getCategories().contains(category))
          categories.getCategories().add(category);
    }

}
