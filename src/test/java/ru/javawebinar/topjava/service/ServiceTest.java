package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public abstract class ServiceTest {

    @Test
    public abstract void testSave() throws Exception;

    @Test
    public abstract void testGet() throws Exception;

    @Test(expected = NotFoundException.class)
    public abstract void testGetNotFound() throws Exception;

    @Test
    public abstract void testGetAll() throws Exception;

    @Test
    public abstract void testDelete() throws Exception;

    @Test(expected = NotFoundException.class)
    public abstract void testDeleteNotFound() throws Exception;

    @Test
    public abstract void testUpdate() throws Exception;
}
