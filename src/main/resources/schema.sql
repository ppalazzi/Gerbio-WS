DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS Category;

CREATE TABLE Brand (
  id INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  reduced_name VARCHAR(250) NOT NULL,
  partner_id VARCHAR(250) NOT NULL
);

CREATE TABLE Category (
  id INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  partner_id VARCHAR(250) NOT NULL,
  definition_price_scope VARCHAR(250) DEFAULT NULL,
  parent_id INT,
  foreign key (parent_id) references Category(id)
);