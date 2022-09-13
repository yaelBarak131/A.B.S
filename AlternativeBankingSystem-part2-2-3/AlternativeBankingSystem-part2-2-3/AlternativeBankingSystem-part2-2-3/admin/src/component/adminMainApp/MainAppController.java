package component.adminMainApp;

import component.Admin.AdminController;
import component.Admin.Counter;
import component.login.LoginController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static util.Constants.*;

public class MainAppController {


    @FXML
    private Label numOfCurrYAZ;

    @FXML
    private Button increaseYazButton;

    @FXML
    private BorderPane boardPane;

    @FXML
    private ScrollPane topPanelAdminScreen;
    @FXML
    private Button RewindModeButton;
    @FXML
    private Label adminNameLabel;
    @FXML
    private Text errorMessage;

    @FXML
    private ComboBox<String> chooseYazCB;

    final Counter counter = new Counter();
    final Counter oldCounter = new Counter();

    private Stage primaryStage;
    // delete engine - change to servlet

    private BorderPane loginComponent;
    private AdminController adminController;
    private LoginController loginController;

    private BorderPane borderPane;

    private final StringProperty currentUserName;
    private StringProperty adminName;
    private StringProperty message;
    private BooleanProperty showMessage;

    private BooleanProperty ableChooseYaz;
    private BooleanProperty ableIncreaseYaz;

    private BooleanProperty isFileSelected;
    private StringProperty decreaseYazText;
    private BooleanProperty rewindMode;

    private VBox adminScreen;


    @FXML
    public void initialize() {
        // prepare components
        // add load to login and admin page?
        // loadLoginPage();
        //loadChatRoomPage();
        adminNameLabel.textProperty().bind(adminName);
        numOfCurrYAZ.textProperty().bind(counter.valueProperty().asString());
        errorMessage.textProperty().bind(message);
        errorMessage.visibleProperty().bind(showMessage);
        chooseYazCB.disableProperty().bind(ableChooseYaz);
        RewindModeButton.textProperty().bind(decreaseYazText);
        increaseYazButton.disableProperty().bind(ableIncreaseYaz);
    }

    public MainAppController() {
        currentUserName = new SimpleStringProperty(SOMEONE);
        counter.setValue(1);
        isFileSelected = new SimpleBooleanProperty(false);
        adminName = new SimpleStringProperty();
        message = new SimpleStringProperty("");
        ableChooseYaz = new SimpleBooleanProperty(true);
        decreaseYazText = new SimpleStringProperty("Start rewind");
        ableIncreaseYaz = new SimpleBooleanProperty(false);
        rewindMode = new SimpleBooleanProperty(false);
        showMessage = new SimpleBooleanProperty(false);
    }


    public boolean isIsFileSelected() {
        return isFileSelected.get();
    }

    public BooleanProperty isFileSelectedProperty() {
        return isFileSelected;
    }


    public void loadLoginScreen(BorderPane main) {
        URL loginPageUrl = getClass().getResource(LOGIN_PAGE_FXML_RESOURCE_LOCATION);
        // does it work?
        topPanelAdminScreen = (ScrollPane) main.getTop();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(loginPageUrl);
            loginComponent = fxmlLoader.load();
            loginController = fxmlLoader.getController();
            boardPane.getChildren().clear();
            boardPane.setCenter(loginComponent);

            loginController.setAdminAppMainController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUserName(String userName) {
        currentUserName.set(userName);
    }

    public void loadAdminScreen(String userName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            // load main fxml
            URL mainFXML = getClass().getResource(ADMIN_PAGE_FXML_RESOURCE_LOCATION);
            loader.setLocation(mainFXML);
            adminScreen = loader.load();
            boardPane.setTop(topPanelAdminScreen);
            boardPane.setCenter(adminScreen);

            // wire up controller
            adminController = loader.getController();
            adminController.setPrimaryStage(primaryStage);
            primaryStage.setMinHeight(850);
            primaryStage.setWidth(950);
            String name = "Admin Name: " + userName;
            adminName.set(name);
            numOfCurrYAZ.textProperty().bind(getCurrYaz());
            adminController.getRewindMode().bind(rewindMode);
            adminController.getCurrYaz().bind(counter.valueProperty());
            adminController.setActive();

        } catch (IOException e) {
            e.printStackTrace();
        }


        // borderPane.setCenter(adminScreen);
        // customerButtom.disableProperty().bind(adminController.isFileSelectedProperty().not());
        //  adminController.setModel(engine);
        // adminController.showLoans();
        //adminController.showCustomer();
    }


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

   /* public void setModel(Engine engine) {
            this.engine = engine;
        }*/

    @FXML
    void increaseYaz(ActionEvent event) {
        String finalUrl = HttpUrl
                .parse(Constants.SET_YAZ)
                .newBuilder()
                .addQueryParameter(ACTION_TYPE, INCREASE)
                .addQueryParameter(CHOSE_YAZ, "stam")

                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                //todo 14/7
                Platform.runLater(() -> {
                    message.set(e.getMessage());
                    showMessage.set(true);
                });
                // think about something else to respond

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //todo 14/7
                String body = response.body().string();
                Platform.runLater(() -> {
                    if(response.code()!=200) {
                        message.set(body);
                        showMessage.set(true);
                    }   else{
                        showMessage.set(false);
                        counter.setValue(Integer.parseInt(body));
                    } });
            }
        });
        // engine.updateYaz(counter.getValue());

    }

    public StringExpression getCurrYaz() {
        return counter.valueProperty().asString();
    }

    @FXML
    void setRewindMode(ActionEvent event) {
        message.set("");


        if (!rewindMode.get()) {
            setChooseYaz();
            oldCounter.setValue(counter.getValue());
            rewindMode.set(true);
            decreaseYazText.set("Stop rewind");
            ableIncreaseYaz.set(true);
            ableChooseYaz.set(false);
            //chooseYazCB.setValue(Integer.toString(counter.getValue()));
        } else {
            counter.setValue(oldCounter.getValue());
            rewindMode.set(false);
            decreaseYazText.set("Start rewind");
            ableChooseYaz.set(true);
            ableIncreaseYaz.set(false);
        }
        String finalUrl = HttpUrl
                .parse(Constants.SET_YAZ)
                .newBuilder()
                .addQueryParameter(ACTION_TYPE, REWIND_MODE)
                .addQueryParameter(CHOSE_YAZ, Integer.toString(counter.getValue()))
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                //todo 14/7
                Platform.runLater(() -> {
                    message.set(e.getMessage());
                    showMessage.set(true);
                });
                // think about something else to respond

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //todo 14/7
                String body = response.body().string();
                Platform.runLater(() -> {
                    if(response.code()!=200)
                    {
                        message.set(body);
                        showMessage.set(true);
                    }else {
                        showMessage.set(false);
                    }
                });
                //think if we need to do something
            }

        });
    }

    private void setChooseYaz() {
        if (chooseYazCB.getItems().size() > 0)
            chooseYazCB.getItems().clear();

        ObservableList<String> list = chooseYazCB.getItems();
        chooseYazCB.setPromptText("Choose YAZ");
        int size = Integer.parseInt(getCurrYaz().get());
        //Adding items to the combo box
        for (int i = 1; i <= size; i++) {
            list.add(Integer.toString(i));
        }
    }

    @FXML
    void chooseYAZ(ActionEvent event) {
        String choseYaz = chooseYazCB.getValue();
        if (choseYaz != null) {
            counter.setValue(Integer.parseInt(choseYaz));

            //send to customer the yaz ....
            String finalUrl = HttpUrl
                    .parse(Constants.SET_YAZ)
                    .newBuilder()
                    .addQueryParameter(ACTION_TYPE, "setRewindYaz")
                    .addQueryParameter(CHOSE_YAZ, Integer.toString(counter.getValue()))
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                    //todo 14/7
                    Platform.runLater(() -> {
                        message.set(e.getMessage());
                        showMessage.set(true);
                    });
                    // think about something else to respond

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    //todo 14/7
                    String body = response.body().string();
                    Platform.runLater(() -> {
                        if(response.code()!=200)
                        {
                            message.set(body);
                            showMessage.set(true);
                        }else {
                            showMessage.set(false);
                        }
                    });  //think if we need to do something
                }

            });
        }
    }

    public boolean isRewindMode() {
        return rewindMode.get();
    }
}
