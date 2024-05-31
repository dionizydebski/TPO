package org.example.tpo_dd_s26958;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DisplayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Car> filteredCars = (List<Car>) request.getAttribute("filteredCars");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Car List</title></head><body>");
        out.println("<h1>List of Cars</h1>");
        out.println("<table border='1'><tr><th>Type</th><th>Brand</th><th>Year</th><th>Fuel Consumption</th></tr>");

        for (Car car : filteredCars) {
            out.println("<tr>");
            out.println("<td>" + car.getType() + "</td>");
            out.println("<td>" + car.getBrand() + "</td>");
            out.println("<td>" + car.getYear() + "</td>");
            out.println("<td>" + car.getFuelConsumption() + "</td>");
            out.println("</tr>");
        }

        out.println("</table></body></html>");
        out.println("<form action=\"/TPO_DD_S26958_war_exploded/\">");
        out.println("<input type=\"submit\" value=\"Back\">");
        out.println("</form>");
        out.println("</body></html>");
    }
}
