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
        List<Car> cars = (List<Car>) getServletContext().getAttribute("cars");

        List<Car> filteredCars = cars.stream()
                .filter(car -> car.getType().equalsIgnoreCase(carType))
                .collect(Collectors.toList());

        request.setAttribute("filteredCars", filteredCars);
        request.getRequestDispatcher("/displayCars").forward(request, response);
    }
}
