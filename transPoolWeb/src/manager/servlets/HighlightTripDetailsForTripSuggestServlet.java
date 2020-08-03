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

@WebServlet(name = "HighlightTripDetailsForTripSuggestServlet", urlPatterns = {"/pages/mapDetails/HighlightTripDetailsForTripSuggestServlet"})
public class HighlightTripDetailsForTripSuggestServlet extends HttpServlet {

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
        String tripSuggestId = request.getParameter(Constants.TRIP_SUGGEST_ID);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());

        try {

        }
        catch (Exception ex) {
        }
    }
}
