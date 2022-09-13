package component.CustomerBox;

import data.*;
public interface CustomerData {

    String getName();
    void setName(String name);

    double getBalance();
    void setBalance(double balance);

    void initCustomerData(String name, double balance);



}