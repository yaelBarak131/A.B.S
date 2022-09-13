package component.PendingInfo;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class PendingInfoController {

    @FXML
    private ListView<String> lendersNameList = new ListView<>();

    @FXML
    private ListView<String> lendersInvestList = new ListView<>();
    @FXML
    private Label leftRaisedLabel;

    @FXML
    private Label raisedLabel;

    private SimpleStringProperty leftRaised;
    private SimpleStringProperty raised;

    public PendingInfoController(){
        leftRaised =new SimpleStringProperty();
        raised=new SimpleStringProperty();
    }
    @FXML
    private void initialize() {
        leftRaisedLabel.textProperty().bind(leftRaised);
        raisedLabel.textProperty().bind(raised);
    }
    public void addInformationToLists(List<String> names,List<String> investments,String _leftToRaised,String _raised){
        lendersNameList.getItems().addAll(names);
        lendersInvestList.getItems().addAll(investments);
        this.leftRaised.set(_leftToRaised);
        this.raised.set(_raised);

    }
}
