package org.example.tpo_dd_s26958;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataLoaderServlet extends HttpServlet {

    private List<Car> cars = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        String filePath = getServletContext().getRealPath("/WEB-INF/cars.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Car car = new Car(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]));
                cars.add(car);
            }
        } catch (IOException e) {
            throw new ServletException("Error reading car data", e);
        }


        getServletContext().setAttribute("cars", cars);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String carType = (String) request.getAttribute("carType");

        List<Car> filteredCars = cars.stream()
                .filter(car -> car.getType().equalsIgnoreCase(carType))
                .collect(Collectors.toList());

        request.setAttribute("filteredCars", filteredCars);
        request.getRequestDispatcher("/displayCars").forward(request, response);
    }
}
