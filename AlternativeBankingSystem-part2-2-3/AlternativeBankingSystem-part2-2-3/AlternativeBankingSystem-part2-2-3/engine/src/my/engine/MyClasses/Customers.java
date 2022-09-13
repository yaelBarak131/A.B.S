package my.engine.MyClasses;


import java.util.HashMap;
import java.util.Map;

public class Customers {

    private Map<String, Customer> customers = new HashMap<>();

    /**
     * Gets the value of the absCustomer property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the absCustomer property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAbsCustomer().add(newItem);
     * </pre>
     */
    public Map<String, Customer> getCustomers() {
        if (customers == null) {
            customers = new HashMap<>();
        }
        return this.customers;
    }

//    public Object copyJaxbClass(AbsDescriptor abs) throws CustomerException {
//        customers.clear();
//
//        int size = abs.getAbsCustomers().getAbsCustomer().size();
//        customers = new HashMap<>(size);
//
//
//        for (AbsCustomer absC : abs.getAbsCustomers().getAbsCustomer()) {
//            Customer temp = new Customer();
//            temp = temp.copy(absC);
//            String name = absC.getName();
//            customers.put(name, temp);
//        }
//        return this;
//    }

    /**
     * Check that every costumer have an uniq name.
     * It's ok if we don't have any costumers?
     * <p>
     * and if she is ok , she also copy the varibels.//think how to split this maybe?
     */
//    public void checkJaxbClass(AbsDescriptor abs) throws CustomerException {
//        Set<String> temp = new HashSet<>();
//
//        for (AbsCustomer absC : abs.getAbsCustomers().getAbsCustomer()) {
//            if (temp.contains(absC.getName()))
//                throw new CustomerException(absC.getName());
//
//            else
//                temp.add(absC.getName());
//        }
//    }

    public Customer findCustomer(String name){
        return this.customers.get(name);
    }

    public void uploadBorrowLoans(Loans loans){
        for(Customer customer:getCustomers().values())
        {
            customer.updateAllBorrowerLoan(loans);
        }
    }
}


