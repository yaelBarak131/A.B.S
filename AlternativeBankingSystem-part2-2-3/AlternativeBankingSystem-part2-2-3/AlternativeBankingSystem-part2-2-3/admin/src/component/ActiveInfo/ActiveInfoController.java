package component.ActiveInfo;


import component.PaymentInfo.PaymentInfoController;
import component.WarpPayment.WarpPaymentsController;
import data.PrintPayment;
import data.PrintPayments;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.net.URL;

public class ActiveInfoController {

    @FXML
    private Label lenders;

    @FXML
    private Label activeYazLabel;

    @FXML
    private Label nextYAZLabel;

    @FXML
    private Label allPaymentsLabel;

    private PopOver paymentInfo;
    private PopOver pendingInfo;
    private SimpleStringProperty nextYaz;
    private SimpleStringProperty activeYaz;
    private ScrollPane paymentScrollPane=new ScrollPane();
    private WarpPaymentsController warpPaymentsController;
    private PaymentInfoController paymentInfoController;

    public PopOver getPaymentInfo() {
        return paymentInfo;
    }

    public ActiveInfoController(){
        nextYaz=new SimpleStringProperty();
        activeYaz=new SimpleStringProperty();
    }

    @FXML
    private void initialize() {
       nextYAZLabel.textProperty().bind(nextYaz);
       activeYazLabel.textProperty().bind(activeYaz);
    }

    public PopOver getPendingInfo() {
        return pendingInfo;
    }

    public void setPendingInfo(PopOver pendingInfo) {
        this.pendingInfo = pendingInfo;
    }

    @FXML
    void removeLenderInfo(MouseEvent event) {
        pendingInfo.hide();
    }

    @FXML
    void removePaymentsInfo(MouseEvent event) {
        paymentInfo.hide();
    }

    @FXML
    void showLenderInfo(MouseEvent event) {
        pendingInfo.show(lenders);
    }

    @FXML
    void showPaymentsInfo(MouseEvent event) {

        paymentInfo.show(allPaymentsLabel);
    }
    public void addInformationToLists(PopOver pendingInfo, String nextYaz, String activeYaz, PrintPayments payments){
        setPendingInfo(pendingInfo);
        this.nextYaz.set(nextYaz);
        this.activeYaz.set(activeYaz);
        paymentScrollPane = createPaymetTail(payments);
    }
    private ScrollPane createPaymetTail(PrintPayments payments){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/WarpPayment/WarpPayments.fxml");
            loader.setLocation(mainFXML);
            paymentScrollPane = loader.load();
            warpPaymentsController = loader.getController();
            addPaymentsToFlowPane(payments);
            paymentInfo = new PopOver(paymentScrollPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return paymentScrollPane;
    }

    public void addPaymentsToFlowPane(PrintPayments payments){
        if(payments.getPayments().size()>0) {
            if(warpPaymentsController.getPaymentsFlowPane().getChildren().size()>0)
                warpPaymentsController.getPaymentsFlowPane().getChildren().clear();
            for (PrintPayment payment : payments.getPayments())
                warpPaymentsController.getPaymentsFlowPane().getChildren().add(createPaymentTail(payment));
            }
    }
    public VBox createPaymentTail(PrintPayment payment){
        VBox singelPayment = new VBox();
        String paymentYaz = Integer.toString(payment.getPaymentYaz());
        String capital = Integer.toString(payment.getCapital());
        String interest = Integer.toString(payment.getInterest());
        String sum = Integer.toString(payment.getSum());

        try { FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("/component/PaymentInfo/paymentInfo.fxml");
        loader.setLocation(mainFXML);
        singelPayment = loader.load();

        paymentInfoController = loader.getController();
        paymentInfoController.addInformationToPayment(paymentYaz,capital,interest,sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return singelPayment;
    }
}
