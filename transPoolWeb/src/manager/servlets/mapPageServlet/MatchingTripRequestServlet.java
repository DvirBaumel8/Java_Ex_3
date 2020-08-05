package manager.servlets.mapPageServlet;

import com.google.gson.Gson;
import engine.dto.mapPage.TripRequestDto;
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

@WebServlet(name = "MatchingTripRequestServlet", urlPatterns = {"/pages/mapDetails/MatchingTripRequestServlet"})
public class MatchingTripRequestServlet extends HttpServlet {


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

        EngineManager engine = ServletUtils.getEngineManager(getServletContext());

        try {

        }
        catch (Exception ex) {
        }
    }

}
