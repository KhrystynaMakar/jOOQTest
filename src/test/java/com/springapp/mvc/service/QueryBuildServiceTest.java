package com.springapp.mvc.service;

import com.springapp.mvc.dto.Group;
import com.springapp.mvc.dto.Item;
import com.springapp.mvc.dto.Rule;
import junit.framework.Assert;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Operator;
import org.jooq.SelectQuery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class QueryBuildServiceTest {

    @Autowired
    @Qualifier("dslContextDatabase")
    private DSLContext dslContextDatabase;

    @Autowired
    private QueryBuildService queryBuildService;

    private Item item;

    @Before
    public void setUp() throws Exception {
        Group nestedGroup = new Group();

        List<Item> rules = new ArrayList<>();
        Rule rule = new Rule();
        rule.setId("car");
        rule.setField("color");
        rule.setValue("red");
        rules.add(rule);

        Rule rule1 = new Rule();
        rule1.setId("car");
        rule1.setField("model");
        rule1.setValue("Auris");
        rules.add(rule1);

        List<Item> rules1 = new ArrayList<>();
        Rule rule2 = new Rule();
        rule2.setId("car");
        rule2.setField("manufactor");
        rule2.setValue("Toyota");
        rules1.add(rule2);

        nestedGroup.setRules(rules1);
        nestedGroup.setCondition("OR");

        rules.add(nestedGroup);

        Group group = new Group();
        group.setRules(rules);
        group.setCondition("AND");

        item = group;
    }

    @Test
    public void testGetQueryString() throws Exception {
        String expected = "select * from car where (car.color = 'red' and car.model = 'Auris' and car" +
                ".manufactor = 'Toyota')";

        Assert.assertEquals(expected, queryBuildService.getQueryString(item));
    }

    @Test
    public void testGetQuery() throws Exception {
        SelectQuery selectQuery = dslContextDatabase.selectQuery();
        selectQuery.addFrom(table("car"));
        selectQuery.addConditions(field("car.color").equal("red").and(field("car.model").equal("Auris")));
        selectQuery.addConditions(field("car.manufactor").equal("Toyota"));

        Assert.assertEquals(selectQuery, queryBuildService.getQuery(item));
    }

    @Test
    public void testClarifyCondition() throws Exception {
        Condition groupCondition = field("car.color").equal("red");
        Condition condition = field("car.model"). equal("Kuga");
        Condition expectedAndCondition = groupCondition.and(condition);

        Assert.assertEquals(expectedAndCondition, queryBuildService.clarifyCondition(groupCondition, "AND",
                condition));

        Condition expectedOrCondition = groupCondition.or(condition);
        Assert.assertEquals(expectedOrCondition, queryBuildService.clarifyCondition(groupCondition, "OR", condition));
    }

    @Test
    public void testGetRules() throws Exception {
        Assert.assertEquals(2, queryBuildService.getRules((Group)item).size());
    }

    @Test
    public void testGetGroups() throws Exception {
        Assert.assertEquals(1, queryBuildService.getGroups((Group)item).size());
    }

    @Test
    public void testGetFullFieldName() throws Exception {
        Rule rule = new Rule();
        rule.setId("car");
        rule.setField("color");

        String expected = "car.color";
        Assert.assertEquals(expected, queryBuildService.getFullFieldName(rule));
    }

    @Test
    public void testGetOperator() throws Exception {
        String orOperator = "OR";
        Assert.assertEquals(Operator.OR, queryBuildService.getOperator(orOperator));
    }
}
