package component.Customer;

import data.CategoriesAndFileStatus;
import data.PrintTransactionsAndBalance;
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

public class CategoriesAndFileStatusRefresher extends TimerTask {
    private final Consumer<CategoriesAndFileStatus> categoriesAndFileConsumer;
    private final Consumer<String> onFail;

    public CategoriesAndFileStatusRefresher(Consumer<CategoriesAndFileStatus> categoriesAndFileConsumer, Consumer<String> onFail) {
        this.categoriesAndFileConsumer = categoriesAndFileConsumer;
        this.onFail = onFail;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(GET_CATEGORIES_AND_FILE_STATUS)
                .newBuilder()
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
                    CategoriesAndFileStatus res = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, CategoriesAndFileStatus.class);
                    categoriesAndFileConsumer.accept(res);
                }
            }
        });

    }
}
