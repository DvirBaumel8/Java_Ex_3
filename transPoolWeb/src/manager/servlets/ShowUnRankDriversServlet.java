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
import java.util.List;

@WebServlet(name = "ShowUnRankDriversServlet", urlPatterns = {"/pages/mapDetails/ShowUnRankDriversServlet"})
public class ShowUnRankDriversServlet extends HttpServlet {
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
        String requestId = request.getParameter(Constants.TRIP_REQUEST_ID);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        List<String> drivers = engine.getDriversToRating(mapName, Integer.parseInt(requestId));
        String driversJson = new Gson().toJson(drivers);
        response.getWriter().write(driversJson);
    }

}
