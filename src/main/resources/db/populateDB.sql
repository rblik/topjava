DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2016-09-25 10:00', 'Завтрак', 500, 100000),
  ('2016-09-25 13:00', 'Обед', 1000, 100000),
  ('2016-09-25 20:00', 'Ужин', 500, 100000),
  ('2016-09-26 10:00', 'Завтрак', 1000, 100000),
  ('2016-09-26 13:00', 'Обед', 500, 100000),
  ('2016-09-26 20:00', 'Ужин', 550, 100000),
  ('2016-09-25 11:00', 'Админ ланч', 550, 100001),
  ('2016-09-25 16:00', 'Админ обжиранч', 550, 100001);

-- EXPLAIN SELECT * FROM meals WHERE user_id = 100000;
-- EXPLAIN SELECT * FROM meals WHERE id = 100002 AND user_id = 100000;
-- EXPLAIN SELECT * FROM meals WHERE user_id=100001 AND date_time>'2016-09-25' AND date_time<'2016-09-27' ORDER BY date_time DESC