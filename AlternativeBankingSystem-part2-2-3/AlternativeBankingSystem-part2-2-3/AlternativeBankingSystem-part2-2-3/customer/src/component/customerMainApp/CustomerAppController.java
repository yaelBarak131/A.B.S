package component.customerMainApp;

import component.Customer.CustomerController;
import component.login.LoginController;
import component.LoanBox.LoanBoxController;
import component.LoanBox.LoanData;
import data.PrintLoan;
import data.PrintLoans;
import data.CustomerScreenData;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;

import static util.Constants.*;

public class CustomerAppController {


    @FXML
    private BorderPane boardPane;

    @FXML
    private ScrollPane topPanelCustomerScreen;

    @FXML
    private TextArea messageLoansToPay;

    @FXML
    private Label helloCustomerLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Button addFileButton;


    @FXML
    private Label numOfCurrYAZ;
    @FXML
    private FlowPane loanFlowPane;

    @FXML
    private ComboBox<?> statusCB;


    private Stage primaryStage;
    // delete engine - change to servlet

    private BorderPane loginComponent;
    private CustomerController customerController;
    private LoginController loginController;

    private final StringProperty currentUserName;
    private StringProperty loadFileMessage;
    private SimpleBooleanProperty showFileMessage;
    private SimpleIntegerProperty currYaz;
    private YazRefresher yazRefresher;
    private Timer yazTimer;
    private SimpleBooleanProperty rewindMode;
    private BooleanProperty showMessage;
    private boolean customerScreenOn = false;
    private int yazCount = 1;

    private VBox customerScreen;

    @FXML
    void addNewXmlFile(ActionEvent event) {
        loadFileMessage.set("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        String finalUrl = HttpUrl
                .parse(Constants.LOAD_XML)
                .newBuilder()
                .addQueryParameter("userName", currentUserName.get())
                .addQueryParameter("pathXml", absolutePath)
                .build()
                .toString();


        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        loadFileMessage.set(e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();

                Platform.runLater(() -> {
                    if (response.code() != 200) {
                        loadFileMessage.set(responseBody);
                        showFileMessage.set(true);

                    } else {
                        // loadFileMessage.set(responseBody);
                        CustomerScreenData res = GSON_INSTANCE.fromJson(responseBody, CustomerScreenData.class);
                        customerController.setComponents(res.getCategories(), res.getCustomer());
                        showFileMessage.set(false);
                        customerController.setFileInTheSystem(res.isFileOnSystem());
                        //customerController.showLoans(res.getLoans(),loanFlowPane);
                    }

                });
            }
        });
    }

    @FXML
    public void initialize() {
        messageLabel.visibleProperty().bind(showMessage);
        messageLabel.textProperty().bind(loadFileMessage);

        showMessage.bind(Bindings.or(rewindMode, showFileMessage));

        numOfCurrYAZ.textProperty().bind(currYaz.asString());
        addFileButton.disableProperty().bind(rewindMode);

        messageLoansToPay.setEditable(false);

    }

    public CustomerAppController() {
        currentUserName = new SimpleStringProperty(SOMEONE);
        showFileMessage = new SimpleBooleanProperty(false);
        loadFileMessage = new SimpleStringProperty();
        currYaz = new SimpleIntegerProperty(1);
        rewindMode = new SimpleBooleanProperty(false);
        showMessage = new SimpleBooleanProperty(false);
    }


    public void loadLoginScreen(BorderPane main) {
        URL loginPageUrl = getClass().getResource(LOGIN_PAGE_FXML_RESOURCE_LOCATION);
        topPanelCustomerScreen = (ScrollPane) main.getTop();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            loginController = fxmlLoader.getController();
            boardPane.getChildren().clear();
            boardPane.setCenter(loginComponent);

            loginController.setAdminAppMainController(this);
            loginController.setCurrYaz(currYaz.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    public void loadCustomerScreen(String customerName) {
        URL mainFXML = getClass().getResource(CUSTOMER_PAGE_FXML_RESOURCE_LOCATION);
        customerScreenOn = true;
        try {
            FXMLLoader loader = new FXMLLoader();
            // load main fxml

            loader.setLocation(mainFXML);
            customerScreen = loader.load();

            customerController = loader.getController();
            //customerController.setModel(engine);
            //customerController.setComponents(engine.getCategories());

            boardPane.setTop(topPanelCustomerScreen);
            boardPane.setCenter(customerScreen);

            // wire up controller
            customerController = loader.getController();
            customerController.setCustomer(customerName);
            customerController.setActive();
            customerController.bindRewindMode(rewindMode);
            customerController.bindCurrYaz(currYaz);
            customerController.showLoansToPayInThisYaz();
            customerController.showLoansToClose();
            customerController.setMessageLoansToPay(messageLoansToPay);
        } catch (IOException e) {
            e.printStackTrace();
        }
        helloCustomerLabel.setText("Hello " + currentUserName.get() + " welcome to:");
        customerController.setPrimaryStage(primaryStage);
        primaryStage.setHeight(900);
        primaryStage.setMinWidth(1000);


        // add current yaz


    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /*public void setModel(Engine engine) {
             this.engine = engine;
         }*/
    public void showLoans(PrintLoans loans) {
        loanFlowPane.alignmentProperty().set(Pos.TOP_LEFT);

        if (loanFlowPane.getChildren().size() > 0)
            loanFlowPane.getChildren().clear();

        String status = statusCB.getValue().toString();

        if (status.equals("All")) {
            //  loans = engine.getAllLoans();
        } else
            // loans = engine.getAllLoans().getLoanWithStatus(status);

            if (loans.getLoans().size() == 0) {
                noLoansInThisStatus();
            } else {
                addLoans(loans);
            }
    }

    private void noLoansInThisStatus() {
        Label noLoans;
        //
        //check from servlet if customer was enter
        //if(isFileSelected.get()) {
        noLoans = new Label("There are no loans in this status right now.");
        loanFlowPane.alignmentProperty().set(Pos.CENTER);
        loanFlowPane.getChildren().add(noLoans);
        // }

    }

    private void addLoans(PrintLoans loans) {

        for (PrintLoan loan : loans.getLoans()) {
            createLoanTile(loan);
        }
    }

    private void createLoanTile(LoanData loan) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/LoanBox/loanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            LoanBoxController loanBoxControllerController = loader.getController();
            loanBoxControllerController.initLoanData(loan);

            loanFlowPane.getChildren().add(singleLoanTile);
            //add to list?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLoanTile(PrintLoan loan) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/LoanBox/loanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            LoanBoxController loanBoxControllerController = loader.getController();
            loanBoxControllerController.initLoanData(loan);

            loanFlowPane.getChildren().add(singleLoanTile);
            //add to list?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActive() {
        startLastTransactionsRefresher();
    }

    public void startLastTransactionsRefresher() {
        yazRefresher = new YazRefresher(
                this::updateYaz,
                this::onFail,
                rewindMode);
        yazTimer = new Timer();
        yazTimer.schedule(yazRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void onFail(String message) {
        Platform.runLater(() -> {
                    loadFileMessage.set(message);
                    showFileMessage.set(true);
                }
        );
    }

    private void updateYaz(Integer newYaz) {

        Platform.runLater(() -> {
            showFileMessage.set(false);
            this.currYaz.set(newYaz);

            if (rewindMode.get()) {
                showFileMessage.set(false);
                loadFileMessage.set("System is in REWIND mode");
            }
            if (yazCount < this.currYaz.intValue()) {
                yazCount = this.currYaz.intValue();
                if (customerScreenOn) {
                    customerController.showLoansToPayInThisYaz();
                    customerController.showLoansToClose();
                }

            }


        });

    }
}


