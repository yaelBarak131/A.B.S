package component.Admin;

import data.PrintCustomerAndBalance;
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

public class CustomersRefresher extends TimerTask {

    private final Consumer<PrintCustomerAndBalance> customerAndBalanceConsumer;
    private final Consumer<String> onFail;

    private SimpleBooleanProperty rewindMode = new SimpleBooleanProperty();
    private SimpleIntegerProperty currYaz = new SimpleIntegerProperty();

    public CustomersRefresher(Consumer<PrintCustomerAndBalance> httpRequestLoggerConsumer, Consumer<String> onFail, SimpleBooleanProperty rewindMode, SimpleIntegerProperty currYaz) {
        this.customerAndBalanceConsumer = httpRequestLoggerConsumer;
        this.onFail = onFail;
        this.rewindMode.bind(rewindMode);
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
                .parse(Constants.CUSTOMERS_AND_BALANCE)
                .newBuilder()
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
                    PrintCustomerAndBalance customers = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, PrintCustomerAndBalance.class);
                    customerAndBalanceConsumer.accept(customers);
                }
            }
        });

    }


}
