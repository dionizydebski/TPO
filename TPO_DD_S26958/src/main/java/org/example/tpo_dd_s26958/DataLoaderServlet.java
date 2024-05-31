package org.example.tpo_dd_s26958;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
}
