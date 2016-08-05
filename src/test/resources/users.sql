DROP TABLE IF EXISTS users;
CREATE TABLE
  users
(
  id       BIGINT AUTO_INCREMENT NOT NULL,
  male     BOOLEAN               NOT NULL,
  nm_title VARCHAR(20)           NOT NULL,
  nm_first VARCHAR(50)           NOT NULL,
  nm_last  VARCHAR(50)           NOT NULL,
  email    VARCHAR(50)           NOT NULL,
  phone    VARCHAR(20)           NOT NULL,
  PRIMARY KEY (id)
);