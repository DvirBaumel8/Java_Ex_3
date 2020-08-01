package manager.servlets;

import com.google.gson.Gson;
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoadAccountBalanceServlet", urlPatterns = {"/pages/userDetails/LoadAccountBalanceServlet"})
public class LoadAccountBalanceServlet extends HttpServlet {
    private final String USER_DETAILS_URL = "../userDetails/userDetails.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter(Constants.USER_NAME);
        String amountToLoad = request.getParameter(Constants.USER_AMOUNT_TO_LOAD);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());

            try {
                engine.loadMoneyIntoAccount(userName, amountToLoad);
                String newBalance = engine.getUserAccountBalance(userName);
                String jsonNewBalance = new Gson().toJson(newBalance);
                response.getWriter().write(jsonNewBalance);
            }
            catch (Exception ex) {
                String error = ex.getMessage();
                String json = new Gson().toJson(error);
                response.getWriter().write(json);
                //Display Error to user
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "The  not found.");
            }

        response.sendRedirect(USER_DETAILS_URL);
    }
}
