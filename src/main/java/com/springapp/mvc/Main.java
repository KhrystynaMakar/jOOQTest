package com.springapp.mvc;

import com.jooqtest.model.tables.Car;
import com.jooqtest.model.tables.Driver;
import com.jooqtest.model.tables.records.CarRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static com.jooqtest.model.tables.Car.CAR;
import static com.jooqtest.model.tables.Driver.DRIVER;
import static com.jooqtest.model.tables.Company.COMPANY;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/jooqtest";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);

            DSLContext database = DSL.using(conn, SQLDialect.MYSQL);

            Result<Record> result = database.select().from("Car").fetch();
            for (Record r: result) {
                Long id = r.getValue(Car.CAR.ID);
                String manufactor = (String)r.getValue(1);
                String model = r.getValue(Car.CAR.MODEL);
                Date date = r.getValue(Car.CAR.CREATE_DATE);
                String color = r.getValue(Car.CAR.COLOR);
                Integer doorQuantity = r.getValue(Car.CAR.DOOR_QUANTITY);

                System.out.println("Car " + id + ": " + manufactor + ", " + model + ", " + date + ", " + color + ", " +
                        doorQuantity);
            }

            System.out.println();
            System.out.println("=============== Car with door_quantity filter ===============");
            List<CarRecord> result2 = database.select()
                    .from(Car.CAR)
                    .where(Car.CAR.DOOR_QUANTITY.eq(4))
                    .fetch().into(CarRecord.class);
            for (CarRecord r: result2) {
                Long id = r.getId();
                String manufactor = r.getManufactor();
                String model = r.getModel();
                Date date = r.getCreateDate();
                String color = r.getColor();
                Integer doorQuantity = r.getDoorQuantity();

                System.out.println("Car " + id + ": " + manufactor + ", " + model + ", " + date + ", " + color + ", " +
                        doorQuantity);
            }

            String sql = database.select(field("color")).from(table("Car")).getSQL();

            System.out.println();
            showDriversWithTheirCar(database);

            System.out.println();
            showCompanyAndEmployee(database);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception!: " + e);
        }
    }

    public static void showDriversWithTheirCar(DSLContext database) {
        System.out.println("=============== Drivers with their car ===============");
        Result<Record> result = database.select()
                .from("Driver")
                .join("Car").on(field("Driver.car_id").equal(field("Car.id")))
                .fetch();
        for (Record r: result) {
            Long id = r.getValue(Driver.DRIVER.ID);
            String firstName = r.getValue(Driver.DRIVER.FIRST_NAME);
            String lastName = r.getValue(Driver.DRIVER.LAST_NAME);
            Date birthDate = r.getValue(Driver.DRIVER.BIRTHDAY);
            String telephone = r.getValue(Driver.DRIVER.TELEPHONE);

            Long carId = r.getValue(Driver.DRIVER.CAR_ID);
            String manufactor = r.getValue(Car.CAR.MANUFACTOR);
            String model = r.getValue(Car.CAR.MODEL);
            Date date = r.getValue(Car.CAR.CREATE_DATE);
            String color = r.getValue(Car.CAR.COLOR);
            Integer doorQuantity = r.getValue(Car.CAR.DOOR_QUANTITY);

            System.out.println("Driver " + id + ": " + firstName + " " + lastName + ", " + birthDate + ", " +
                    telephone + ", car " + carId + ": " + manufactor + ", " + model + ", " + date + ", " + color + ", " +
                    doorQuantity);
        }
    }

    public static void showCompanyAndEmployee(DSLContext database){
        System.out.println("=============== Company and employee ===============");
        Result<Record> result = database.select()
                .from(table("Company").as("c"))
                .join(table("Driver").as("d")).on(field("d.company_id").equal(field("c.id")))
                .join("Car").on(field("car.id").equal(field("d.car_id")))
                .fetch();

        for (Record r: result) {
            String companyName = r.getValue(COMPANY.NAME);
            String driverName = r.getValue(DRIVER.FIRST_NAME) + " " + r.getValue(DRIVER.LAST_NAME);
            String car = r.getValue(CAR.MANUFACTOR) + " " + r.getValue(CAR.MODEL);

            System.out.println("Company: " + companyName + ", driver: " + driverName + ", car: " + car );
        }
    }
}
