package component.Admin;

import data.PrintCustomerAndBalance;
import data.PrintLoans;
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
import static util.Constants.OFF;

public class LoansRefresher extends TimerTask {

    private final Consumer<PrintLoans> loansConsumer;
    private final Consumer<String> onFail;

    private String status;
    private SimpleBooleanProperty rewindMode = new SimpleBooleanProperty();
    private SimpleIntegerProperty currYaz = new SimpleIntegerProperty();


    public LoansRefresher(Consumer<PrintLoans> httpRequestLoggerConsumer, Consumer<String> onFail, String status, SimpleBooleanProperty rewindMode, SimpleIntegerProperty currYaz) {
        this.loansConsumer = httpRequestLoggerConsumer;
        this.status = status;
        this.onFail = onFail;
        this.rewindMode.bind(rewindMode);  //todo check the bind work...
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
                .parse(Constants.LOANS)
                .newBuilder()
                .addQueryParameter(Constants.USER_NAME, "a")
                .addQueryParameter(Constants.TYPE_USER, "admin")
                .addQueryParameter(Constants.STATUS, this.status)
                .addQueryParameter(Constants.TYPE_LOAN, "loner")
                .addQueryParameter(MODE, mode)
                .addQueryParameter(CURR_YAZ, currYaz.asString().getValue())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                onFail.accept(e.getMessage());
                // think about something else to respond

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String jsonArrayOfUsersNames = response.body().string();
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
