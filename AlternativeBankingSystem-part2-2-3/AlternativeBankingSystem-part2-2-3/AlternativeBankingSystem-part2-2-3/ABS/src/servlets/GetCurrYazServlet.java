package servlets;

import data.SellingLoansData;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;

import static constant.Constants.*;
import static servlets.InlayServlet.GSON_INSTANCE;

@WebServlet(name = "=GetCurrYazServlet", urlPatterns = {"/getCurrYazServlet"})
public class GetCurrYazServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngineManager(getServletContext());

        synchronized (getServletContext()) {
            int yaz = 0;
            boolean rewindMode = engine.isRewindMode();
            String mode;
            if (rewindMode) {
                mode = ON;
                yaz = engine.getRewindYaz();

            } else {
                mode = OFF;
                yaz = engine.getYaz();

            }
            response.getOutputStream().print("good");
            response.addHeader(CURR_YAZ, Integer.toString(yaz));
            response.addHeader(MODE, mode);

            response.setStatus(HttpServletResponse.SC_OK);

        }
    }
}

