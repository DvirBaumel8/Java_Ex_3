package manager.servlets.userPageServlet;

import com.google.gson.Gson;
import engine.dto.userPage.UserTransactionsHistoryDto;
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserTransactionsHistoryServlet", urlPatterns = {"/pages/userDetails/UserTransactionsHistoryServlet"})
public class UserTransactionsHistoryServlet extends HttpServlet {

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
        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        List<UserTransactionsHistoryDto> userTransactionsHistoryDtoList = engine.getUserTransactionsByUserName(userName);;
        String jsonNewBalanceResponse = new Gson().toJson(userTransactionsHistoryDtoList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonNewBalanceResponse);
        response.sendRedirect(MAP_DETAILS_URL);
    }
}
