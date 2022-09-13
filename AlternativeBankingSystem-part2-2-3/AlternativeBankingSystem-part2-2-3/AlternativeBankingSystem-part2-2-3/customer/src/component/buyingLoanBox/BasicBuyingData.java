package component.buyingLoanBox;

import component.SellingLoanBox.SellingData;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class BasicBuyingData implements BuyingData {
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
    public double getBuyingPrice() {
        return price.get();
    }

    @Override
    public void setBuyingPrice(double price) {
        this.price.set( price);
    }

    @Override
    public void initBuyingData(String loanId, double sellingPrice) {
        this.id.set(loanId);
        this.price.set(sellingPrice);
    }
}
