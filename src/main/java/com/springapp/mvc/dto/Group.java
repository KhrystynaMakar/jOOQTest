package com.springapp.mvc.dto;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("group")
public class Group extends Item {
    @JsonIgnore
    private final static String OBJ_TYPE = "group";
    private String condition;
    private List<Item> rules;

    public Group() {
        rules = new ArrayList<Item>();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<Item> getRules() {
        return rules;
    }

    public void setRules(List<Item> rules) {
        this.rules = rules;
    }

    public void addItem(Item item){
        rules.add(item);
    }

    @Override
    @JsonIgnore
    public String getObjType() {
        return OBJ_TYPE;
    }
}
