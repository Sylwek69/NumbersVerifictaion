/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.sylwek.lab4.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel;

/**
 * Servlet responsible for model history
 *
 * @author Sylwester Iwanowski
 * @version 4.0
 */
@WebServlet(name = "VerificationNumberHistoryServlet", urlPatterns = {"/VerificationNumberHistoryServlet"})
public class VerificationNumberHistoryServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PeselNipRegonModel model = (PeselNipRegonModel) req.getAttribute("model");
        List<String> history;
        history = model.getFullHistory();

        try (PrintWriter out = resp.getWriter()) {
            render(out, history);
        }
    }

    /**
     *
     * Renders the page to show the history of checked numbers
     *
     * @param out Writer object
     * @param history a list with the history of checked numbers
     */
    private void render(PrintWriter out, List<String> history) {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Number (Pesel, Nip, Regon)</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Historia sprawdzonych numerow:</h1><hr>");

        if (!history.isEmpty()) {
            out.println("<h1><table><thead> <tr><th> id <th> numer </th></tr></thead>");
            Integer i = 0;
            for (String revision : history) {
                out.println("<tr><td>" + ++i + "</td><td>" + revision + "</td></tr><p>");
            }
            out.println("</table></h1>");
        } else {
            out.println("Brak historii sprawdzen");
        }
        out.println("</body>");
        out.println("</html>");

    }

}
