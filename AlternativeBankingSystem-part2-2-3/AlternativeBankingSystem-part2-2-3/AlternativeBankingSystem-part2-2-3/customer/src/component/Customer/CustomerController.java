package component.Customer;


import com.google.gson.Gson;
import component.CustomerLastTransactions.CustomerLastTransactionsController;
import component.LastTransactionsBox.LastTransactionBoxController;
import component.LoanBox.LoanBoxController;
import component.PaymentBox.PaymentBoxController;
import component.SellingLoanBox.SellingLoanController;
import component.buyingLoanBox.BuyingLoanController;
import data.SellingLoanData;
import data.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import okhttp3.*;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.PopOver;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static util.Constants.*;
import static util.Constants.BUY_LOANS;

public class CustomerController {
    @FXML
    private TextField idTF;

    @FXML
    private ComboBox<String> categoryCB1;

    @FXML
    private TextField capitalTF;

    @FXML
    private TextField totalYazTimeTF;

    @FXML
    private TextField paysEvreyYazTF;

    @FXML
    private TextField interestPerPaymentTF;

    @FXML
    private ComboBox<String> statusCB;

    @FXML
    private FlowPane loanFlowPane;
    @FXML
    private Text messageLoansToSell;
    @FXML
    private ComboBox<String> statusCB1;

    @FXML
    private FlowPane loanFlowPane1;

    @FXML
    private Label accountBalance;

    @FXML
    private TextField enterAmountCM;

    @FXML
    private Text actionWork;
    @FXML
    private TabPane mainTP;

    @FXML
    private TextField enterAmountWM;
    @FXML
    private VBox addLoanVbox;
    @FXML
    private Button confirmBuy;

    @FXML
    private Button confirmSell;
    @FXML
    private Button comfirmPayment;
    @FXML
    private Button comfirmPayment1;

    @FXML
    private TabPane transctionTab;
    @FXML
    private Label errorMessage;

    @FXML
    private Text actionWork1;

    @FXML
    private FlowPane loanToInvestFlowPane;
    @FXML
    private TextField amountTF;

    @FXML
    private TextField intrestTF;

    @FXML
    private TextField minYazTF;
    @FXML
    private Text amountInvalidText;

    @FXML
    private Text interestInvalidText;

    @FXML
    private Text yazInvalidText;
    @FXML
    private FlowPane lastTransactionsFlowPane;
    @FXML
    private CheckComboBox<String> categoryCB;
    @FXML
    private Label lastTransactions;
    @FXML
    private TextField maxLoanTF;
    @FXML
    private TextField maxPrecentTF;
    @FXML
    private Text maxPrecentLabel;
    @FXML
    private Text maxLoanLabel;
    @FXML
    private Text finishInlayLabel;
    @FXML
    private FlowPane paymentPayInThisCurrentYaz;
    @FXML
    private FlowPane paymentCloseALoan;
    @FXML
    private Label payYourLoansLabel;
    @FXML
    private Label balanceAfterLabel;
    @FXML
    private Label balanceAfterLabel1;
    @FXML
    private Label currBalancePaymentLabel;
    @FXML
    private Label currBalancePaymentLabel1;
    @FXML
    private Label confirmPaymentCloseLabel;
    @FXML
    private Label confirmPaymentThisYazLabel;
    @FXML
    private Button inlayButton;
    @FXML
    private ProgressBar taskProgressBar;

    @FXML
    private FlowPane flowPaneShowLoansToBuy;

    @FXML
    private Label currentBalanceBuyLabel;

    @FXML
    private Label afterBuyingBalanceLabel;

    @FXML
    private FlowPane flowPaneShowLoansToSell;

    @FXML
    private Label currentBalanceSellLabel;

    @FXML
    private Label afterSellBalanceLabel;
    //?
    @FXML
    private Label confirmPaymentBuyLoans;

    @FXML
    private Text idErrorText;

    @FXML
    private Text categoryErrorText;

    @FXML
    private Text capitalErrorText;

    @FXML
    private Text totalYazErrorText;

    @FXML
    private Text payYazErrorText;

    @FXML
    private Text interestErrorText;

    @FXML
    private Text finishMessageText;
    @FXML
    private Button contineButton;
    private double balanceCountBuyLoans = 0;
    private BuyingLoanController buyingLoanController;


    /********************************************************************************************************************/
    private PopOver popOverTransactions;
    private CustomerLastTransactionsController customerLastTransactionsController;
    private LastTransactionBoxController lastTransactionBoxController;
    private StringProperty balance;
    private Stage primaryStage;
    private String customerName;
    private BooleanProperty choseCustomer;
    private StringProperty amountMessage;
    private StringProperty interestMessage;
    private StringProperty yazMassage;
    private StringProperty maxPrecentMessage;
    private StringProperty categoryMessage;

    private StringProperty maxLoanMessage;
    private BooleanProperty showAmountMessage;
    private BooleanProperty showInterestMessage;
    private BooleanProperty showYazMessage;
    private BooleanProperty showMaxLoanMessage;
    private BooleanProperty showMaxPrecentMessage;
    private BooleanProperty showFinishInlayMessage;
    private StringProperty finishInlayMessage;
    private Map<String, CheckBox> investLoans;
    private Map<SellingLoanData, CheckBox> loanToSell;
    private Map<SellingLoanData, CheckBox> loanToBuy;
    private BooleanProperty areCategoriesInSystem;
    private PrintLoans relventLoans;
    private PaymentBoxController paymentBoxController;
    private Map<String, PrintLoanToPay> loansToPay;
    private Map<String, PrintLoanToPay> loansToClose;
    private Label noLoansToClose = null;
    private Label noLoansToPay = null;
    private BooleanProperty showInlayButton;
    private final StringProperty chargeProperty = new SimpleStringProperty();
    private final StringProperty withdrawProperty = new SimpleStringProperty();
    private LastTransactionsRefresher printTransactionsRefresher;
    private Timer transactionsTimer;
    private LoansRefresher loanerLoansRefresher;
    private CategoriesAndFileStatusRefresher customerAndFileRefresher;

    private LoansRefresher lenderLoansRefresher;
    private LoanToSellRefresher loanToSellRefresher;
    private LoanToBuyRefresher loanToBuyRefresher;
    private IntegerProperty currYaz;
    private Map<String, SellingLoanData> toBuy;
    private boolean inlayWas = false;
    private Timer loansTimer;
    private Timer customerAndFileRefresherTimer;
    private final IntegerProperty totalTransactions;
    private StringProperty idErrorMessage;
    private StringProperty loansToSellMessage;
    private BooleanProperty showLoansToSellMessage;
    private BooleanProperty rewindMode;
    private BooleanProperty fileInTheSystem;
    private StringProperty capitalErrorMessage;
    private StringProperty totalYazErrorMessage;
    private StringProperty payYazErrorMessage;
    private StringProperty interestErrorMessage;
    private StringProperty finishMessage;
    private BooleanProperty showIdErrorMessage;

    private BooleanProperty showCapitalErrorMessage;
    private BooleanProperty showTotalYazErrorMessage;
    private BooleanProperty showPayYazErrorMessage;
    private BooleanProperty showInterestErrorMessage;
    private BooleanProperty showCategoryErrorMessage;
    Map<Integer, List<String>> messagesInRewindMode = new HashMap<>();
    
    private BooleanProperty showFinishAddLoanMessage;
    LoansOnSell oldLoansToSell;
    LoansOnSell oldLoansToBuy;
    Set<String> oldCategories;

    private StringProperty errorText;
    private BooleanProperty showErrorMessage;
    TextArea messageLoansToPay;
    private int countTransactions = 0;

    /********************************************************************************************************************/

    public CustomerController() {
        investLoans = new HashMap<>();
        loanToSell = new HashMap<>();
        loanToBuy = new HashMap<>();
        oldCategories = new HashSet<>();
        balance = new SimpleStringProperty("0.0");
        choseCustomer = new SimpleBooleanProperty(true);
        finishInlayMessage = new SimpleStringProperty();
        showFinishInlayMessage = new SimpleBooleanProperty(false);
        amountMessage = new SimpleStringProperty("your amount to invest os off your limit");
        interestMessage = new SimpleStringProperty("your interest is invalid");
        yazMassage = new SimpleStringProperty("your min yaz is invalid");
        maxPrecentMessage = new SimpleStringProperty("Invalid input");
        maxLoanMessage = new SimpleStringProperty("Invalid input");
        showAmountMessage = new SimpleBooleanProperty(false);
        showInterestMessage = new SimpleBooleanProperty(false);
        showYazMessage = new SimpleBooleanProperty(false);
        showMaxLoanMessage = new SimpleBooleanProperty(false);
        showMaxPrecentMessage = new SimpleBooleanProperty(false);
        showInlayButton = new SimpleBooleanProperty(false);
        totalTransactions = new SimpleIntegerProperty();
        idErrorMessage = new SimpleStringProperty("Your id exist in the system please enter a different one");
        capitalErrorMessage = new SimpleStringProperty("your capital is invalid");
        totalYazErrorMessage = new SimpleStringProperty("your total yaz time is invalid");
        payYazErrorMessage = new SimpleStringProperty("your pays every yaz is invalid");
        interestErrorMessage = new SimpleStringProperty("your interest is invalid");
        categoryMessage = new SimpleStringProperty("You have to choose a cetogey");
        finishMessage = new SimpleStringProperty();
        showIdErrorMessage = new SimpleBooleanProperty(false);
        showCapitalErrorMessage = new SimpleBooleanProperty(false);
        showTotalYazErrorMessage = new SimpleBooleanProperty(false);
        showPayYazErrorMessage = new SimpleBooleanProperty(false);
        showInterestErrorMessage = new SimpleBooleanProperty(false);
        showCategoryErrorMessage = new SimpleBooleanProperty(false);
        showFinishAddLoanMessage = new SimpleBooleanProperty(false);
        loansToSellMessage = new SimpleStringProperty();
        showLoansToSellMessage = new SimpleBooleanProperty(false);
        areCategoriesInSystem = new SimpleBooleanProperty(false);
        rewindMode = new SimpleBooleanProperty(false);
        currYaz = new SimpleIntegerProperty(1);
        oldLoansToSell = new LoansOnSell();
        oldLoansToBuy = new LoansOnSell();
        fileInTheSystem = new SimpleBooleanProperty(false);
        errorText = new SimpleStringProperty();
        showErrorMessage = new SimpleBooleanProperty(false);
    }

    /********************************************************************************************************************/

    @FXML
    private void initialize() {
        // actionWork is for charge option
        actionWork.textProperty().bind(chargeProperty);

        // actionWork is for withdraw option
        actionWork1.textProperty().bind(withdrawProperty);

        accountBalance.textProperty().bind(balance);
        mainTP.disableProperty().bind(choseCustomer.not());

        amountInvalidText.textProperty().bind(amountMessage);
        amountInvalidText.visibleProperty().bind(showAmountMessage);

        interestInvalidText.textProperty().bind(interestMessage);
        interestInvalidText.visibleProperty().bind(showInterestMessage);

        yazInvalidText.textProperty().bind(yazMassage);
        yazInvalidText.visibleProperty().bind(showYazMessage);

        maxLoanLabel.textProperty().bind(maxLoanMessage);
        maxLoanLabel.visibleProperty().bind(showMaxLoanMessage);

        maxPrecentLabel.textProperty().bind(maxPrecentMessage);
        maxPrecentLabel.visibleProperty().bind(showMaxPrecentMessage);

        finishInlayLabel.textProperty().bind(finishInlayMessage);
        finishInlayLabel.visibleProperty().bind(showFinishInlayMessage);

        inlayButton.disableProperty().bind(showInlayButton.not());
        contineButton.disableProperty().bind(areCategoriesInSystem.not());

        idErrorText.textProperty().bind(idErrorMessage);
        idErrorText.visibleProperty().bind(showIdErrorMessage);

        capitalErrorText.textProperty().bind(capitalErrorMessage);
        capitalErrorText.visibleProperty().bind(showCapitalErrorMessage);

        payYazErrorText.textProperty().bind(payYazErrorMessage);
        payYazErrorText.visibleProperty().bind(showPayYazErrorMessage);

        totalYazErrorText.textProperty().bind(totalYazErrorMessage);
        totalYazErrorText.visibleProperty().bind(showTotalYazErrorMessage);

        interestErrorText.textProperty().bind(interestErrorMessage);
        interestErrorText.visibleProperty().bind(showInterestErrorMessage);

        finishMessageText.textProperty().bind(finishMessage);
        finishMessageText.visibleProperty().bind(showFinishAddLoanMessage);

        categoryErrorText.textProperty().bind(categoryMessage);
        categoryErrorText.visibleProperty().bind(showCategoryErrorMessage);

        messageLoansToSell.textProperty().bind(loansToSellMessage);
        messageLoansToSell.visibleProperty().bind(showLoansToSellMessage);
        //bind componentTo rewind mode
        addLoanVbox.disableProperty().bind(rewindMode);
        addLoanVbox.disableProperty().bind(fileInTheSystem.not());

        errorMessage.textProperty().bind(errorText);
        errorMessage.visibleProperty().bind(showErrorMessage);

        confirmBuy.disableProperty().bind(rewindMode);
        confirmSell.disableProperty().bind(rewindMode);
        comfirmPayment.disableProperty().bind(rewindMode);
        comfirmPayment1.disableProperty().bind(rewindMode);
        inlayButton.disableProperty().bind(rewindMode);
        contineButton.disableProperty().bind(rewindMode);
        transctionTab.disableProperty().bind(rewindMode);

        initStatusCB(statusCB);
        initStatusCB(statusCB1);

        currBalancePaymentLabel1.textProperty().bind(balance);
        currBalancePaymentLabel.textProperty().bind(balance);
        currentBalanceBuyLabel.textProperty().bind(balance);

    }

    public void bindCurrYaz(IntegerProperty currYaz) {
        this.currYaz.bind(currYaz);
    }

    @FXML
    void confirmButtonSellingLoans(ActionEvent event) {
        //todo 14/7 - check is the same
        showLoansToSellMessage.set(false);

        List<SellingLoanData> loansOnSell = new ArrayList<>();

        for (Map.Entry<SellingLoanData, CheckBox> entry : loanToSell.entrySet()) {
            if (entry.getValue().isSelected()) {
                loansOnSell.add(entry.getKey());

            }
        }
        //send to servlet the list of all the loan on sell and there price add this to the list of loan in engine ,
        //delete this loan from the selling tab , and write on the screen that loan on sell right now.
        MediaType mediaType = MediaType.parse("application/json");
        Gson gson = new Gson();

        String body = gson.toJson(new LoansOnSell(loansOnSell));

        Request request = new Request.Builder()
                .url(ADD_LOANS_TO_SELL)
                .addHeader(USER_NAME, customerName)
                .post(RequestBody.create(body.getBytes()))
                .build();

        HttpClientUtil.runAsync(request, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            loansToSellMessage.set(e.getMessage());
                            showLoansToSellMessage.set(true);
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String message = response.body().string();
                Platform.runLater(() -> {
                            //todo 14/7
                            if (response.code() != 200) {
                                loansToSellMessage.set(message);
                                showLoansToSellMessage.set(true);
                            } else {
                                loansToSellMessage.set(message);
                                showLoansToSellMessage.set(true);

                            }
                        }
                );

            }
        });

    }

    @FXML
    void confirmButtonBuyingLoans(ActionEvent event) {

       /* SellingLoansData loansToBuy = new SellingLoansData(new HashSet<>(), customerName);

        //save all the lone the customer choose to buy
        for (Map.Entry<SellingLoanData, CheckBox> entry : loanToBuy.entrySet()) {
            if (entry.getValue().isSelected())
                loansToBuy.getLoansOnSell().add(entry.getKey());
        }*/
        //send to servlet the list of all the loan on sell and there price add this to the list of loan in engine ,
        //delete this loan from the selling tab , and write on the screen that loan on sell right now.
        MediaType mediaType = MediaType.parse("application/json");
        Gson gson = new Gson();

        String body = gson.toJson(new LoansOnSell(new ArrayList<>(toBuy.values())));

        Request request = new Request.Builder()
                .url(BUY_LOANS)
                .addHeader(USER_NAME, customerName)
                .post(RequestBody.create(body.getBytes()))
                .build();

        HttpClientUtil.runAsync(request, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            errorText.set(e.getMessage());
                            showErrorMessage.set(true);
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String responseBody = response.body().string();
                Platform.runLater(() -> {

                    if (response.code() != 200) {
                        errorText.set(responseBody);
                        showErrorMessage.set(true);
                    } else {
                        showErrorMessage.set(false);


                        confirmPaymentBuyLoans.setText(responseBody);
                        String balanceFromServlet = response.header(BALANCE);
                        balance.set(balanceFromServlet);

                        toBuy.clear();
                        flowPaneShowLoansToBuy.getChildren().clear();
                        afterBuyingBalanceLabel.setText(response.header(BALANCE));
                        confirmPaymentBuyLoans.setText("Loans bought successfully");
                    }
                });
            }
        });
    }

    void chooseCustomer() {
        /*//clear all screen
       if(customersName.getValue().equals(""))
           return;
        if (!Objects.equals(customerName, customersName.getValue()) && customerName != null&&!customerName.equals("")) {
            clearScreen();
            showFinishInlayMessage.set(false);

        }
        currYaz = engine.getYaz();
        customerName = customersName.getValue();
        double balance_ = engine.getBalance(customerName);
        balance.set(Double.toString(balance_));

        choseCustomer.set(true);
        showFinishInlayMessage.set(false);
        showLoansToPayInThisYaz();

         PrintLoansToPay loans = engine.getLoansToPayInCurrYaz(customerName, currYaz);
                 int loansSize = loans.getLoansToPay().size();
                 if( loansSize ==  1 )
                     payYourLoansLabel.setText("You have " + loans.getLoansToPay().size() + " loan to pay");

                 else if(loansSize > 1)
                     payYourLoansLabel.setText("You have " + loans.getLoansToPay().size() + " loans to pay");
        showLoansToClose();
        showLoanerLoans();
        showLenderLoan();
*/
    }

    @FXML
    void chooseStatusLoner(ActionEvent event) {  //show borrow loans by status
        loanerLoansRefresher.setStatus(statusCB.getValue());
        //   showLoansByStatus("loner",statusCB,loanFlowPane);
    }

    @FXML
    void confirmCharge(ActionEvent event) {
        if (validUserInputNumber(enterAmountCM.getCharacters().toString(), 0, 0)) {
            String amount = enterAmountCM.getCharacters().toString();

            String finalUrl = HttpUrl
                    .parse(Constants.CHARGE_OR_WITHDRAW)
                    .newBuilder()
                    .addQueryParameter("customer", customerName)
                    .addQueryParameter("charge_or_withdraw", "charge")
                    .addQueryParameter("amount", amount)
                    .build()
                    .toString();

            HttpClientUtil.runAsync(finalUrl, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Platform.runLater(() ->
                            chargeProperty.set("Something went wrong, charge failed")
                    );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                    String responseBody = response.body().string();

                    if (response.code() != 200) {
                        Platform.runLater(() ->
                                chargeProperty.set("Something went wrong, charge failed")
                        );
                    } else {
                        Platform.runLater(() -> {

                            balance.set(responseBody);
                            chargeProperty.set("Charge succeeded");


                        });
                    }
                }
            });

        } else
            chargeProperty.set("wrong input, try again");

        enterAmountCM.setText("");
    }

    @FXML
    void confirmWithdraw(ActionEvent event) {
        int money = (int) Double.parseDouble(accountBalance.textProperty().get());


        if (validUserInputNumber(enterAmountWM.getCharacters().toString(), 1, money)) {
            if (money - Integer.parseInt(enterAmountWM.getCharacters().toString()) >= 0) {
                String amount = enterAmountWM.getCharacters().toString();

                String finalUrl = HttpUrl
                        .parse(Constants.CHARGE_OR_WITHDRAW)
                        .newBuilder()
                        .addQueryParameter("customer", customerName)
                        .addQueryParameter("charge_or_withdraw", "withdraw")
                        .addQueryParameter("amount", amount)
                        .build()
                        .toString();

                HttpClientUtil.runAsync(finalUrl, new Callback() {

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Platform.runLater(() ->
                                withdrawProperty.set("Something went wrong, charge failed")
                        );
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        String responseBody = response.body().string();
                        if (response.code() != 200) {
                            Platform.runLater(() ->
                                    withdrawProperty.set("Something went wrong, withdraw failed")
                            );
                        } else {
                            Platform.runLater(() -> {
                                balance.set(responseBody);
                                withdrawProperty.set("withdraw succeeded");
                            });
                        }
                    }
                });
            }

            //engine.withdrawMoneyFromAccount(customersName.getValue(), Integer.parseInt(enterAmountWM.getCharacters().toString()));
            //balance.set(Double.toString(engine.getBalance(customersName.getValue())));
        } else {
            withdrawProperty.set("Wrong input, Withdraw failed!");
        }

//     else
//
//    {
//        withdrawProperty.set("Wrong input, Withdraw failed!");
//    }

        enterAmountWM.setText("");
    }


    public void setCustomer(String customerName) {
        this.customerName = customerName;
    }

    @FXML
    public void initAmountCTextArea(MouseEvent mouseEvent) {
        chargeProperty.set("");
    }

    @FXML
    void chooseStatusLender(ActionEvent event) {
        lenderLoansRefresher.setStatus(statusCB1.getValue());
        // showLoansByStatus("lender",statusCB1,loanFlowPane1);
    }

    @FXML
    void continueButton(ActionEvent event) throws CloneNotSupportedException {
        loanToInvestFlowPane.getChildren().clear();
        showYazMessage.set(false);
        showInterestMessage.set(false);
        showAmountMessage.set(false);
        showMaxLoanMessage.set(false);
        showMaxPrecentMessage.set(false);
        showFinishInlayMessage.set(false);


        boolean validInvest = checkAmount();
        boolean validInterest = checkInterest(intrestTF);
        boolean validMinYaz = checkMinYAz();
        boolean validMaxLoanBorrow = checkMaxLoan();
        boolean validPrecent = checkPrecentToInvest();
        List<String> categories = new ArrayList<>();

        if (categoryCB.getCheckModel().getCheckedItems().size() == 0)
            categories.add("All Categories");
        else
            categories = categoryCB.getCheckModel().getCheckedItems();


        if (validInvest && validInterest && validMinYaz && validMaxLoanBorrow && validPrecent) {
            /**Ask from the servlet the relvent Loans**/
            getRelventLoansFromServlet(categories);
        } else {
            /**show message of wat not good with the parmeter the client enter**/
            setTextMassage(validInvest, validInterest, validMinYaz, validMaxLoanBorrow, validPrecent);

        }
        showInlayButton.set(true);
    }

    @FXML
    public void initAmountWTextArea(MouseEvent mouseEvent) {
        withdrawProperty.set("");
    }

    /*  @FXML
      public void inlay(ActionEvent event) throws CloneNotSupportedException {
          List<String> loansID = new ArrayList<>();
          for (Map.Entry<String, CheckBox> entry : investLoans.entrySet()) {
              if (entry.getValue().isSelected())
                  loansID.add(entry.getKey());
          }
          if(loansID.size()>0) {
              int left = engine.inlay(loansID, Integer.parseInt(amountTF.getCharacters().toString()), customerName, relventLoans, getInputTF(maxPrecentTF.getCharacters().toString()));
             // finishInlayMessage.set("For the loans you invested, only took " + (Integer.parseInt(amountTF.getCharacters().toString()) - left) + "\n so you've got another " + left + " left that hasn't been invested.");
            //  balance.set(Double.toString(engine.getBalance(customerName)));
              showFinishInlayMessage.set(true);
              investLoans.clear();
              clearScreen();
             // showLoanerLoans();
             // showLenderLoan();
              showLoansToPayInThisYaz();
              showLoansToClose();
              showInlayButton.set(false);
          }
      }*/
    @FXML
    public void inlay(ActionEvent event) {
        List<String> loansId = new ArrayList<>();
        // get check loans --> send to engine.inlay...
        for (Map.Entry<String, CheckBox> entry : investLoans.entrySet()) {
            if (entry.getValue().isSelected())
                loansId.add(entry.getKey());
        }
        if (loansId.size() > 0) {
            InlayData data = new InlayData(loansId, Integer.parseInt(amountTF.getCharacters().toString()), customerName, relventLoans, getInputTF(maxPrecentTF.getCharacters().toString()));

            MediaType mediaType = MediaType.parse("application/json");
            Gson gson = new Gson();
            String body = gson.toJson(data);

            Request request = new Request.Builder()
                    .url(Constants.INLAY)
                    .post(RequestBody.create(body.getBytes()))
                    .build();

            HttpClientUtil.runAsync(request, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Platform.runLater(() ->
                            finishInlayMessage.set(e.getMessage())
                    );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseBody = response.body().string();

                    if (response.code() != 200) {
                        finishInlayMessage.set(responseBody);
                    } else {
                        Platform.runLater(() -> {

                            String left = response.header("left");
                            String balanceFromServlet = response.header("balance");

                            finishInlayMessage.set("For the loans you invested, only took " + (Integer.parseInt(amountTF.getCharacters().toString()) - Integer.parseInt(left)) + "\n so you've got another " + left + " left that hasn't been invested.");
                            balance.set(balanceFromServlet);
                            showFinishInlayMessage.set(true);
                            investLoans.clear();
                            clearScreen();
                            // showLoanerLoans();
                            // showLenderLoan();
                            //showLoansToPayInThisYaz();
                            //showLoansToClose();
                            showInlayButton.set(false);
                            inlayWas = true;

                        });
                    }
                }

            });
        }

    }


    /*@FXML
    public void showLastTransactions(MouseEvent mouseEvent) {

        //    PrintTransactions transactions = engine.getAllTransactions(customerName);
          //  popOverTransactions = new PopOver(createLastTransactions(transactions));

            popOverTransactions.show(lastTransactions);

    }*/
    @FXML
    public void confirmButtonClicked(ActionEvent event) {
        if (loansToClose != null) {

            MediaType mediaType = MediaType.parse("application/json");
            Gson gson = new Gson();
            PrintLoansToPay send = new PrintLoansToPay(new ArrayList<>(loansToClose.values()), customerName, currYaz.get());
            String body = gson.toJson(send);

            Request request = new Request.Builder()
                    .url(CLOSE_LOANS)
                    .post(RequestBody.create(body.getBytes()))
                    .build();

            HttpClientUtil.runAsync(request, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Platform.runLater(() -> {
                                //todo 14/7
                                errorText.set(e.getMessage());
                                showErrorMessage.set(true);
                            }
                    );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    //todo 14/7
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        if (response.code() != 200) {
                            errorText.set(responseBody);
                            showErrorMessage.set(true);
                        } else {
                            showErrorMessage.set(false);
                            String balanceFromServlet = response.header(BALANCE);
                            balance.set(balanceFromServlet);
                            loansToClose.clear();
                            paymentCloseALoan.getChildren().clear();
                            showLoansToClose();

                            if (loansToPay != null) {
                                loansToPay.clear();
                                paymentPayInThisCurrentYaz.getChildren().clear();
                            }
                            balanceAfterLabel.setText(currBalancePaymentLabel.getText());

                        }
                    });
                }

            });


        }

    }

    @FXML
    public void confirmButtonClicked1(ActionEvent event) {

        if (loansToPay != null) {

            MediaType mediaType = MediaType.parse("application/json");
            Gson gson = new Gson();
            PrintLoansToPay send = new PrintLoansToPay(new ArrayList<>(loansToPay.values()), customerName, currYaz.get());
            String body = gson.toJson(send);

            Request request = new Request.Builder()
                    .url(PAY_LOANS)
                    .post(RequestBody.create(body.getBytes()))
                    .build();

            HttpClientUtil.runAsync(request, new Callback() {

                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Platform.runLater(() -> {
                                //todo 14/7
                                errorText.set(e.getMessage());
                                showErrorMessage.set(true);
                            }
                    );
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseBody = response.body().string();
                    Platform.runLater(() -> {
                        if (response.code() != 200) {
                            errorText.set(responseBody);
                            showErrorMessage.set(true);
                        } else {
                            showErrorMessage.set(false);
                            String balanceFromServlet = response.header(BALANCE);
                            balance.set(balanceFromServlet);
                            balanceAfterLabel1.setText(balance.getValue());
                            loansToPay.clear();

                            paymentPayInThisCurrentYaz.getChildren().clear();
                            showLoansToPayInThisYaz();
                            payYourLoansLabel.setText("");


                            if (loansToClose != null) {
                                loansToClose.clear();
                                paymentCloseALoan.getChildren().clear();
                            }
                            balanceAfterLabel1.setText(currBalancePaymentLabel1.getText());
                        }
                    });

                }
            });

        }
    }

    /********************************************************************************************************************/
    private int getInputTF(String input) {
        if (input.equals(""))
            return 0;
        else
            return Integer.parseInt(input);
    }

    public void setChooseCustomer(boolean newCustomer) {
        /*customersName.setValue("");*/
        choseCustomer.set(newCustomer);
    }

    public void clearScreen() {
        amountTF.clear();
        minYazTF.clear();
        intrestTF.clear();
        categoryCB.getCheckModel().clearChecks();
        maxPrecentTF.clear();
        maxLoanTF.clear();
        loanToInvestFlowPane.getChildren().clear();
        statusCB.setValue("All");
        statusCB1.setValue("All");
        paymentPayInThisCurrentYaz.getChildren().clear();
        paymentCloseALoan.getChildren().clear();
        showAmountMessage.set(false);
        showInterestMessage.set(false);
        showYazMessage.set(false);
        showMaxLoanMessage.set(false);
        showMaxPrecentMessage.set(false);
        withdrawProperty.set("");
        enterAmountWM.clear();
        chargeProperty.set("");
        enterAmountCM.clear();


    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // public void setModel(Engine engine) {
    //    this.engine = engine;
    //  }

    public void setComponents(Set<String> categories, PrintCustomer customer) {
        setBalance(customer);
        //setChooseCustomer(true);
//        initStatusCB(statusCB);
//        initStatusCB(statusCB1);
        initCategoriesCB(categories);
        initCategoriesCB1(categories);

    }

    public void setFileInTheSystem(boolean isFileInSystem) {
        fileInTheSystem.set(isFileInSystem);
    }

    private void setBalance(PrintCustomer customer) {
        double balance_ = customer.getBalance();
        balance.set(Double.toString(balance_));
    }

    private void addLoans(PrintLoans loans, FlowPane loanFlowPane) {

        for (PrintLoan loan : loans.getLoans()) {
            createLoanTile(loan, loanFlowPane);
        }

    }

    private void createLoanTile(PrintLoan loan, FlowPane loanFlowPane) {
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

    private void noLoansInThisStatus(FlowPane loanFlowPane, String message) {
        if (loanFlowPane.getChildren().size() > 0) {
            loanFlowPane.getChildren().clear();
        }
        Label noLoans = new Label(message);
        loanFlowPane.alignmentProperty().set(Pos.CENTER);
        loanFlowPane.getChildren().add(noLoans);

    }

    private void showLoanToBuy(LoansOnSell loans, String type) {


        if (loans != null) {

            if (loanChange(loans, type) || loans.getLoans().size() == 0 || balanceCountBuyLoans != Double.parseDouble(currentBalanceBuyLabel.getText())) {
                if (flowPaneShowLoansToBuy.getChildren().size() > 0) {
                    flowPaneShowLoansToBuy.getChildren().clear();
                }

                flowPaneShowLoansToBuy.alignmentProperty().set(Pos.TOP_LEFT);
                afterBuyingBalanceLabel.setText(currentBalanceBuyLabel.getText());

                balanceCountBuyLoans = Double.parseDouble(currentBalanceBuyLabel.getText());

                if (loans.getLoans().size() == 0) {
                    noLoansInThisStatus("No loans to buy at this moment");
                } else {
                    addLoansToBuy(loans);
                }
                oldLoansToBuy = loans;
            }
        } else {
            noLoansInThisStatus("No loans to buy in this moment");
        }

    }

    private void addLoansToBuy(LoansOnSell loans) {

        toBuy = new HashMap<>();

        for (SellingLoanData loan : loans.getLoans()) {
            createLoanToBuyBoxTile(loan);
        }
    }

    private void createLoanToBuyBoxTile(SellingLoanData loan) {

        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/buyingLoanBox/buyingLoanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            buyingLoanController = loader.getController();

            flowPaneShowLoansToBuy.getChildren().add(singleLoanTile);

            buyingLoanController.setInformation(loan, afterBuyingBalanceLabel, toBuy);
            buyingLoanController.bindSelected(rewindMode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void noLoansInThisStatus(String message) {
        Label noLoans = new Label(message);
        flowPaneShowLoansToBuy.alignmentProperty().set(Pos.CENTER);
        flowPaneShowLoansToBuy.getChildren().add(noLoans);

    }

    public boolean validUserInputNumber(String input, int sInd, int eInd) {
        int ind;
        int charSize = Integer.toString(eInd).length();

        String len = Integer.toString(eInd);

        if (input.matches("[0-9]+")) { // can't be a negative number, so no check for '-' sign

            // sign for no limit in number
            if (charSize < input.length() && sInd != eInd)
                return false;

            ind = Integer.parseInt(input);

            if (!(ind >= sInd && ind <= eInd) && sInd != eInd)
                return false;


            return !input.startsWith("0") || sInd != eInd;

        } else {
            return false;
        }
    }

    private void initStatusCB(ComboBox<String> statusComboBox) {
        statusComboBox.getItems().clear();
        ObservableList<String> list = statusComboBox.getItems();
        statusComboBox.setPromptText("All");
        statusComboBox.setValue("All");
        //Adding items to the combo box
        list.add("New");
        list.add("Pending");
        list.add("Active");
        list.add("In Risk");
        list.add("Finished");
        list.add("All");

    }

    public void showLoans(PrintLoans loans, FlowPane loanFlowPane) {
        loanFlowPane.alignmentProperty().set(Pos.TOP_LEFT);

        if (loanFlowPane.getChildren().size() > 0)
            loanFlowPane.getChildren().clear();

        if (loans.getLoans().size() == 0) {
            noLoansInThisStatus(loanFlowPane, "There are no loans in this status.");
        } else {
            addLoans(loans, loanFlowPane);
        }
    }

    private void showLoansByStatus(String typeLoans, ComboBox<String> comboBox, FlowPane loanFlowPane) {

        String finalUrl = HttpUrl
                .parse(Constants.GET_LOANS)
                .newBuilder()
                .addQueryParameter("userName", customerName)
                .addQueryParameter("status", comboBox.getValue())
                .addQueryParameter("typeLoans", typeLoans)
                .build()
                .toString();


        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            Label noLoans = new Label(e.getMessage());
                            loanFlowPane.alignmentProperty().set(Pos.CENTER);
                            loanFlowPane.getChildren().add(noLoans);
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //todo 14/7
                String responseBody = response.body().string();

                Platform.runLater(() -> {
                    if (response.code() != 200) {
                        Label noLoans = new Label(responseBody);
                        loanFlowPane.alignmentProperty().set(Pos.CENTER);
                        loanFlowPane.getChildren().add(noLoans);
                    } else {
                        // loadFileMessage.set(responseBody);
                        PrintLoans res = GSON_INSTANCE.fromJson(responseBody, PrintLoans.class);
                        showLoans(res, loanFlowPane);
                    }
                });
            }
        });
    }

    private boolean checkAmount() {
        if (amountTF.getCharacters().length() == 0) {
            amountMessage.set("You have to enter amount to invest.");
            return false;
        } else {
            amountMessage.set("your amount to invest os off your limit");
            Double newValue = new Double(balance.get());
            if (newValue == 0)
                return false;
            else {
                return validUserInputNumber(amountTF.getCharacters().toString(), 0, newValue.intValue());
            }
        }
    }

    private boolean checkInterest(TextField interestToCheck) {
        if (interestToCheck.getCharacters().length() == 0) {
            return true;
        } else
            return validUserInputNumber(interestToCheck.getCharacters().toString(), 0, 100);

    }

    private boolean checkMinYAz() {
        if (minYazTF.getCharacters().length() == 0) {
            return true;
        } else
            return validUserInputNumber(minYazTF.getCharacters().toString(), 0, 0);
    }

    private boolean checkMaxLoan() {
        if (maxLoanTF.getCharacters().length() == 0) {
            return true;
        } else
            return validUserInputNumber(maxLoanTF.getCharacters().toString(), 0, 0);
    }

    private boolean checkPrecentToInvest() {
        if (maxPrecentTF.getCharacters().length() == 0) {
            return true;
        } else
            return validUserInputNumber(maxPrecentTF.getCharacters().toString(), 1, 100);
    }

    private void setTextMassage(boolean validAmount, boolean validInterest, boolean validMinYaz, boolean validMaxLoanBorrow, boolean validPrecent) {
        if (!validAmount)
            showAmountMessage.set(true);
        if (!validInterest)
            showInterestMessage.set(true);
        if (!validMinYaz)
            showYazMessage.set(true);

    }

    private void initCategoriesCB(Set<String> categories) {
        categoryCB.getItems().clear();
        ObservableList<String> list = categoryCB.getItems();
        list.addAll(categories);
        list.add("All Categories");

        if (categories.size() > 0)
            areCategoriesInSystem.set(true);
    }


    private void createLoanTile(PrintLoan loan) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/LoanBox/loanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            LoanBoxController loanBoxControllerController = loader.getController();
            loanBoxControllerController.initLoanData(loan);
            CheckBox checkLoan = new CheckBox();
            loanToInvestFlowPane.getChildren().add(singleLoanTile);
            loanToInvestFlowPane.getChildren().add(checkLoan);
            investLoans.put(loan.getId(), checkLoan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ScrollPane createTransactionBoxTile(PrintTransaction singleTransaction) {

        ScrollPane singleTransactionTile;

        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/LastTransactionsBox/LastTransactionBox.fxml");
            loader.setLocation(mainFXML);
            singleTransactionTile = loader.load();

            lastTransactionBoxController = loader.getController();
            lastTransactionBoxController.setInformation(singleTransaction);

            return singleTransactionTile;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void showLoansToPayInThisYaz() {

        paymentPayInThisCurrentYaz.alignmentProperty().set(Pos.TOP_LEFT);

        balanceAfterLabel1.setText(currBalancePaymentLabel1.getText());

        if (paymentPayInThisCurrentYaz.getChildren().size() > 0) {
            paymentPayInThisCurrentYaz.getChildren().clear();
            noLoansToPay.textProperty().set("");
        }

        String mode;
        if (rewindMode.get())
            mode = ON;
        else
            mode = OFF;

        String finalUrl = HttpUrl
                .parse(GET_LOANS_PAY_IN_THIS_YAZ)
                .newBuilder()
                .addQueryParameter(USER_NAME, customerName)
                .addQueryParameter(REWIND_MODE, mode)
                .addQueryParameter(CURR_YAZ, Integer.toString(currYaz.get()))
                .build()
                .toString();


        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            //todo 14/7
                            errorText.set(e.getMessage());
                            showErrorMessage.set(true);
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //todo 14/7
                String responseBody = response.body().string();
                Platform.runLater(() -> {
                    if (response.code() != 200) {
                        errorText.set(responseBody);
                        showErrorMessage.set(true);
                    } else {
                        showErrorMessage.set(false);

                        // loadFileMessage.set(responseBody);
                        PrintLoansToPay res = GSON_INSTANCE.fromJson(responseBody, PrintLoansToPay.class);

                        if (res.getLoansToPay().size() == 0) {

                            noLoansToPay(true);
                            messageLoansToPay.clear();
                            if (res.getLoansToPay().size() == 0) {
                                noLoansToPay(true);
                                messageLoansToPay.clear();
                                if (rewindMode.get()) {
                                    messageLoansToPay.clear();
                                    for (String s : messagesInRewindMode.get(currYaz.get()))
                                        messageLoansToPay.appendText(s);
                                } else {
                                    messageLoansToPay.appendText("No loans to pay at this moment");
                                    if (messagesInRewindMode.containsKey(currYaz.get()))
                                        messagesInRewindMode.get(currYaz.get()).add("No loans to pay at this moment");
                                    else {
                                        List<String> temp = new ArrayList<>();
                                        temp.add("No loans to pay at this moment");
                                        messagesInRewindMode.put(currYaz.get(), temp);
                                    }
                                }
                                messageLoansToPay.deselect();
                            }

                        }
                        else {
                            addLoansToPayInThisCurrentYaz(res);
                        }
                    }
                });
            }
        });
    }

    private void noLoansToPay(boolean thisTime) {

        if (thisTime) {
            if (paymentPayInThisCurrentYaz.getChildren().size() > 0) {
                paymentPayInThisCurrentYaz.getChildren().clear();
                noLoansToPay.textProperty().set("");
            }
            noLoansToPay = new Label("There are no loans to pay at this yaz time.");
            paymentPayInThisCurrentYaz.alignmentProperty().set(Pos.CENTER);
            paymentPayInThisCurrentYaz.getChildren().add(noLoansToPay);
        } else {
            if (paymentCloseALoan.getChildren().size() > 0) {
                paymentCloseALoan.getChildren().clear();
                noLoansToPay.textProperty().set("");
            }
            noLoansToClose = new Label("There are no loans to close.");
            paymentCloseALoan.alignmentProperty().set(Pos.CENTER);
            paymentCloseALoan.getChildren().add(noLoansToClose);
        }

    }

    private void addLoansToPayInThisCurrentYaz(PrintLoansToPay loans) {

        loansToPay = new HashMap<>();
        messageLoansToPay.clear();

        for (PrintLoanToPay loan : loans.getLoansToPay()) {
            String mess = "Pay loan id: " + loan.getId().toString();


            if(rewindMode.get())
            {
                messageLoansToPay.clear();
                for(String s : messagesInRewindMode.get(currYaz.get()))
                    messageLoansToPay.appendText(s);
            }
            else {
                messageLoansToPay.appendText(mess);

                if (messagesInRewindMode.containsKey(currYaz.get()))
                    messagesInRewindMode.get(mess);
                else {

                    List<String> temp = new ArrayList<>();
                    temp.add(mess);
                    messagesInRewindMode.put(currYaz.get(), temp);
                }
            }

            createPaymentBoxTile(loan, true);
        }
        messageLoansToPay.deselect();


    }

    private void createPaymentBoxTile(PrintLoanToPay loan, boolean thisTime) {
        ScrollPane singlePaymentBoxTile;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/PaymentBox/PaymentBox.fxml");
            loader.setLocation(mainFXML);
            singlePaymentBoxTile = loader.load();

            paymentBoxController = loader.getController();

            if (thisTime) {
                paymentPayInThisCurrentYaz.getChildren().add(singlePaymentBoxTile);
                paymentBoxController.setInformation(loan, balanceAfterLabel, balanceAfterLabel1, loansToPay, loansToClose, true);
                paymentBoxController.bindSelected(rewindMode);
            } else {
                paymentCloseALoan.getChildren().add(singlePaymentBoxTile);
                paymentBoxController.setInformation(loan, balanceAfterLabel, balanceAfterLabel1, loansToPay, loansToClose, false);
                paymentBoxController.bindSelected(rewindMode);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showLoansToClose() {

        paymentCloseALoan.alignmentProperty().set(Pos.TOP_LEFT);

        if (paymentCloseALoan.getChildren().size() > 0) {
            paymentCloseALoan.getChildren().clear();
            noLoansToPay.textProperty().set("");
        }

        String mode;
        balanceAfterLabel.setText(currBalancePaymentLabel.getText());

        if (rewindMode.get())
            mode = ON;
        else
            mode = OFF;

        String finalUrl = HttpUrl
                .parse(Constants.GET_LOANS_TO_CLOSE)
                .newBuilder()
                .addQueryParameter(USER_NAME, customerName)
                .addQueryParameter(REWIND_MODE, mode)
                .addQueryParameter(CURR_YAZ, Integer.toString(currYaz.get()))
                .build()
                .toString();


        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            //todo 14/7
                            errorText.set(e.getMessage());
                            showErrorMessage.set(true);
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //todo 14/7
                String responseBody = response.body().string();
                Platform.runLater(() -> {
                    if (response.code() != 200) {
                        errorText.set(responseBody);
                        showErrorMessage.set(true);
                    } else {
                        showErrorMessage.set(false);
                        // loadFileMessage.set(responseBody);
                        PrintLoansToPay res = GSON_INSTANCE.fromJson(responseBody, PrintLoansToPay.class);

                        if (res.getLoansToPay().size() == 0) {
                            noLoansToPay(false);
                        } else {
                            addLoansToClose(res);
                        }
                    }
                });
            }
        });

    }

    public void bindRewindMode(SimpleBooleanProperty rewindMode) {
        this.rewindMode.bind(rewindMode);
    }

    private void addLoansToClose(PrintLoansToPay loans) {

        loansToClose = new HashMap<>();

        for (PrintLoanToPay loan : loans.getLoansToPay()) {
            createPaymentBoxTile(loan, false);
        }
    }

    private void getRelventLoansFromServlet(List<String> categories) {
        //engine.getOptionalLoansForInvestment(customerName, Integer.parseInt(amountTF.getCharacters().toString()), categories, getInputTF(intrestTF.getCharacters().toString()), getInputTF((minYazTF.getCharacters().toString())), getInputTF(maxLoanTF.getCharacters().toString()));

        RelventLoansData relventLoansData = new RelventLoansData(customerName, Integer.parseInt(amountTF.getCharacters().toString()), categories, getInputTF(intrestTF.getCharacters().toString()), getInputTF((minYazTF.getCharacters().toString())), getInputTF(maxLoanTF.getCharacters().toString()));
        MediaType mediaType = MediaType.parse("application/json");
        Gson gson = new Gson();
        String body = gson.toJson(relventLoansData);

        Request request = new Request.Builder()
                .url(Constants.GET_RELVENT_LOANS)
                .post(RequestBody.create(body.getBytes()))
                .build();

        HttpClientUtil.runAsync(request, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            //todo 14/7
                            errorText.set(e.getMessage());
                            showErrorMessage.set(true);
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //todo 14/7
                String responseBody = response.body().string();

                Platform.runLater(() -> {
                    if (response.code() != 200) {
                        errorText.set(responseBody);
                        showErrorMessage.set(true);
                    } else {
                        showErrorMessage.set(false);
                        // loadFileMessage.set(responseBody);
                        PrintLoans loans = GSON_INSTANCE.fromJson(responseBody, PrintLoans.class);
                        showLoanForInlay(loans);
                    }
                });
            }
        });
    }

    private void showLoanForInlay(PrintLoans loans) {

        if (loanFlowPane.getChildren().size() > 0) {
            loanFlowPane.getChildren().clear();
        }

        loanFlowPane.alignmentProperty().set(Pos.TOP_LEFT);

        if (loans.getLoans().size() == 0) {
            noLoansInThisStatus(loanToInvestFlowPane, "No loans to invest with this selected parameters");

        } else {
            for (PrintLoan loan : loans.getLoans())
                createLoanTile(loan);
        }
        relventLoans = loans;
    }

    public void setActive() {
        startLastTransactionsRefresher();
        startLonerLoansRefresher();
        startLenderLoansRefresher();
        startLoanToSellRefresher();
        startLoanToBuyRefresher();
        //categoriesRefresher
        startCategoriesRefresher();
    }

    public void startLonerLoansRefresher() {

        loanerLoansRefresher = new LoansRefresher(
                this::updateLonerLoans,
                this::onFail,
                statusCB.getValue(),
                customerName,
                "loner", rewindMode, currYaz);
        loansTimer = new Timer();
        loansTimer.schedule(loanerLoansRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void onFail(String message) {
        Platform.runLater(() -> {
            errorText.set(message);
            showErrorMessage.set(true);
        });
    }

    private void updateLonerLoans(PrintLoans loans) {

        Platform.runLater(() -> {
            showErrorMessage.set(false);
            showLoans(loans, loanFlowPane);
        });

    }

    public void startCategoriesRefresher() {

        customerAndFileRefresher = new CategoriesAndFileStatusRefresher(
                this::updateCategories,
                this::onFail);
        customerAndFileRefresherTimer = new Timer();
        customerAndFileRefresherTimer.schedule(customerAndFileRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateCategories(CategoriesAndFileStatus res) {

        Platform.runLater(() -> {
            //setFile in system
            //check the size of the category is bigger if yes so change all- like sell /buy loans
            showErrorMessage.set(false);
            fileInTheSystem.set(res.isFileInSystem());
            setCategoriesCb(res.getCategories());
        });

    }

    private void setCategoriesCb(Set<String> categories) {
        if (categories != null) {
            if (categoriesChange(categories)) {
                initCategoriesCB1(categories);
                initCategoriesCB(categories);
            }
        }
        oldCategories = categories;
    }

    private boolean categoriesChange(Set<String> newCategories) {
        if (newCategories.size() != 0) {
            return oldCategories.size() < newCategories.size();
        }
        return false;
    }

    public void startLoanToSellRefresher() {
        //todo 14/7
        loanToSellRefresher = new LoanToSellRefresher(
                customerName,
                this::updateLoansToSell,
                this::onFail,
                rewindMode,
                currYaz);
        loansTimer = new Timer();
        loansTimer.schedule(loanToSellRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateLoansToSell(LoansOnSell loans) {

        Platform.runLater(() -> {
            showLoanToSellOrBuy(loans, flowPaneShowLoansToSell, "Sell");
        });

    }

    public void startLoanToBuyRefresher() {
        loanToBuyRefresher = new LoanToBuyRefresher(
                customerName,
                this::updateLoansToBuy,
                this::onFail,
                rewindMode,
                currYaz);
        loansTimer = new Timer();
        loansTimer.schedule(loanToBuyRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateLoansToBuy(LoansOnSell loans) {

        Platform.runLater(() ->
            showLoanToBuy(loans, "Buy")
        );

    }

    public void startLenderLoansRefresher() {
        lenderLoansRefresher = new LoansRefresher(
                this::updateLenderLoans,
                this::onFail,
                statusCB.getValue(),
                customerName,
                "lender",
                rewindMode,
                currYaz);
        loansTimer = new Timer();
        loansTimer.schedule(lenderLoansRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateLenderLoans(PrintLoans loans) {
        Platform.runLater(() -> {
            showErrorMessage.set(false);
            showLoans(loans, loanFlowPane1);
        });

    }

    public void startLastTransactionsRefresher() {
        printTransactionsRefresher = new LastTransactionsRefresher(
                this::updateLastTransactions,
                this::onFail,
                customerName,
                rewindMode,
                currYaz);
        transactionsTimer = new Timer();
        transactionsTimer.schedule(printTransactionsRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void updateLastTransactions(PrintTransactionsAndBalance lastTransactions) {

        Platform.runLater(() -> {
            showErrorMessage.set(false);
            if (lastTransactionsFlowPane.getChildren().size() > 0)
                lastTransactionsFlowPane.getChildren().clear();
            if (lastTransactions != null) {


                lastTransactionsFlowPane.getChildren().addAll(getAllUpdatedTransactions(lastTransactions.getPrintTransactions()));
                totalTransactions.set(lastTransactions.getPrintTransactions().getTransactions().size());
                // if(rewindMode.get())

                balance.set(Double.toString(lastTransactions.getBalance()));
                if (lastTransactions.getPrintTransactions().getTransactions().size() > countTransactions) {
                    countTransactions++;
                    if (loansToPay != null) {
                        loansToPay.clear();
                        paymentPayInThisCurrentYaz.getChildren().clear();
                        showLoansToPayInThisYaz();
                    } else {
                        paymentPayInThisCurrentYaz.getChildren().clear();
                        showLoansToPayInThisYaz();

                    }
                    if (loansToClose != null) {
                        loansToClose.clear();
                        paymentCloseALoan.getChildren().clear();
                        showLoansToClose();
                    } else {
                        paymentCloseALoan.getChildren().clear();
                        showLoansToClose();
                    }

                }
            }
        });

    }

    private List<ScrollPane> getAllUpdatedTransactions(PrintTransactions customerTransactions) {
        List<ScrollPane> newTrans = new ArrayList<>();

        for (PrintTransaction transaction : customerTransactions.getTransactions()) {
            newTrans.add(createTransactionBoxTile(transaction));
        }
        return newTrans;
    }

    @FXML
    void addLoan(ActionEvent event) {
        /**Check the information the customer enter is ok ,
         * ID of the loan and category will be check in the servlet,
         * because it needs the information about all the loan and categories in our system.*/
        showPayYazErrorMessage.set(false);
        showTotalYazErrorMessage.set(false);
        showCapitalErrorMessage.set(false);
        showIdErrorMessage.set(false);
        showFinishAddLoanMessage.set(false);
        showCategoryErrorMessage.set(false);
        //check all text filed are fill
        if (checkTexFiledFull()) {
            finishMessage.set("You have to fill all parameters");
            showFinishAddLoanMessage.set(true);
        }
        //Check the information the client enter is a whole number
        else {
            boolean checkCapital = checkWholeNumber(capitalTF.getCharacters().toString());
            boolean checkTotalYaz = checkWholeNumber(totalYazTimeTF.getCharacters().toString());
            boolean checkPaysEveryYaz = checkWholeNumber(paysEvreyYazTF.getCharacters().toString());
            boolean checkCategoryIsCheck = checkOneCategoryIsCheck();

            //Check the interest is valid number
            boolean checkInterest = checkInterest(interestPerPaymentTF);

            //Check the rate of payments is fully divided by the total length of the loan.
            boolean checkRateOfPayments = false;
            if (checkPaysEveryYaz && checkTotalYaz)
                checkRateOfPayments = checkRateOfPayments();
            if (!(checkCapital && checkTotalYaz && checkPaysEveryYaz && checkInterest && checkRateOfPayments) || checkCategoryIsCheck) {    /** add methode that show message that something not correct*/
                setTextErrorMassage(checkCapital, checkTotalYaz, checkPaysEveryYaz, checkInterest, checkRateOfPayments, checkCategoryIsCheck);
            } else {
                //send the request add a new loan to the servlet
                addLoanToCustomer();
            }
            //clear all text filed
            clearTextFill();
        }
    }

    private void clearTextFill() {
        idTF.clear();
        //categoryTF.clear(); move to set this after add xml
        capitalTF.clear();
        totalYazTimeTF.clear();
        paysEvreyYazTF.clear();
        interestPerPaymentTF.clear();
        categoryCB1.setValue("");
    }

    private void initCategoriesCB1(Set<String> categories) {
        categoryCB1.getItems().clear();
        ObservableList<String> list = categoryCB1.getItems();
        list.addAll(categories);
    }

    private boolean checkTexFiledFull() {
        return (idTF.getCharacters().length() == 0 || checkOneCategoryIsCheck() ||
                capitalTF.getCharacters().length() == 0 || totalYazTimeTF.getCharacters().length() == 0 ||
                paysEvreyYazTF.getCharacters().length() == 0 || interestPerPaymentTF.getCharacters().length() == 0
        );

    }

    private boolean checkOneCategoryIsCheck() {
        return categoryCB1.getValue() == null;
    }

    private boolean checkWholeNumber(String number) {
        //check is a number

        if (validUserInputNumber(number, 1, Integer.MAX_VALUE)) {
            Double newNumber = new Double(number);

            if (newNumber % 1 == 0)
                return true;
        }
        return false;

    }

    boolean checkRateOfPayments() {

        Integer totalYazTime = new Integer(totalYazTimeTF.getCharacters().toString());
        Integer paysEveryYaz = new Integer(paysEvreyYazTF.getCharacters().toString());

        return (totalYazTime % paysEveryYaz) == 0;
    }

    private void addLoanToCustomer() {
        String loanId = idTF.getCharacters().toString();
        String category = categoryCB1.getValue();
        String capital = capitalTF.getCharacters().toString();
        String totalYazTime = totalYazTimeTF.getCharacters().toString();
        String paysEveryYaz = paysEvreyYazTF.getCharacters().toString();
        String interest = interestPerPaymentTF.getCharacters().toString();

        AddLoanData newLoanData = new AddLoanData(customerName, loanId, category, capital, totalYazTime, paysEveryYaz, interest);

        MediaType mediaType = MediaType.parse("application/json");
        Gson gson = new Gson();
        String body = gson.toJson(newLoanData);

        Request request = new Request.Builder()
                .url(Constants.ADD_LOAN)
                .post(RequestBody.create(body.getBytes()))
                .build();

        HttpClientUtil.runAsync(request, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    finishMessage.set(e.getMessage());
                    showFinishAddLoanMessage.set(true);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                String header = response.header(EXCEPTION_TYPE);
                Platform.runLater(() -> {
                    if (response.code() != 200) {
                        Platform.runLater(() -> {
                                    if (header.equals("Loan"))
                                        showIdErrorMessage.set(true);
                                    else if (header.equals("Payment")) {
                                        showTotalYazErrorMessage.set(true);
                                        showPayYazErrorMessage.set(true);
                                    } else {
                                        finishMessage.set(responseBody);
                                        showFinishAddLoanMessage.set(true);
                                    }
                                }
                        );
                    } else {
                        System.out.println(responseBody);
                        Platform.runLater(() -> {
                            finishMessage.set("Loan add successfully!");
                            showFinishAddLoanMessage.set(true);
                            categoryCB1.setValue("");
                        });
                    }
                });
            }
        });
    }

    private void setTextErrorMassage(boolean validCapital, boolean validTotalYaz, boolean validPaysEveryYaz, boolean validInterest, boolean validRateOfPayments, boolean validCategory) {
        if (!validCapital)
            showCapitalErrorMessage.set(true);
        if (!validTotalYaz || !validRateOfPayments)
            showTotalYazErrorMessage.set(true);
        if (!validPaysEveryYaz || !validRateOfPayments)
            showPayYazErrorMessage.set(true);
        if (!validInterest)
            showInterestMessage.set(true);
        if (validCategory)
            showCategoryErrorMessage.set(true);
    }


    private void showLoanToSellOrBuy(LoansOnSell loans, FlowPane flowPane, String type) {

        flowPane.alignmentProperty().set(Pos.TOP_LEFT);
        if (loans != null) {
            if (loanChange(loans, type) || loans.getLoans().size() == 0) {

                if (flowPane.getChildren().size() > 0) {
                    flowPane.getChildren().clear();
                }
                if (loans.getLoans().size() == 0) {
                    noLoansInThisStatus(flowPane, "No loans to sell in this moment");
                }

                for (SellingLoanData loan : loans.getLoans())
                    createLoanToSellOrBuyBoxTile(loan, flowPane, type);

                oldLoansToSell = loans;
            }
        } else {
            noLoansInThisStatus(flowPane, "No loans to sell in this moment");
        }


    }

    private boolean loanChange(LoansOnSell newLoans, String type) {
        if (newLoans.getLoans().size() != 0) {
            LoansOnSell oldLoans;
            if (type.equals("Sell"))
                oldLoans = oldLoansToSell;
            else
                oldLoans = oldLoansToBuy;
            if (newLoans.getLoans().size() > oldLoans.getLoans().size())
                return true;
            else
                for (SellingLoanData oldLoan : oldLoans.getLoans())
                    for (SellingLoanData newLoan : newLoans.getLoans()) {
                        if (oldLoan.getSellingCustomer().equals(newLoan.getSellingCustomer()) && oldLoan.getLoanId().equals(newLoan.getLoanId())) {
                            if (oldLoan.getSellingPrice() != newLoan.getSellingPrice())
                                return true;
                        }
                    }
        }
        return false;
    }

    private void createLoanToSellOrBuyBoxTile(SellingLoanData loan, FlowPane flowPane, String type) {

        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/SellingLoanBox/sellingLoanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            SellingLoanController sellingLoanController = loader.getController();
            sellingLoanController.initSellingData(loan.getLoanId(), loan.getSellingPrice());
            CheckBox checkLoan = new CheckBox();
            flowPane.getChildren().add(singleLoanTile);
            flowPane.getChildren().add(checkLoan);

            if (type.equals("Sell"))
                loanToSell.put(loan, checkLoan);
            else if (type.equals("Buy"))
                loanToBuy.put(loan, checkLoan);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setMessageLoansToPay(TextArea messageLoansToPay) {
        this.messageLoansToPay = messageLoansToPay;
    }

}