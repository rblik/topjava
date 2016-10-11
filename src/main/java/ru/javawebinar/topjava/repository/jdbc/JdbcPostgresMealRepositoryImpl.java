package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;

/**
 * User: gkislin
 * Date: 26.08.2014
 */
@Profile(Profiles.POSTGRES)
@Repository
public class JdbcPostgresMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl {

    @Autowired
    public JdbcPostgresMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Object getDate(LocalDateTime dateTime) {
        return dateTime;
    }
}
