package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.Repo.MemoryStorageDAO;
import ru.javawebinar.topjava.Repo.StorageDAO;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditMealServlet extends HttpServlet {
    private static StorageDAO storage;

    @Override
    public void init() throws ServletException {
        storage = MemoryStorageDAO.getInstance();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = (Meal) storage.get(id);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMealForm.jsp").forward(request, response);
    }
}
