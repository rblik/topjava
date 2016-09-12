package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.repository.MemoryStorageDAO;
import ru.javawebinar.topjava.repository.StorageDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static StorageDAO storage;

    @Override
    public void init() throws ServletException {
        storage = MemoryStorageDAO.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("remove")!=null) {
            int id = Integer.parseInt(request.getParameter("id"));
            storage.delete(id);
        }
        @SuppressWarnings("unchecked")
        List<MealWithExceed> mealsWithExceeded = MealsUtil.getWithExceeded(storage.getAll(), 2000);
        request.setAttribute("meals", mealsWithExceeded);
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String date = request.getParameter("date");
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        Meal meal = new Meal(id, localDateTime, description, calories);
        storage.update(meal);
        response.sendRedirect("meals");
    }
}
