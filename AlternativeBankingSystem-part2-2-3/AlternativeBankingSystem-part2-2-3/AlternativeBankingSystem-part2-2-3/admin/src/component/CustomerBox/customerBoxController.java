package component.CustomerBox;

import component.numOfLons.numOfLoansController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
public class customerBoxController extends BasicCustomerData {

    @FXML
    private Label nameLabel;

    @FXML
    private Label balanceLabel;


    public customerBoxController(){
        name = new SimpleStringProperty();
        balance = new SimpleDoubleProperty();

    }
    @FXML
    private void initialize() {
        nameLabel.textProperty().bind(name);
        balanceLabel.textProperty().bind(balance.asString());

    }



}