
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "servlet-app", urlPatterns = "/")
public class Servlet extends HttpServlet {
    private final Set<String> responseSet = ConcurrentHashMap.newKeySet();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter writer = response.getWriter();
        String currentUser = request.getRemoteHost() + "::" + request.getHeader("User-Agent");
        responseSet.add(currentUser);

        writer.println("<h1>List of available users: </h1>");
        writer.println("<ul>");

        for (String s: responseSet) {
            writer.println("<li><b>" + s + "</b></li>");
        }
        writer.println("</ul>");
    }
}