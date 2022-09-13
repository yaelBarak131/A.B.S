package component.RiskInfo;

import component.ActiveInfo.ActiveInfoController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;

public class RiskInfoController {

    @FXML
    private Label numOfLatePayment;

    @FXML
    private Label sumOfDebt;

    @FXML
    private Label activeInformation;



    private StringProperty num;
    private StringProperty sum;
    private PopOver activeInfo;

    public RiskInfoController(){
        num=new SimpleStringProperty();
        sum=new SimpleStringProperty();
    }
    @FXML
    private void  initialize(){
        numOfLatePayment.textProperty().bind(num);
        sumOfDebt.textProperty().bind(sum);
    }
    public void addInfo(String numOfPayments,String debt,PopOver activeInfo){
        num.set(numOfPayments);
        sum.set(debt);
        this.activeInfo = activeInfo;
    }
    @FXML
    void showActiveInfo(MouseEvent event) {
        activeInfo.show(activeInformation);
    }
}
