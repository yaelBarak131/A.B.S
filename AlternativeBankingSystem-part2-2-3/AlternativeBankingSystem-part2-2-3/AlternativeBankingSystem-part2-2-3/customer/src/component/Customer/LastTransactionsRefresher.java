package component.Customer;

import data.PrintTransactions;
import data.PrintTransactionsAndBalance;
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
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.Constants.*;

public class LastTransactionsRefresher extends TimerTask {

    private final Consumer<PrintTransactionsAndBalance> lastTransactionsConsumer;
    private final Consumer<String> onFail;

    private final String customerName;
    private BooleanProperty rewindMode = new SimpleBooleanProperty();
    private IntegerProperty currYaz = new SimpleIntegerProperty();

    public LastTransactionsRefresher(Consumer<PrintTransactionsAndBalance> httpRequestLoggerConsumer,Consumer<String> onFail,  String customerName, BooleanProperty rewindMode, IntegerProperty currYaz) {
        this.lastTransactionsConsumer = httpRequestLoggerConsumer;
        this.onFail=onFail;
        this.customerName = customerName;
        this.rewindMode.bind(rewindMode);  //check the bind work...
        this.currYaz.bind(currYaz);
    }

    @Override
    public void run() {
        String mode;
        if (rewindMode.get())
            mode = ON;
        else
            mode = OFF;

        String finalUrl = HttpUrl
                .parse(Constants.LAST_TRANSACTIONS)
                .newBuilder()
                .addQueryParameter("customer", customerName)
                .addQueryParameter(REWIND_MODE, mode)
                .addQueryParameter(CURR_YAZ, Integer.toString(currYaz.get()))
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
               //todo 14/7-check is the same
                if (response.code() != 200) {
                    onFail.accept(jsonArrayOfUsersNames);
                } else {
                    PrintTransactionsAndBalance transactionsAndBalance = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, PrintTransactionsAndBalance.class);
                    lastTransactionsConsumer.accept(transactionsAndBalance);
                }
            }
        });

    }


}
