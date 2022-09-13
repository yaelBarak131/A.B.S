package util;

import com.google.gson.Gson;

public class Constants {

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String SOMEONE = "<Anonymous>";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";
    public static final String STATUS = "status";
    // fxml locations
    public final static String MAIN_PAGE_FXML_RESOURCE_LOCATION = "/component/adminMainApp/main.fxml";
    public final static String LOGIN_PAGE_FXML_RESOURCE_LOCATION = "/component/login/login.fxml";
    public final static String ADMIN_PAGE_FXML_RESOURCE_LOCATION = "/component/Admin/admin.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABS_Web";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH +"/loginServlet";

    public final static String CUSTOMERS_AND_BALANCE = FULL_SERVER_PATH + "/customerAndBalanceServlet";
    public final static String LOANS = FULL_SERVER_PATH + "/GetLoansServlet";
    public final static String SET_YAZ =   FULL_SERVER_PATH + "/SetYazServlet";
    public static final String TYPE_LOAN="typeLoans";
    public static final String USER_NAME = "userName";
    public static final String TYPE_USER = "typeUser";

    public static final String ACTION_TYPE = "actionType";
    public static final String INCREASE =  "increase";
    public static final String REWIND_MODE =  "rewindMode";
    public static final String SET_REWIND_YAZ ="setRewindYaz";

    public static final String MODE =  "mode";
    public static final String ON =  "on";
    public static final String OFF =  "off";
    public static final String CURR_YAZ =  "currYaz";
    public static final String CHOSE_YAZ =  "choseYaz";


    public final static String USERS_LIST = FULL_SERVER_PATH + "/userslist";
    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
