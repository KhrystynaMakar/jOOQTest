package com.springapp.mvc.service;


import com.springapp.mvc.dto.Group;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.ObjectTypes;
import com.springapp.mvc.dto.Rule;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Operator;
import org.jooq.SelectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.jooq.impl.DSL.falseCondition;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class QueryBuildService {
    @Autowired
    @Qualifier("dslContextDatabase")
    private DSLContext dslContextDatabase;

    private Set<String> tableNames;

    public String getQueryString(Item item) {
        SelectQuery query = parseGroup(item, null);
        cleanQueryBuildServiceObject();
        return dslContextDatabase.renderInlined(query);
    }

    //1st item = group, always
    private SelectQuery parseGroup(Item item, SelectQuery query) {
        Group group = (Group)item;
        List<Item> items = group.getRules();
        String condition = group.getCondition();
        return  checkItems(items, condition, (query == null) ? dslContextDatabase.selectQuery() : query);
    }

    private SelectQuery checkItems(List<Item> items, String condition, SelectQuery query) {
        for (Item item: items) {
            if (item.getObjType().equals(ObjectTypes.GROUP.toString().toLowerCase())) {
                parseGroup(item, query);
            } else if (item.getObjType().equals(ObjectTypes.RULE.toString().toLowerCase())) {
                parseRule(item, query, condition);
            }
        }
        return query;
    }

    private SelectQuery parseRule(Item item, SelectQuery query, String operator) {
        Rule rule = (Rule) item;
        String tableName = rule.getId();

        if (!getTableNames().contains(tableName)) {
            query.addFrom(table(rule.getId()));
            getTableNames().add(tableName);
        }
        query.addConditions(getOperator(operator), field(getFullFieldName(rule)).equal(rule.getValue()));
        return query;
    }

    private String getFullFieldName(Rule rule) {
        return rule.getId() + "." + rule.getField();
    }

    private Operator getOperator(String operatorStr) {
        if (operatorStr.equals(Operator.OR.toString())){
            return Operator.OR;
        } else {
            return Operator.AND;
        }
    }

    private Set<String> getTableNames() {
        if (tableNames == null) {
            tableNames = new HashSet<String>();
        }
        return tableNames;
    }

    private void cleanQueryBuildServiceObject() {
        tableNames.clear();
    }

}
