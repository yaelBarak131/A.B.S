package component.login;

import component.customerMainApp.CustomerAppController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;

public class LoginController {

    @FXML
    public TextField userNameTextField;

    @FXML
    public Label errorMessageLabel;
    private CustomerAppController customerMainAppController;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();
    private int currYaz;
    @FXML
    public void initialize() {
        errorMessageLabel.textProperty().bind(errorMessageProperty);
        HttpClientUtil.setCookieManagerLoggingFacility(line ->
                Platform.runLater(() ->
                        updateHttpStatusLine(line)));
    }

    @FXML
    private void loginButtonClicked(ActionEvent event) {

        String userName = userNameTextField.getText();
        if (userName.isEmpty()) {
            errorMessageProperty.set("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        // added Query parameter - typeUser = admin
        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("userName", userName)
                .addQueryParameter("typeUser", "customer")
                .build()
                .toString();

        updateHttpStatusLine("New request is launched for: " + finalUrl);

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.code() != 200) {
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    Platform.runLater(() -> {
                        customerMainAppController.updateUserName(userName);
                        customerMainAppController.loadCustomerScreen(userName);
                        customerMainAppController.setActive();
                    });
                }
            }
        });
    }

    @FXML
    private void userNameKeyTyped(KeyEvent event) {
        errorMessageProperty.set("");
    }

    @FXML
    private void quitButtonClicked(ActionEvent e) {
        Platform.exit();
    }

    private void updateHttpStatusLine(String data) {
        // change
        //mainAppController.updateHttpLine(data);
    }
    // delete?
    public void setAdminAppMainController(CustomerAppController customerMainAppController) {
        this.customerMainAppController = customerMainAppController;
    }

    public int getCurrYaz() {
        return currYaz;
    }

    public void setCurrYaz(int currYaz) {
        this.currYaz = currYaz;
    }
}
