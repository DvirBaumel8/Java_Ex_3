package manager.servlets;
//taken from: http://www.servletworld.com/servlet-tutorials/servlet3/multipartconfig-file-upload-example.html
// and http://docs.oracle.com/javaee/6/tutorial/doc/glraq.html
import engine.manager.EngineManager;
import manager.constans.Constants;
import manager.utils.ServletUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "FileUpload", urlPatterns = {"/pages/userDetails/FileUpload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet {

    private final String USER_DETAILS_URL = "../userDetails/userDetails.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(USER_DETAILS_URL);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mapPathName = request.getParameter(Constants.MAP_UPLOAD_NAME);
        //String userName = request.getParameter(Constants.USER_NAME);
        String userName = "ohad";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Collection<Part> parts = request.getParts();


        StringBuilder fileContent = new StringBuilder();

        for (Part part : parts) {
            //to write the content of the file to an actual file in the system (will be created at c:\samplefile)
            part.write("samplefile");
            //to write the content of the file to a string
            fileContent.append(readFromInputStream(part.getInputStream()));
        }

        EngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        try {
            engineManager.handleFileUploadProcess(fileContent.toString(), "d", "map1");
        }
        catch (Exception ex) {
            String error = ex.getMessage();
            //Display error to user
        }

        response.sendRedirect(USER_DETAILS_URL);
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}