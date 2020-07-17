package manager.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MapScreenServlet extends HttpServlet {

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
            /*
            Gson gson = new Gson();
            EngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
            List<MapsTableElementDetails> mapsTableElements = engineManager.getAllMapsTableElementsDetails();
            String json = gson.toJson(mapsTableElements);
            out.println(json);
            out.flush();

             */
        }
    }
}
