package com.springapp.mvc.service;

import com.springapp.mvc.dto.Group;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.Rule;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemBuilder {

    public static final Item CAR_RED = buildRule("car","color","red");
    public static final Item CAR_BLUE = buildRule("car","color","blue");
    public static final Item CAR_GOLD = buildRule("car","color","gold");
    public static final Item CAR_BLACK = buildRule("car","color","black");
    public static final Item CAR_KUGA = buildRule("car","model","Kuga");
    public static final Item CAR_3= buildRule("car","id","3");
    public static final Item DRIVER_SLAVIK = buildRule("driver", "first_name", "Slavik");
    public static final Item DRIVER_MAKAR = buildRule("driver", "last_name", "Makar");


    public Item build(List<Item> items, String operator) {
        Group group = new Group();
        group.setRules(items);
        group.setCondition(operator);
        return group;
    }

    public static Rule buildRule(String id, String field, String value) {
        Rule rule = new Rule();
        rule.setId(id);
        rule.setField(field);
        rule.setValue(value);
        return rule;
    }
}
