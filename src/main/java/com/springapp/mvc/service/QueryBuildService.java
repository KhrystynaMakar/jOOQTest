package com.springapp.mvc.service;


import com.springapp.mvc.dto.Group;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.ObjectTypes;
import com.springapp.mvc.dto.Rule;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.SelectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.jooq.impl.DSL.condition;
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
        return dslContextDatabase.renderInlined(getQuery(item));
    }

    public SelectQuery getQuery(Item item) {
        Group group = (Group) item;
        SelectQuery query = dslContextDatabase.selectQuery();

        Condition condition = parseRules(group, query);
        query.addConditions(getOperator(group.getCondition()), condition);
        parseGroup(group, query);
        cleanQueryBuildServiceObject();
        return query;
    }

    private Condition parseRules(Group group, SelectQuery query) {
        List<Rule> rules = getRules(group);
        String operator = group.getCondition();
        Condition condition = field(getFullFieldName(rules.get(0))).equal(rules.get(0).getValue());
        checkQueryTables(query, rules.get(0));

        for (int i = 1; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            checkQueryTables(query, rule);
            if (operator.equals("OR")) {
               condition = condition.or(field(getFullFieldName(rule)).equal(rule.getValue()));
            } else {
               condition = condition.and(field(getFullFieldName(rule)).equal(rule.getValue()));
            }
        }
        return condition;
    }

    private SelectQuery parseGroup(Group parentGroup, SelectQuery query) {
        List<Group> groups = getGroups(parentGroup);
        String parentCondition = parentGroup.getCondition();

        for (Group group : groups) {
            query.addConditions(getOperator(parentCondition), parseRules(group, query));
            parseGroup(group, query);
        }
        return query;
    }

    private List<Rule> getRules(Group group) {
        List<Rule> rules = new ArrayList<Rule>();
        for (Item subItem : group.getRules()) {
            if (subItem.getObjType().equals(ObjectTypes.RULE.toString().toLowerCase())) {
                rules.add((Rule) subItem);
            }
        }
        return rules;
    }

    private List<Group> getGroups(Group parentGroup) {
        List<Group> groups = new ArrayList<Group>();
        for (Item subItem : parentGroup.getRules()) {
            if (subItem.getObjType().equals(ObjectTypes.GROUP.toString().toLowerCase())) {
                groups.add((Group) subItem);
            }
        }
        return groups;
    }

    private String getFullFieldName(Rule rule) {
        return rule.getId() + "." + rule.getField();
    }

    private Operator getOperator(String operatorStr) {
        if (operatorStr.equals(Operator.OR.toString())) {
            return Operator.OR;
        } else {
            return Operator.AND;
        }
    }

    private void checkQueryTables(SelectQuery query, Rule rule) {
        String tableName = rule.getId();

        if (!getTableNames().contains(tableName)) {
            query.addFrom(table(tableName));
            getTableNames().add(tableName);
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
