package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.engine.options.Engine;
import users.UserManager;
import util.ServletUtils;
import util.SessionUtils;

import java.io.IOException;

import static constant.Constants.*;

@WebServlet(name = "= SetYazServlet", urlPatterns = {"/SetYazServlet"})
public class SetYazServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Engine engine = ServletUtils.getEngineManager(getServletContext());

        String actionTypeFromSession = request.getParameter(ACTION_TYPE);

        String yazToSet = request.getParameter("choseYaz");

        if (actionTypeFromSession == null || yazToSet == null)
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        else {
            synchronized (getServletContext()) {
                if (actionTypeFromSession.equals(INCREASE)) {
                    int newYaz = engine.getYaz() + 1;
                    engine.updateYaz(newYaz);
                    response.setStatus(HttpServletResponse.SC_OK);

                } else if (actionTypeFromSession.equals(REWIND_MODE)) {
                    engine.saveAdminRewindDataAndStartRewindMode();
                    engine.setRewindYaz(Integer.parseInt(yazToSet));
                    response.setStatus(HttpServletResponse.SC_OK);

                } else if(actionTypeFromSession.equals(SET_REWIND_YAZ)){
                    engine.setRewindYaz(Integer.parseInt(yazToSet));
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            response.getOutputStream().print(Integer.toString(engine.getYaz()));
            }
        }


    }

}

