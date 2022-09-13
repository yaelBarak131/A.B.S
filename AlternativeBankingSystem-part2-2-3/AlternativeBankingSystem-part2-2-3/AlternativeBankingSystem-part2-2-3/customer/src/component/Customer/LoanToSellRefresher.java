package component.Customer;

import data.LoansOnSell;
import javafx.application.Platform;
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
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

import static util.Constants.*;
import static util.Constants.OFF;

public class LoanToSellRefresher extends TimerTask {

    private final String userName;
    private final Consumer<LoansOnSell> loansConsumer;
    //todo 14/7
    private final Consumer<String> onFail;
    private BooleanProperty rewindMode = new SimpleBooleanProperty();
    private IntegerProperty currYaz = new SimpleIntegerProperty();


    public LoanToSellRefresher(String userName, Consumer<LoansOnSell> loansConsumer, Consumer<String> onFail, BooleanProperty rewindMode, IntegerProperty currYaz) {
        this.userName = userName;
        this.loansConsumer = loansConsumer;
        this.onFail = onFail;
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
                .parse(Constants.GET_LOANS_TO_SELL)
                .newBuilder()
                .addQueryParameter(USER_NAME, userName)
                .addQueryParameter(REWIND_MODE, mode)
                .addQueryParameter(CURR_YAZ, Integer.toString(currYaz.get()))
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                           //todo 14/7
                    onFail.accept(e.getMessage());
                            // think about something else to respond
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String jsonArrayOfUsersNames = response.body().string();
                if(response.code()!=200)
                  onFail.accept(jsonArrayOfUsersNames);
                else {
                    LoansOnSell loans = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, LoansOnSell.class);
                    loansConsumer.accept(loans);
                }

            }
        });
    }
}
