DROP TABLE IF EXISTS car;
CREATE TABLE car (
id BIGINT  NOT NULL AUTO_INCREMENT,
manufactor VARCHAR(255),
create_date DATE,
model VARCHAR(30),
color VARCHAR(20),
door_quantity INT,
petrol BOOLEAN,

PRIMARY KEY(id)
);

DROP TABLE IF EXISTS company;
CREATE TABLE company (

  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(50),
  email VARCHAR(30),
  city VARCHAR(20),

  PRIMARY KEY (id)

);

DROP TABLE IF EXISTS driver;
CREATE TABLE driver (

  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NOT NULL,
  birthday DATE,
  telephone VARCHAR(20) NOT NULL,
  car_id BIGINT NOT NULL,
  company_id BIGINT NOT NULL,

  FOREIGN KEY (car_id) REFERENCES car(id),
  FOREIGN KEY (company_id) REFERENCES company(id),
  PRIMARY KEY(id)
);