DROP TABLE IF EXISTS users;
CREATE TABLE
  users
(
  id       SERIAL                NOT NULL,
  male     BOOLEAN,
  nm_title CHARACTER VARYING(20) NOT NULL,
  nm_first CHARACTER VARYING(50) NOT NULL,
  nm_last  CHARACTER VARYING(50) NOT NULL,
  email    CHARACTER VARYING(50) NOT NULL,
  phone    CHARACTER VARYING(20) NOT NULL,
  PRIMARY KEY (id)
);