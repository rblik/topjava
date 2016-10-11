package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Profile(Profiles.HSQLDB)
@Repository
public class JdbcHSQLDBMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl{

    @Autowired
    public JdbcHSQLDBMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", Date.from(meal.getDateTime().atZone(ZoneId.systemDefault()).toInstant()))
                .addValue("user_id", userId);

        return execute(meal, map);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Date start = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, start, end);
    }

}
