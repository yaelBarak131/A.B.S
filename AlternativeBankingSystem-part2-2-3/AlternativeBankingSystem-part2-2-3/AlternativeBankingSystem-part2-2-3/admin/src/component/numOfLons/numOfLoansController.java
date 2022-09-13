package component.numOfLons;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class numOfLoansController {

    @FXML
    private Text numNewLoans;

    @FXML
    private Text numPendingLoans;

    @FXML
    private Text numInRiskLoans;

    @FXML
    private Text numFinishLoans;

    public Text getNumNewLoans() {
        return numNewLoans;
    }

    public Text getNumPendingLoans() {
        return numPendingLoans;
    }

    public Text getNumInRiskLoans() {
        return numInRiskLoans;
    }

    public Text getNumFinishLoans() {
        return numFinishLoans;
    }
}
