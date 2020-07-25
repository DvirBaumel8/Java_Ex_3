package manager.servlets;


import com.google.gson.Gson;
import engine.dto.MapsTableElementDetailsDto;
import engine.dto.UserMapDetailsPageDto;
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "UserMapDetailsServlet", urlPatterns = {"/pages/userDetails/UserMapDetailsServlet"})
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
        //Todo - send user name
        //String userName = request.getParameter(Constants.USER_NAME);
        EngineManager engine = ServletUtils.getEngineManager(getServletContext());
        UserMapDetailsPageDto userMapDetailsPageDto = engine.getUserMapDetailsPageDto("d");
        String json = new Gson().toJson(userMapDetailsPageDto);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}

