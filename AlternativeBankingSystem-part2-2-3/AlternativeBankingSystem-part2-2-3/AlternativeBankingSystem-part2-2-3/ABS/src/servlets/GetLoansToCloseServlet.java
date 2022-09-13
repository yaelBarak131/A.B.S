package servlets;

import com.google.gson.Gson;
import data.PrintLoansToPay;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import util.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

import static constant.Constants.*;

@WebServlet(name="=getLoansToCloseServlet", urlPatterns = {"/getLoansToCloseServlet"})
public class GetLoansToCloseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngineManager(getServletContext());
        String usernameFromParameter = request.getParameter(USER_NAME);
        String modeFromParameter = request.getParameter(REWIND_MODE);
        String currYazFromParameter = request.getParameter(CURR_YAZ);

        if (usernameFromParameter == null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }

        PrintLoansToPay loans = null;
        Gson gson = new Gson();
        synchronized (getServletContext()) {
            if (modeFromParameter.equals(OFF)) {
                loans = engine.getLoansToClose(usernameFromParameter);
            } else if (modeFromParameter.equals(ON)) {
                loans = engine.getLoansToCloseInRewindMode(usernameFromParameter, Integer.parseInt(currYazFromParameter));
            }

            String json = gson.toJson(loans);
            try (PrintWriter out = response.getWriter()) {
                out.println(json);
                out.flush();
                //todo 14/7
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}

