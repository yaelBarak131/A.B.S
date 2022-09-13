package component.SellingLoanBox;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SellingLoanController extends BasicSellingData{


        @FXML
        private Label idLabel;

        @FXML
        private Label priceLabel;

        public SellingLoanController(){
                id = new SimpleStringProperty();
                price = new SimpleDoubleProperty();

        }
        @FXML
        private void initialize() {
                idLabel.textProperty().bind(id);
                priceLabel.textProperty().bind(price.asString());

        }


}
