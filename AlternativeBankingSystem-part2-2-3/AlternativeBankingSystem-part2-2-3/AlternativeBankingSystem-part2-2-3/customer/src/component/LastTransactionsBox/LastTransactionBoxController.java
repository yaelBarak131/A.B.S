package component.LastTransactionsBox;
import data.PrintTransaction;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class LastTransactionBoxController extends BasicTransactionData {


    @FXML
    private Label yazActionLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label balanceBeforeLabel;

    @FXML
    private Label balanceAfterLabel;


    public LastTransactionBoxController() {
        yaz = new SimpleIntegerProperty();
        amountOfTransaction = new SimpleDoubleProperty();
        action = new SimpleStringProperty();
        before = new SimpleDoubleProperty();
        after = new SimpleDoubleProperty();

    }

    @FXML
    private void initialize(){

        yazActionLabel.textProperty().bind(yaz.asString());
        amountLabel.textProperty().bind(amountOfTransaction.asString());
        typeLabel.textProperty().bind(action);
        balanceBeforeLabel.textProperty().bind(before.asString());
        balanceAfterLabel.textProperty().bind(after.asString());
    }

    public void setInformation(PrintTransaction customerTransaction){
        initTransactionData(customerTransaction);
    }


}
