package component.SellingLoanBox;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class BasicSellingData implements SellingData {
    protected StringProperty id;
    protected DoubleProperty price;

    @Override
    public String getLoanId() {
        return id.get();
    }

    @Override
    public void setLoanId(String loanId) {
        id.set(loanId);
    }

    @Override
    public double getSellingPrice() {
        return price.get();
    }

    @Override
    public void setSellingPrice(double price) {
        this.price.set( price);
    }

    @Override
    public void initSellingData(String loanId, double sellingPrice) {
        this.id.set(loanId);
        this.price.set(sellingPrice);
    }
}
