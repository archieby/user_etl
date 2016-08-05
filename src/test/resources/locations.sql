DROP TABLE IF EXISTS locations;
CREATE TABLE
  locations
(
  user_id  BIGINT  NOT NULL,
  street   VARCHAR NOT NULL,
  city     VARCHAR NOT NULL,
  state    VARCHAR NOT NULL,
  postcode VARCHAR NOT NULL,
  CONSTRAINT user_id UNIQUE (user_id)
);