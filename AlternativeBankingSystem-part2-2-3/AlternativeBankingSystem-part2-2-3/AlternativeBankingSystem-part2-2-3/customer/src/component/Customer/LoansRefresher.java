package component.Customer;

import data.PrintLoans;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.Constants.*;

public class LoansRefresher extends TimerTask {

    private final Consumer<PrintLoans> loansConsumer;
    private final Consumer<String> onFail;

    private String status;
    private final String userName;
    private final String loansType;
    private BooleanProperty rewindMode = new SimpleBooleanProperty();
    private IntegerProperty currYaz = new SimpleIntegerProperty();

    public LoansRefresher(Consumer<PrintLoans> httpRequestLoggerConsumer, Consumer<String> onFail, String status, String userName, String loansType, BooleanProperty rewindMode, IntegerProperty currYaz) {
        this.loansConsumer = httpRequestLoggerConsumer;
        this.onFail = onFail;
        this.status = status;
        this.userName = userName;
        this.loansType = loansType;
        this.rewindMode.bind(rewindMode);  // check the bind work...
        this.currYaz.bind(currYaz);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void run() {
        String mode;
        if (rewindMode.get())
            mode = ON;
        else
            mode = OFF;

        String finalUrl = HttpUrl
                .parse(Constants.GET_LOANS)
                .newBuilder()
                .addQueryParameter(Constants.USER_NAME, this.userName)
                .addQueryParameter(Constants.TYPE_USER, "customer")
                .addQueryParameter(Constants.STATUS, this.status)
                .addQueryParameter(Constants.TYPE_LOAN, this.loansType)
                .addQueryParameter(MODE, mode)
                .addQueryParameter(CURR_YAZ, Integer.toString(currYaz.get()))
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                //todo 14/7
                onFail.accept(e.getMessage());
                // think about something else to respond

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfUsersNames = response.body().string();
                //System.out.println(jsonArrayOfUsersNames);
                //todo 14/7
                if (response.code() != 200)
                    onFail.accept(jsonArrayOfUsersNames);
                else {
                    PrintLoans loans = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, PrintLoans.class);
                    loansConsumer.accept(loans);
                }
            }
        });

    }


}
