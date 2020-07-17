package manager.utils;

import manager.constans.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static String getAttribute (HttpServletRequest request, String param) {
        HttpSession session = request.getSession(true);
        Object sessionAttribute = session != null ? request.getParameter(param) : null;
        return sessionAttribute != null ? sessionAttribute.toString() : null;
    }

    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
