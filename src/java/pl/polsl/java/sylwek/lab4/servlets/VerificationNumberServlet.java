/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.sylwek.lab4.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.java.sylwek.lab4.model.IncorrectNumberOfCharactersException;
import pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel;

/**
 * Servlet responsible for operations on model
 *
 * @author Sylwester Iwanowski
 * @version 4.0
 */
@WebServlet(name = "VerificationNumberServlet", urlPatterns = {"/VerificationNumberServlet"})
public class VerificationNumberServlet extends HttpServlet {

    /**
     * PeselNipRegonModel model
     */
    PeselNipRegonModel model;

    /**
     * Init method responsible for initiating PeselNipRegonModel model
     */
    @Override
    public void init() {
        model = new PeselNipRegonModel(null);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param resp servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

            Cookie[] cookies = req.getCookies();
            String number = req.getParameter("number");
            String action = req.getParameter("action");

            switch (action) {
                case "Historia":
                    req.setAttribute("model", model);
                    RequestDispatcher rd = getServletContext().getRequestDispatcher("/VerificationNumberHistoryServlet");
                    rd.include(req, resp);
                    break;

                case "Ciasteczka":
                    renderCookie(out, cookies, number);
                    break;

                case "Sprawdz numer":
                    model.changeValue(number);
                    PeselNipRegonModel.Value value;

                    try {
                        value = model.numberingCheckValue();
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        Cookie cookie = new Cookie(number, date);
                        resp.addCookie(cookie);

                    } catch (IncorrectNumberOfCharactersException e) {
                        out.println(e.getMessage());
                        return;
                    }
                    render(out, number, value);
                    break;
            }
        }
    }

    /**
     * Renders the page for checking the number
     *
     * @param out Writer object
     * @param number parameter
     * @param value Enum object
     */
    private void render(PrintWriter out, String number, PeselNipRegonModel.Value value) {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Werifikator Pesel Nip Regon</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Werifikator numerow (Pesel, Nip, Regon)</h1><hr><hr>");

        out.println("<h1>Sprawdzasz numer:  " + number + "</h1>" + "<p><hr>");

        out.println("<h1>Weryfikacja: </h1>");

        switch (value) {
            case REGON:
                out.println("<h1>Regon poprawny: " + number + "</h1>");
                break;
            case NO_REGON:
                out.println("<h1>Regon niepoprawny: " + number + "</h1>");
                break;
            case PESEL:
                out.println("<h1>Pesel poprawny: " + number + "</h1");
                break;
            case NO_PESEL:
                out.println("<h1>Pesel niepoprawny: " + number + "</h1>");
                break;
            case NIP:
                out.println("<h1>Nip poprawny: " + number + "</h1>");
                break;
            case NO_NIP:
                out.println("<h1>Nip niepoprawny: " + number + "</h1>");
                break;
        }

        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Renders the page for cookies
     *
     * @param out Writer object
     * @param cookies cookies array
     * @param number parameter
     */
    private void renderCookie(PrintWriter out, Cookie[] cookies, String number) {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Werifikator Pesel Nip Regon</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Werifikator numerow (Pesel, Nip, Regon)</h1><hr><hr>");

        if (cookies != null) {
            out.println("Dołączone CIASTECZKA");
            for (int i = 0; i < cookies.length; i++) {
                out.println("<hr>");
                out.println("<br>Dziekujemy za skorzystanie z naszego walidatora. Podales nr: "
                        + cookies[i].getName() + "</br>");
                out.println("<br> W dniu: " + cookies[i].getValue() + "</br>");
            }
        } else {
            out.println("Nie ma ciasteczek");
        }

        out.println("</body>");
        out.println("</html>");
    }

    
    
    private void testDatabase1() {
    
    }
}
