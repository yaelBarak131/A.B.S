package component.customerMainApp;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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

public class YazRefresher extends TimerTask {
    private final Consumer<Integer> yazConsumer;
    private final Consumer<String> onFailConsumer;

    private SimpleBooleanProperty mode;

    public YazRefresher(Consumer<Integer> yazConsumer, Consumer<String> onFailConsumer, SimpleBooleanProperty customerMode) {
        this.yazConsumer = yazConsumer;
        this.onFailConsumer = onFailConsumer;
        mode = new SimpleBooleanProperty(false);
        customerMode.bind(mode);
    }


    @Override
    public void run() {

        String finalUrl = HttpUrl
                .parse(Constants.GET_CURR_YAZ)
                .newBuilder()
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                            //httpRequestLoggerConsumer.accept("Users Request # " + finalRequestNumber + " | Ended with failure...");
                            onFailConsumer.accept(e.getMessage());
                            // think about something else to respond
                        }
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String body = response.body().string();
                String yaz = response.header(CURR_YAZ);
                String modeFomServlet = response.header(MODE);

                if (modeFomServlet.equals("on"))
                    mode.set(true);
                else
                    mode.set(false);
                //Integer loans = GSON_INSTANCE.fromJson(jsonArrayOfUsersNames, Map.class);
                yazConsumer.accept(Integer.parseInt(yaz));


            }
        });
    }
}
