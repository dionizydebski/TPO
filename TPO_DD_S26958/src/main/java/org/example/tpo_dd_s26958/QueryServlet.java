package org.example.tpo_dd_s26958;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class QueryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String carType = request.getParameter("carType");
        request.setAttribute("carType", carType);

        request.getRequestDispatcher("/loadData").forward(request, response);
    }
}
