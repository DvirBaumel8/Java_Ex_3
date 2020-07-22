package manager.servlets;

import Engine.manager.EngineManager;
import Engine.maps.MapsTableElementDetails;
import com.google.gson.Gson;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/UserMapDetailsServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UserMapDetailsServlet extends HttpServlet {
    private final String USER_DETAILS_URL = "../userDetails/userDetails.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            EngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
            List<MapsTableElementDetails> mapsTableElements = engineManager.getAllMapsTableElementsDetails();
            String json = gson.toJson(mapsTableElements);
            out.println(json);
            out.flush();
        }
    }
}

