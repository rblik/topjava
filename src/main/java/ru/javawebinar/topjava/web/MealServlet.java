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
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        //Если есть наличие этого параметра в запросе - значит попытка залогиниться
        String userIdStr = request.getParameter("userId");

        if (userIdStr != null) {
            int userId = Integer.valueOf(userIdStr);
            User user = adminRestController.get(userId);

            //Зачем проверяю, сам не знаю. ведь пересылаем на Get
            // а там exception оно и так кидает при неверном userId.
            // Решил на всякий случай, на будущее, так как прийти может любой id.
            if (user != null) {
                request.getSession().setAttribute("sessionUser", user);
                response.sendRedirect("meals");
            }

            //в противном случае понимаем, что чел был уже на страничке с авторизацией
        } else {
            Object sessionUser = request.getSession().getAttribute("sessionUser");

            //Если юзер был, но не авторизовался, то ложим в переменную "-1" и передаем на репозиторий,
            // где оно сразу проверится и возвратит null, а сервис кинет эксепшн
            int userId = (sessionUser == null) ? -1 : ((User) sessionUser).getId();

            String ldBegin = request.getParameter("dateFrom");
            String ldEnd = request.getParameter("dateUntil");
            String ltBegin = request.getParameter("timeFrom");
            String ltEnd = request.getParameter("timeUntil");

            //Ответвляемся при наличии запроса по фильтру
            if (notNullDateData(ldBegin, ldEnd, ltBegin, ltEnd)) {
                if (appropriateDateData(ldBegin, ldEnd, ltBegin, ltEnd)) {
                    LocalDate beginDate = LocalDate.parse(ldBegin);
                    LocalDate endDate = LocalDate.parse(ldEnd);
                    LocalTime beginTime = LocalTime.parse(ltBegin);
                    LocalTime endTime = LocalTime.parse(ltEnd);

                    //DTO типа LocalDateTime, хотя время - интервал в одном дне а дата это дата. криво :(
                    LocalDateTime beginDateTime = LocalDateTime.of(beginDate, beginTime);
                    LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

                    if (beginDate.isBefore(endDate) && beginTime.isBefore(endTime)) {
                        request.setAttribute("mealList", mealRestController.getFilteredByDate(userId, beginDateTime, endDateTime));
                        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("meals");
                    }
                } else {
                    response.sendRedirect("meals");
                }

            } else {
                String id = request.getParameter("id");

                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.valueOf(request.getParameter("calories")), userId);
                LOG.info(meal.getId() == null ? "Create {}" : "Update {}", meal);
                mealRestController.save(meal);
                response.sendRedirect("meals");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        Object sessionUser = request.getSession().getAttribute("sessionUser");

        //Если юзер не авторизован то ложим в переменную "-1" и передаем нв репозитори,
        // где оно сразу проверится и возвратит null, а сервис кинет эксепшн
        // или в случае getAll вернет пустой список (все сделал в контроллере)
        int userId = (sessionUser == null) ? -1 : ((User) sessionUser).getId();

        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList",
                    mealRestController.getAll(userId));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mealRestController.delete(id, userId);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, userId) :
                    mealRestController.get(getId(request), userId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    private boolean notNullDateData(String ldBegin, String ldEnd, String ltBegin, String ltEnd) {
        return ldBegin != null && ldEnd != null && ltBegin != null && ltEnd != null;
    }

    private boolean appropriateDateData(String ldBegin, String ldEnd, String ltBegin, String ltEnd) {
        return !ldBegin.isEmpty() && !ldEnd.isEmpty() && !ltBegin.isEmpty() && !ltEnd.isEmpty();
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
