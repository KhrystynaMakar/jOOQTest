package com.springapp.mvc.service;


import com.springapp.mvc.dto.Group;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.ObjectTypes;
import com.springapp.mvc.dto.Rule;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Operator;
import org.jooq.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class QueryBuildService {
    @Autowired
    @Qualifier("dslContextDatabase")
    private DSLContext dslContextDatabase;

    private Set<String> tableNames;

    public static final Logger logger = LoggerFactory.getLogger(QueryBuildService.class);

    public String getQueryString(Item item) {
        logger.debug("Get query string");
        return dslContextDatabase.renderInlined(getQuery(item));
    }

    public SelectQuery getQuery(Item item) {
        logger.debug("Get query.");
        Group group = (Group) item;
        SelectQuery query = dslContextDatabase.selectQuery();
        Condition condition = parseRules(group, query);
        if (condition != null) {
            query.addConditions(getOperator(group.getCondition()), condition);
        }
        query.addConditions(getOperator(group.getCondition()),parseGroup(group, query));
        cleanQueryBuildServiceObject();
        return query;
    }

    private Condition parseRules(Group group, SelectQuery query) {
        List<Rule> rules = getRules(group);
        if (!rules.isEmpty()) {
            logger.debug("Parse rules.");
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
        return null;
    }

    private Condition parseGroup(Group parentGroup, SelectQuery query) {
        logger.debug("Parse group.");
        List<Group> groups = getGroups(parentGroup);
        String parentConditionOperator = parentGroup.getCondition();
        Condition groupCondition = null;
        for (Group group : groups) {
            Condition ruleCondition = parseRules(group, query);
            if (ruleCondition != null) {
                groupCondition = (groupCondition == null) ? ruleCondition : clarifyCondition(groupCondition,
                        parentConditionOperator, ruleCondition);
            }

            if (!getGroups(group).isEmpty()) {
                if (groupCondition == null) {
                    groupCondition = parseGroup(group, query);
                } else {
                    groupCondition = clarifyCondition(groupCondition, group.getCondition(), parseGroup(group, query) );
                }
            }
        }
         return groupCondition;
    }

    public Condition clarifyCondition(Condition groupConditions, String operator, Condition condition) {
        if (operator.equals("OR")) {
            groupConditions = groupConditions.or(condition);
        } else {
            groupConditions = groupConditions.and(condition);
        }
        return groupConditions;
    }

    public List<Rule> getRules(Group group) {
        List<Rule> rules = new ArrayList<Rule>();
        for (Item subItem : group.getRules()) {
            if (subItem.getObjType().equals(ObjectTypes.RULE.toString().toLowerCase())) {
                rules.add((Rule) subItem);
            }
        }
        return rules;
    }

    public List<Group> getGroups(Group parentGroup) {
        List<Group> groups = new ArrayList<Group>();
        for (Item subItem : parentGroup.getRules()) {
            if (subItem.getObjType().equals(ObjectTypes.GROUP.toString().toLowerCase())) {
                groups.add((Group) subItem);
            }
        }
        return groups;
    }

    public String getFullFieldName(Rule rule) {
        return rule.getId() + "." + rule.getField();
    }

    public Operator getOperator(String operatorStr) {
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
