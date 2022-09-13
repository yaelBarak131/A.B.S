package component.PaymentBox;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import data.*;


import java.util.Map;

public class PaymentBoxController extends BasicPaymentBoxData {

        @FXML
        private Label IdPaymentLabel;

        @FXML
        private Label yazPaymentLabel;

        @FXML
        private Label paymentPaymentLabel;

        @FXML
        private CheckBox selected;

        @FXML
        private Label canNotPressLabel;
        private Label balanceAfter1;
        private Label balanceAfter;
        private Map<String, PrintLoanToPay> loansToPay;
        private Map<String, PrintLoanToPay> loansToClose;

        private boolean type;

        public void bindSelected(BooleanProperty rewindMode){
                selected.disableProperty().bind(rewindMode);
        }
        public PaymentBoxController() {
                id = new SimpleStringProperty();
                yazTime = new SimpleIntegerProperty();
                amount = new SimpleIntegerProperty();
        }

        @FXML
        private void initialize() {
                IdPaymentLabel.textProperty().bind(id);
                yazPaymentLabel.textProperty().bind(yazTime.asString());
                paymentPaymentLabel.textProperty().bind(amount.asString());
        }

        public void setInformation(PrintLoanToPay customerTransaction, Label balanceAfter, Label balanceAfter1, Map<String, PrintLoanToPay> toPay, Map<String, PrintLoanToPay> toClose, boolean type) {
                initPaymentData(customerTransaction.getId(), customerTransaction.getYazToPay(), customerTransaction.getDebt());
                this.balanceAfter = balanceAfter;
                this.balanceAfter1 = balanceAfter1;
                this.type = type;
                loansToPay = toPay;
                loansToClose = toClose;
                canNotPressLabel.setText("");
        }
        @FXML
        void checkboxChecked(ActionEvent event) {

                if (type)
                        updateToPay();

                else
                        updateToClose();


        }
        private void updateToClose(){

                int before = (int)Double.parseDouble(balanceAfter.textProperty().getValue());
                int sum =  before - getAmount();

                if (loansToClose.containsKey(getId()) && !selected.isSelected()) {
                        loansToClose.remove(getId());
                        sum = before + getAmount();
                        balanceAfter.setText(Integer.toString(sum));
                        canNotPressLabel.setText("");
                }
                if(sum < 0) {

                        selected.selectedProperty().set(false);
                        canNotPressLabel.setText("not enough money in account ");
                }
                else if(!loansToClose.containsKey(getId()) && selected.isSelected()) {
                        this.loansToClose.put(getId(), getPrintLoanObj());
                        balanceAfter.setText(Integer.toString(sum));
                        canNotPressLabel.setText("");
                }

        }
        private void updateToPay() {

                int before = (int) Double.parseDouble(balanceAfter1.textProperty().getValue());
                int sum = before - getAmount();

                if (loansToPay.containsKey(getId()) && !selected.isSelected()) {
                        loansToPay.remove(getId());
                        sum = before + getAmount();
                        balanceAfter1.setText(Integer.toString(sum));
                        canNotPressLabel.setText("");
                }
                else if (sum < 0) {

                        selected.selectedProperty().set(false);
                } else if (!loansToPay.containsKey(getId()) && selected.isSelected()) {
                        this.loansToPay.put(getId(), getPrintLoanObj());
                        balanceAfter1.setText(Integer.toString(sum));
                        canNotPressLabel.setText("");

                }

        }

        private PrintLoanToPay getPrintLoanObj(){
                PrintLoanToPay add = new PrintLoanToPay();
                add.setId(getId());
                add.setYazToPay(getYazTime());
                add.setDebt(getAmount());
                return add;

        }

}