package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx;
    private AdminRestController adminRestController;
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        adminRestController = appCtx.getBean(AdminRestController.class);
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        mealRestController.setSession(request.getSession());

        String userIdStr = request.getParameter("userId");
        if (userIdStr != null) {
            int userId = Integer.valueOf(userIdStr);
            User user = adminRestController.get(userId);

            if (user != null) {
                session.setAttribute("sessionUser", user);
                response.sendRedirect("meals");
            }
        } else {
            if (request.getParameter("filter") != null) {
                //branching out to filtering
                String ldBegin = request.getParameter("dateFrom");
                String ldEnd = request.getParameter("dateUntil");
                String ltBegin = request.getParameter("timeFrom");
                String ltEnd = request.getParameter("timeUntil");

                request.setAttribute("mealList", mealRestController.getFilteredByDateAndTime(ldBegin, ldEnd, ltBegin, ltEnd));
                request.getRequestDispatcher("/mealList.jsp").forward(request, response);

            } else {
                String id = request.getParameter("id");

                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")));
                LOG.info(meal.getId() == null ? "Create {}" : "Update {}", meal);
                mealRestController.save(meal);
                response.sendRedirect("meals");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        mealRestController.setSession(request.getSession());

        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    mealRestController.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
