package util;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String SOMEONE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/component/customerMainApp/main.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/component/login/login.fxml";
    public final static String CUSTOMER_PAGE_FXML_RESOURCE_LOCATION = "/component/Customer/customer.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABS_Web";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginServlet";
    public final static String CHARGE_OR_WITHDRAW = FULL_SERVER_PATH + "/chargeOrWithdrawServlet";
    public final static String LAST_TRANSACTIONS = FULL_SERVER_PATH + "/lastTransactionsServlet";

    public final static String LOAD_XML = FULL_SERVER_PATH + "/loadXmlServlet";
    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";
    public final static String GET_LOANS = FULL_SERVER_PATH + "/GetLoansServlet";
    public final static String GET_RELVENT_LOANS = FULL_SERVER_PATH + "/GetLoansForInlayServlet";
    public final static String INLAY = FULL_SERVER_PATH + "/InlayServlet";
    public final static String ADD_LOAN = FULL_SERVER_PATH + "/addLoanServlet";
    public final static String GET_LOANS_TO_SELL = FULL_SERVER_PATH + "/getLoanForSellServlet";
    public final static String ADD_LOANS_TO_SELL = FULL_SERVER_PATH + "/addLoanToSellServlet";
    public final static String BUY_LOANS = FULL_SERVER_PATH + "/buyLoansServlet";
    public final static String GET_LOANS_TO_BUY = FULL_SERVER_PATH + "/getToBuyServlet";
    public final static String GET_CURR_YAZ = FULL_SERVER_PATH + "/getCurrYazServlet";
    public final static String GET_CATEGORIES_AND_FILE_STATUS = FULL_SERVER_PATH +  "/getCategoriesAndFileStatusServlet";
    public final static String GET_LOANS_PAY_IN_THIS_YAZ = FULL_SERVER_PATH + "/getLoansToPayServlet";
    public final static String GET_LOANS_TO_CLOSE = FULL_SERVER_PATH + "/getLoansToCloseServlet";
    public final static String CLOSE_LOANS = FULL_SERVER_PATH + "/closeLoansServlet";
    public final static String PAY_LOANS = FULL_SERVER_PATH + "/payLoansInThisYazServlet";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();

    public static final String USER_NAME = "userName";
    public static final String TYPE_USER = "typeUser";
    public static final String PATH_XML = "pathXml";

    public static final String ADMIN = "admin";
    public static final String CUSTOMER = "customer";
    public static final String CHARGE = "charge";
    public static final String WITHDRAW = "withdraw";
    public static final String AMOUNT = "amount";
    public static final String STATUS = "status";
    public static final String TYPE_LOAN = "typeLoans";
    public static final String EXCEPTION_TYPE = "exceptionType";
    public static final String MODE =  "mode";
    public static final String ON =  "on";
    public static final String OFF =  "off";
    public static final String CURR_YAZ =  "currYaz";
    public static final String CHOSE_YAZ =  "choseYaz";
    public static final String BALANCE = "balance";
    public static final String REWIND_MODE = "rewindMode";



}
