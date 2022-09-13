package component.buyingLoanBox;

import component.SellingLoanBox.BasicSellingData;
import data.PrintLoanToPay;
import data.SellingLoanData;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.Map;

public class BuyingLoanController extends BasicBuyingData {

        @FXML
        private Label idLabel;

        @FXML
        private Label priceLabel;
        private Map<String, SellingLoanData> loansToBuy;
        private Label balanceAfter;
        @FXML
        private Label canNotPressLabel;
        @FXML
        private CheckBox selected;
        String sellingCustomer;

        public BuyingLoanController(){
                id = new SimpleStringProperty();
                price = new SimpleDoubleProperty();

        }
        @FXML
        private void initialize() {
                idLabel.textProperty().bind(id);
                priceLabel.textProperty().bind(price.asString());

        }
         public void bindSelected(BooleanProperty rewindMode){
                 selected.disableProperty().bind(rewindMode);
         }
        @FXML
        public void checkboxChecked(ActionEvent actionEvent) {

                double before =  Double.parseDouble(balanceAfter.textProperty().getValue());
                double sum = before - getBuyingPrice();

                if (loansToBuy.containsKey(getLoanId()) && !selected.isSelected()) {
                        loansToBuy.remove(getLoanId());
                        sum = before + getBuyingPrice();
                        balanceAfter.setText(Double.toString(sum));
                        canNotPressLabel.setText("");
                }
                else if (sum < 0) {
                        selected.selectedProperty().set(false);
                        canNotPressLabel.setText("Not enough money");

                } else if (!loansToBuy.containsKey(getLoanId()) && selected.isSelected()) {

                        this.loansToBuy.put(getLoanId(), getSellingLoanData());
                        balanceAfter.setText(Double.toString(sum));
                        canNotPressLabel.setText("");

                }
        }

        public void setInformation(SellingLoanData loanToBuy, Label balanceAfter, Map<String, SellingLoanData> toBuy) {
                initBuyingData(loanToBuy.getLoanId(), loanToBuy.getSellingPrice());
                this.balanceAfter = balanceAfter;
                this.loansToBuy = toBuy;
                canNotPressLabel.setText("");
                this.sellingCustomer = loanToBuy.getSellingCustomer();
        }

        private SellingLoanData getSellingLoanData(){
                return new SellingLoanData(getLoanId(), getBuyingPrice(), sellingCustomer);

        }


}
