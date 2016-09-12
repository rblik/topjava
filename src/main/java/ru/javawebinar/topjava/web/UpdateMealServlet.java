package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MemoryStorageDAO;
import ru.javawebinar.topjava.repository.StorageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class UpdateMealServlet extends HttpServlet {
    private static StorageDAO<Meal> storage;

    @Override
    public void init() throws ServletException {
        storage = MemoryStorageDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = storage.get(id);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/updateMealForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String date = request.getParameter("date");
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        String idString = request.getParameter("id");

        if (idString.isEmpty()) {
            Meal meal = new Meal(localDateTime, description, calories);
            storage.save(meal);
        } else {
            int id = Integer.parseInt(idString);
            Meal meal = new Meal(localDateTime, description, calories);
            meal.setId(id);
            storage.update(meal);
        }

        response.sendRedirect("meals");
    }
}
