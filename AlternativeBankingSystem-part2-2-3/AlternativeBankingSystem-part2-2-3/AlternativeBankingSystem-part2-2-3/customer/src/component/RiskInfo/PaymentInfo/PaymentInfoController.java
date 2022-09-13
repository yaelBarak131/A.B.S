package component.RiskInfo.PaymentInfo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PaymentInfoController {

    @FXML
    private Label paymentYazLabel;

    @FXML
    private Label capitalLabel;

    @FXML
    private Label interestLabel;

    @FXML
    private Label sumLabel;

    private StringProperty paymentYaz;
    private StringProperty capital;
    private StringProperty interest;
    private StringProperty sum;

    public PaymentInfoController(){
        paymentYaz=new SimpleStringProperty();
        capital=new SimpleStringProperty();
        interest=new SimpleStringProperty();
        sum = new SimpleStringProperty();
    }
    @FXML
    private void initialize() {
        paymentYazLabel.textProperty().bind(paymentYaz);
        capitalLabel.textProperty().bind(capital);
        interestLabel.textProperty().bind(interest);
        sumLabel.textProperty().bind(sum);
    }

    public void addInformationToPayment(String paymentYaz, String capital, String interest, String sum) {
        this.paymentYaz.set(paymentYaz);
        this.capital.set(capital);
        this.interest.set(interest);
        this.sum.set(sum);
    }


}