package component.LoanBox;

import component.ActiveInfo.ActiveInfoController;
import component.FinishedInfo.FinishInfoController;
import component.PendingInfo.PendingInfoController;
import component.RiskInfo.RiskInfoController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import data.*;
import org.controlsfx.control.PopOver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoanBoxController extends BasicLoanData {


    @FXML
    private Label idLabel;
    @FXML
    private Label ownerLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label capitalLabel;
    @FXML
    private Label totalYazLabel;
    @FXML
    private Label interestLabel;
    @FXML
    private Label statusLabel;



    private PopOver popOverstatus;
    private PopOver pendingPopOver;
    private PopOver activePopOver;
    private PendingInfoController pendingInfoController;
    private ActiveInfoController activeInfoController;
    private RiskInfoController riskInfoController;
    private FinishInfoController finishInfoController;
    private BooleanProperty allowToPress;

    public LoanBoxController() {
        id = new SimpleStringProperty();
        owner = new SimpleStringProperty();
        category = new SimpleStringProperty();
        capital = new SimpleIntegerProperty();
        totalYaz = new SimpleIntegerProperty();
        interest = new SimpleIntegerProperty();
        status = new SimpleStringProperty();
        allowToPress=new SimpleBooleanProperty(true);
    }


    @FXML
    private void initialize() {
        idLabel.textProperty().bind(id);
        ownerLabel.textProperty().bind(owner);
        categoryLabel.textProperty().bind(category);
        capitalLabel.textProperty().bind(capital.asString());
        totalYazLabel.textProperty().bind(totalYaz.asString());
        interestLabel.textProperty().bind(interest.asString());
        statusLabel.textProperty().bind(status);

    }



    @FXML
    void showStatusInfo(MouseEvent event) {

            if (status.get().equals("Pending"))
                popOverstatus = pendingPopOver();

            if (status.get().equals("Active"))
                popOverstatus = activePopOver();

            if (status.get().equals("In Risk"))
                popOverstatus = RiskPopOver();

            if (status.get().equals("Finished"))
                popOverstatus = finishPopOver();

            if (!status.get().equals("New")) {
                popOverstatus.show(statusLabel);
            }


    }
    private PopOver pendingPopOver(){
        List<String> lendersNames = new ArrayList<>(getLoan().getLendersName());
        List<String> lendersInvestment = new ArrayList<>(getLoan().getLendersInvest());
        String leftToRaised = Integer.toString(getLoan().getCapitalToPay() - getLoan().getTotalAmountRaised());
        String raised = Integer.toString(getLoan().getTotalAmountRaised());

        pendingPopOver = new PopOver(createPendingTile(lendersNames, lendersInvestment, leftToRaised, raised));

        return pendingPopOver;
    }
    private PopOver activePopOver(){
        String nextYaz = Integer.toString(getLoan().getNextYazPayment());
        String activeYaz = Integer.toString(getLoan().getActivationYaz());
        PrintPayments payments = new PrintPayments();
        payments.setPayments(getLoan().getPayments());
       if(pendingPopOver == null)
           pendingPopOver();

       activePopOver = new PopOver(createActiveTile(pendingPopOver, nextYaz, activeYaz, payments));
       return activePopOver;
    }
   private PopOver RiskPopOver(){

       String numOfLatePay = Integer.toString(getLoan().getNumOfLatePayments());
       String sumOfLatePayments = Integer.toString(getLoan().getCapitalDebt() + getLoan().getInterestDebt());

        if(activePopOver==null)
            activePopOver();

      return new PopOver(createRiskTile(numOfLatePay, sumOfLatePayments, activePopOver));
   }
   private PopOver finishPopOver(){
       String startYaz=Integer.toString(getLoan().getActivationYaz());
       String finishYaz=Integer.toString(getLoan().getFinishedYaz());
          if(activeInfoController==null)
              activePopOver();
       PopOver paymentInfo= activeInfoController.getPaymentInfo();
          return new PopOver(createFinishTile(startYaz,finishYaz,activeInfoController.getPendingInfo(), paymentInfo));
   }
    private VBox createPendingTile(List<String> lendersNames, List<String> lendersInvestment, String leftToRaised, String raised) {
        VBox singlePendingTile = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/PendingInfo/pendingInfo.fxml");
            loader.setLocation(mainFXML);
            singlePendingTile = loader.load();

            pendingInfoController = loader.getController();
            pendingInfoController.addInformationToLists(lendersNames, lendersInvestment, leftToRaised, raised);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return singlePendingTile;
    }

    private VBox createActiveTile(PopOver pendingInfo, String nextYaz, String activeYaz, PrintPayments payments) {
        VBox singleActiveTile = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/ActiveInfo/ActiveInfo.fxml");
            loader.setLocation(mainFXML);
            singleActiveTile = loader.load();

            activeInfoController = loader.getController();
            activeInfoController.addInformationToLists(pendingInfo, nextYaz, activeYaz, payments);// payments

        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleActiveTile;
    }

    private VBox createRiskTile(String numOfLatePayments, String sumOfDebt, PopOver activeinfo) {
        VBox singleRiskTile = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/RiskInfo/riskInfo.fxml");
            loader.setLocation(mainFXML);
            singleRiskTile = loader.load();

            riskInfoController = loader.getController();
            riskInfoController.addInfo(numOfLatePayments, sumOfDebt, activeinfo);//payments

        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleRiskTile;
    }

    private VBox createFinishTile(String startsYaz, String finishYaz, PopOver pendingInfo, PopOver paymentsInfo) {
        VBox singleFinishTile = new VBox();
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/FinishedInfo/finishInfo.fxml");
            loader.setLocation(mainFXML);
            singleFinishTile = loader.load();

            finishInfoController = loader.getController();
            finishInfoController.setInfo(startsYaz, finishYaz, pendingInfo,paymentsInfo);// payments

        } catch (IOException e) {
            e.printStackTrace();
        }
        return singleFinishTile;
    }
}
