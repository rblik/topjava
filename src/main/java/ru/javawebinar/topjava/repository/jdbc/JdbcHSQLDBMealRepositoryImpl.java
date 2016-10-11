package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Profile(Profiles.HSQLDB)
@Repository
public class JdbcHSQLDBMealRepositoryImpl extends AbstractJdbcMealRepositoryImpl{

    @Autowired
    public JdbcHSQLDBMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Object getDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
