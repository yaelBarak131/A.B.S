package data;

import java.util.ArrayList;
import java.util.List;

public class CustomersRewindData {
    List<CustomerRewindData> customersRewindData;

    public CustomersRewindData() {
        customersRewindData = new ArrayList<>();
    }

    public CustomersRewindData(List<CustomerRewindData> customerRewindData) {
        this.customersRewindData = customerRewindData;
    }

    public List<CustomerRewindData> getCustomerRewindDataList() {
        return customersRewindData;
    }

    public void setCustomersRewindData(List<CustomerRewindData> customersRewindData) {
        this.customersRewindData = customersRewindData;
    }

    public CustomerRewindData getCustomerRewindDataList(String customerName){
        for (CustomerRewindData customersRewindData:customersRewindData)
              if(customersRewindData.getName().equals(customerName))
                  return customersRewindData;

    return null;

    }
}
