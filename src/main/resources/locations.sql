DROP TABLE IF EXISTS locations;
CREATE TABLE
  locations
(
  user_id  BIGINT                NOT NULL,
  street   CHARACTER VARYING(50) NOT NULL,
  city     CHARACTER VARYING(50) NOT NULL,
  state    CHARACTER VARYING(50) NOT NULL,
  postcode CHARACTER VARYING(20) NOT NULL,
  CONSTRAINT user_id UNIQUE (user_id)
);