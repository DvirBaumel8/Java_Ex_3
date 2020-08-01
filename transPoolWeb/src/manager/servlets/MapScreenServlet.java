package manager.servlets;

import com.google.gson.Gson;
import engine.manager.EngineManager;
import engine.manager.MapPageDto;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "MapScreenServlet", urlPatterns = {"/pages/mapDetails/MapScreenServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class MapScreenServlet extends HttpServlet {

    private final String MAP_DETAILS_URL = "../mapDetails/mapDetails.html";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String mapName = request.getParameter(Constants.MAP_NAME);
        String userName = request.getParameter(Constants.USER_NAME);
        response.setContentType("text/html;charset=UTF-8");
        response.sendRedirect(MAP_DETAILS_URL);
        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        MapPageDto mapPageDto = engine.getMapPageDto(userName, mapName);
        String json = new Gson().toJson(mapPageDto);
        response.getWriter().write(json);
    }
}
