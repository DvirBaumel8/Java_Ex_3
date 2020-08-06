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

@WebServlet(name = "MatchingActionServlet", urlPatterns = {"/pages/mapDetails/MatchingActionServlet"})
public class MatchingActionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String mapName = request.getParameter(Constants.MAP_NAME);
        String userName = request.getParameter(Constants.USER_NAME);
        String tripRequestId = request.getParameter(Constants.TRIP_REQUEST_ID);
        String suggestIdPotentialTrip =  request.getParameter(Constants.SUGGEST_ID_POTENTIAL_TRIP);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        String errors = engine.makeMatch(Integer.parseInt(suggestIdPotentialTrip), mapName, Integer.parseInt(tripRequestId));
        String jsonError = new Gson().toJson(errors);
        response.getWriter().write(jsonError);
    }


}
