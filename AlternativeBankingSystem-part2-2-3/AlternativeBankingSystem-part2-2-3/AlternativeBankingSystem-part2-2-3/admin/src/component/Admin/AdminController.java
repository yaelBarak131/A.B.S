package component.Admin;

import component.CustomerBox.customerBoxController;
import component.LoanBox.LoanBoxController;
import data.*;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static util.Constants.REFRESH_RATE;

public class AdminController {
    @FXML
    private ComboBox<String> statusCB;
    @FXML
    private FlowPane loanFlowPane;
    @FXML
    private FlowPane customerFlowPane;


    private Stage primaryStage;

    private SimpleStringProperty selectedFileProperty;
    private SimpleIntegerProperty currYaz;
    private SimpleBooleanProperty showLabel;
    private SimpleBooleanProperty rewindMode;
    //delete?
    private CustomersRefresher customerAndBalanceRefresher;
    private Timer customerAndBalanceTimer;
    private final IntegerProperty customerAndBalanceProperty;

    private LoansRefresher loansRefresher;
    private Timer loansTimer;
    private final IntegerProperty loansProperty;

  /*  public void setModel(Engine engine){
        this.engine = engine;
   }*/

    public AdminController() {
        selectedFileProperty = new SimpleStringProperty();
        showLabel = new SimpleBooleanProperty(true);
        // odo 29.6
        customerAndBalanceProperty = new SimpleIntegerProperty();
        loansProperty = new SimpleIntegerProperty();
        rewindMode = new SimpleBooleanProperty();
        currYaz = new SimpleIntegerProperty();
    }

    @FXML
    private void initialize() {
        // selectedFileProperty = new SimpleStringProperty();
        // statusCB.disableProperty().bind(isFileSelected.not());
        // anvalideXML.visibleProperty().bind(showLabel);
        initStatusCB();
    }

    public void setActive() {
        startCustomerRefresher();
        startLoansRefresher();
    }

    public SimpleBooleanProperty getRewindMode() {
        return rewindMode;
    }

    public SimpleIntegerProperty getCurrYaz() {
        return currYaz;
    }

    public void startCustomerRefresher() {
        customerAndBalanceRefresher = new CustomersRefresher(
                this::updateCustomersAndBalance,
                this::customerOnFail,
                rewindMode,
                currYaz
        );
        customerAndBalanceTimer = new Timer();
        customerAndBalanceTimer.schedule(customerAndBalanceRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void customerOnFail(String massage) {
        Platform.runLater(() ->
        {
            Label error;
            error = new Label(massage);
            customerFlowPane.alignmentProperty().set(Pos.CENTER);
            customerFlowPane.getChildren().add(error);
        });
    }

    private void updateCustomersAndBalance(PrintCustomerAndBalance customerAndBalance) {

        Platform.runLater(() -> {
            customerFlowPane.alignmentProperty().set(Pos.TOP_LEFT);

            if (customerFlowPane.getChildren().size() > 0)
                customerFlowPane.getChildren().clear();

            if (customerAndBalance.getCustomers().size() == 0)
                noCustomerInThisSystem();
            else {
                customerFlowPane.getChildren().addAll(addCustomers(customerAndBalance));
                customerAndBalanceProperty.set(customerAndBalance.getCustomers().size());
            }
        });

    }

    public void startLoansRefresher() {
        loansRefresher = new LoansRefresher(
                this::updateLoans,
                this::loansOnFail,
                statusCB.getValue(),
                rewindMode,
                currYaz);
        loansTimer = new Timer();
        loansTimer.schedule(loansRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    private void loansOnFail(String massage) {
        Platform.runLater(() ->
        {
            Label error;
            error = new Label(massage);
            loanFlowPane.alignmentProperty().set(Pos.CENTER);
            loanFlowPane.getChildren().add(error);
        });
    }

    private void updateLoans(PrintLoans loans) {

        Platform.runLater(() -> {

            loanFlowPane.alignmentProperty().set(Pos.TOP_LEFT);

            if (loanFlowPane.getChildren().size() > 0)
                loanFlowPane.getChildren().clear();


            if (loans.getLoans().size() == 0) {
                noLoansInThisStatus();
            } else {
                // addLoans(loans);
                loanFlowPane.getChildren().addAll(addLoans(loans));
                loansProperty.set(loans.getLoans().size());
            }
        });

    }

    private List<ScrollPane> addLoans(PrintLoans loans) {
        List<ScrollPane> res = new ArrayList<>();

        for (PrintLoan loan : loans.getLoans()) {
            res.add(createLoanTile(loan));
        }

        return res;
    }

    private ScrollPane createLoanTile(PrintLoan loan) {

        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/LoanBox/loanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            LoanBoxController loanBoxControllerController = loader.getController();
            loanBoxControllerController.initLoanData(loan);

            //loanFlowPane.getChildren().add(singleLoanTile);
            return singleLoanTile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void noLoansInThisStatus() {
        Label noLoans;

        //if(isFileSelected.get()) {
        noLoans = new Label("There are no loans in this status right now.");
        loanFlowPane.alignmentProperty().set(Pos.CENTER);
        loanFlowPane.getChildren().add(noLoans);

    }

    private void noCustomerInThisSystem() {
        Label noCustomer;

        noCustomer = new Label("There are no customer in the system right now.");
        customerFlowPane.alignmentProperty().set(Pos.CENTER);
        customerFlowPane.getChildren().add(noCustomer);

    }

    public BooleanProperty getShowLabel() {
        return showLabel;
    }

    // public StringExpression getFullPath(){
    //     return selectedFileProperty;
    //   }
    public void initStatusCB() {
        ObservableList<String> list = statusCB.getItems();
        statusCB.setPromptText("All");
        statusCB.setValue("All");
        //Adding items to the combo box
        list.add("New");
        list.add("Pending");
        list.add("Active");
        list.add("In Risk");
        list.add("Finished");
        list.add("All");

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

//    @FXML
//    void openFileButtonAction(ActionEvent event) {
//
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select xml file");
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
//        File selectedFile = fileChooser.showOpenDialog(primaryStage);
//        if (selectedFile == null) {
//            return;
//        }
//
//        String absolutePath = selectedFile.getAbsolutePath();
//       if(tryLoadXml(absolutePath)) {//think about the exception and what to do with them
//           showLabel.set(false);
//           selectedFileProperty.set(absolutePath);
//           loanFlowPane.getChildren().clear();
//           customerFlowPane.getChildren().clear();
//           showCustomer();
//           showLoans();
//           isFileSelected.set(true);
//           counter.setValue(1);
//
//       }
//        else {
//           if (oldEngine.isFileOpen())
//               engine = oldEngine;
//
//           showLabel.set(true);
//       }
//
//    }
//    private boolean tryLoadXml(String input) {
//        boolean res = true;
//        try {
//            oldEngine = engine;
//            engine.loadXml(input);
//            engine.setCurrYaz(1);
//            engine.setFileOpen(true);
//            System.out.println("The file was successfully open");
//        } catch (CustomerException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//        } catch (CategoryException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//        } catch (OwnerException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//        } catch (JAXBException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//
//        } catch (PaymentRateException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//
//        } catch (FileNotFoundException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//
//        } catch (NumberFormatException e) {
//            System.out.println(e.getMessage());
//            anvalideXML.textProperty().set(e.getMessage());
//            res = false;
//        }
//        return res;
//    }

    @FXML
    void chooseStatus(ActionEvent event) {
        loansRefresher.setStatus(statusCB.getValue());
    }


    /*public void showLoans(){
        PrintLoans loans;

        loanFlowPane.alignmentProperty().set(Pos.TOP_LEFT);

        if(loanFlowPane.getChildren().size() > 0)
            loanFlowPane.getChildren().clear();

        String status = statusCB.getValue();

        if(status.equals("All"))  {
          //  loans = engine.getAllLoans();
        }
        else
           // loans = engine.getAllLoans().getLoanWithStatus(status);

        if(loans.getLoans().size() == 0){
            noLoansInThisStatus();
        }
        else{
            addLoans(loans);
        }
    }*/
/*


    }*/



/*    private void createLoanTile(LoanData loan) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/loanBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleLoanTile = loader.load();

            LoanBoxController loanBoxControllerController = loader.getController();
            loanBoxControllerController.initLoanData(loan);

            loanFlowPane.getChildren().add(singleLoanTile);
        //add to list?
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*   public void showCustomer(){
           PrintCustomers customers;
           if(customerFlowPane.getChildren().size()>0)
               customerFlowPane.getChildren().clear();
               //customers = engine.getAllCustomers();
            *//*if(customers.getCustomers().size() == 0){
            //noLoans.set(true);
        }*//*
       else{
           //addCustomers(customers);
       }

   }*/
    private List<ScrollPane> addCustomers(PrintCustomerAndBalance customers) {
        String name;
        double balance;
        List<ScrollPane> res = new ArrayList<>();
        for (List<String> customer : customers.getCustomers()) {
            name = customer.get(0);
            balance = Double.parseDouble(customer.get(1));
            res.add(createCustomerTile(name, balance));
        }
        return res;
    }

    private ScrollPane createCustomerTile(String name, double balance) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL mainFXML = getClass().getResource("/component/CustomerBox/customerBox.fxml");
            loader.setLocation(mainFXML);
            ScrollPane singleCustomerTile = loader.load();

            customerBoxController customerBoxControllerController = loader.getController();
            customerBoxControllerController.initCustomerData(name, balance);

            //customerFlowPane.getChildren().add(singleCustomerTile);
            return singleCustomerTile;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
