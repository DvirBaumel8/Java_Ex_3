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

@WebServlet(name = "HighlightTripDetailsForTripRequestServlet", urlPatterns = {"/pages/mapDetails/HighlightTripDetailsForTripRequestServlet"})
public class HighlightTripDetailsForTripRequestServlet extends HttpServlet {
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
        String tripRequestId = request.getParameter(Constants.TRIP_REQUEST_ID);

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());

        try {
            String htmlGraph = engine.userTapOnTripRequest(mapName, Integer.parseInt(tripRequestId));
            String jsonMapPageDto = new Gson().toJson(htmlGraph);
            response.getWriter().write(jsonMapPageDto);
        }
        catch (Exception ex) {
        }
    }
}
