package manager.servlets;

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

    private final String MAP_DETAILS_URL = "../userDetails/userDetails.html";


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
        engine.loadMoneyIntoAccount(userName, amountToLoad);

        //String jsonNewBalanceResponse = new Gson().toJson(mapsTableElementDetailsListDto);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        //response.getWriter().write(json);
        response.sendRedirect(MAP_DETAILS_URL);
    }
}
