package com.springapp.mvc.service;

import com.springapp.mvc.dto.Group;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.Rule;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


import static org.jooq.impl.DSL.field;

@Service
public class CarService {

    @Autowired
    @Qualifier("dslContextDatabase")
    private DSLContext dslContextDatabase;

    public String findByColor(Item item) {
        Rule rule = null;
        Group group = (Group)item;
        /*if (item.getObjType().equals(ObjectTypes.RULE.toString())) {
            rule = (Rule) item;
        } else if (item.getObjType().equals(ObjectTypes.GROUP.toString())) {
            group = (Group) item;
        }*/

        try {
            Rule rule1 = (Rule) group.getRules().get(0);
            String sqlString = dslContextDatabase.select()
                    .from("Driver")
                    .join(rule1.getId()).on(field("Driver.car_id").equal(field("Car.id")))
                    .where(field(rule1.getId() + "." + rule1.getField())
                            .equalIgnoreCase(rule1.getValue())).getSQL();
            return sqlString;
        } catch (NullPointerException e) {
            System.out.println("Exception!: " + e);
            e.printStackTrace();
        }
        return null;
    }
}
