package component.FinishedInfo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.PopOver;

public class FinishInfoController {

    @FXML
    private Label pendingInfoLabel;

    @FXML
    private Label paymentsLabel;

    @FXML
    private Label startingYazLabel;

    @FXML
    private Label finishYazLabel;

    private StringProperty startingYaz;
    private StringProperty finishYaz;
    private PopOver pendingInfo;
    private PopOver paymentsInfo;

    public FinishInfoController(){
        startingYaz=new SimpleStringProperty();
        finishYaz=new SimpleStringProperty();
    }
    @FXML
    private void  initialize(){
        startingYazLabel.textProperty().bind(startingYaz);
        finishYazLabel.textProperty().bind(finishYaz);
    }

    public void setInfo(String startsYaz,String finishYaz,PopOver pendingInfo,PopOver paymentsInfo){
        this.startingYaz.set(startsYaz);
        this.finishYaz.set(finishYaz);
        this.pendingInfo=pendingInfo;
        this.paymentsInfo=paymentsInfo;
    }
    @FXML
    void showPaymentsDescription(MouseEvent event) {
          paymentsInfo.show(paymentsLabel);
    }

    @FXML
    void showPendingInfo(MouseEvent event) {
       pendingInfo.show(pendingInfoLabel);
    }

}
