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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "FileUpload", urlPatterns = {"/pages/userDetails/FileUpload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("pages/userDetails/userDetails.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mapPathName = request.getParameter(Constants.MAP_UPLOAD_NAME);
        String userName = request.getParameter(Constants.USER_NAME);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Collection<Part> parts = request.getParts();

        out.println("<h2> Total parts : " + parts.size() + "</h2>");

        StringBuilder fileContent = new StringBuilder();

        for (Part part : parts) {
            //to write the content of the file to an actual file in the system (will be created at c:\samplefile)
            part.write("samplefile");
            //to write the content of the file to a string
            fileContent.append(readFromInputStream(part.getInputStream()));
        }

        EngineManager engineManager = ServletUtils.getEngineManager(getServletContext());
        try {
            engineManager.handleFileUploadProcess(fileContent.toString(), userName, mapPathName);
        }
        catch (Exception ex) {
            String error = ex.getMessage();
            //Display error to user
        }
        response.sendRedirect("pages/userDetails/userDetails.html");
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}