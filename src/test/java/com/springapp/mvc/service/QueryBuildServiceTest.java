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
import java.util.Arrays;
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

    @Autowired
    private ItemBuilder itemBuilder;

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
        Condition condition = field("car.model").equal("Kuga");
        Condition expectedAndCondition = groupCondition.and(condition);

        Assert.assertEquals(expectedAndCondition, queryBuildService.clarifyCondition(groupCondition, "AND", condition));

        Condition expectedOrCondition = groupCondition.or(condition);
        Assert.assertEquals(expectedOrCondition, queryBuildService.clarifyCondition(groupCondition, "OR", condition));
    }

    @Test
    public void testGetRules() throws Exception {
        Assert.assertEquals(2, queryBuildService.getRules((Group) item).size());
    }

    @Test
    public void testGetGroups() throws Exception {
        Assert.assertEquals(1, queryBuildService.getGroups((Group) item).size());
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

        String andOperator = "AND";
        Assert.assertEquals(Operator.AND, queryBuildService.getOperator(andOperator));
    }

    @Test
    public void testQueryBuildOfRules() throws Exception {
        List<Item> rules = Arrays.asList(ItemBuilder.CAR_RED, ItemBuilder.CAR_KUGA);
        Item item = itemBuilder.build(rules, "OR");
        String expectedQuery = "select * from car where (car.color = 'red' or car.model = 'Kuga')";
        Assert.assertEquals(expectedQuery, queryBuildService.getQueryString(item));
    }

    @Test
    public void testQueryBuildOfRule() throws Exception {
        List<Item> rules = Arrays.asList(ItemBuilder.CAR_RED);
        Item item = itemBuilder.build(rules, "OR");
        String expectedQuery = "select * from car where car.color = 'red'";
        Assert.assertEquals(expectedQuery, queryBuildService.getQueryString(item));
    }

    @Test
    public void testQueryBuildOfRuleAndGroup() throws Exception {
        Item group = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_RED, ItemBuilder.CAR_KUGA), "OR");

        Item testedItem = itemBuilder.build(Arrays.asList(group, ItemBuilder.CAR_3), "AND");
        String expectedQuery = "select * from car where (car.id = '3' and (car.color = 'red' or car.model = 'Kuga')" +
                ")";
        Assert.assertEquals(expectedQuery, queryBuildService.getQueryString(testedItem));
    }

    @Test
    public void testQueryBuildOfGroups() throws Exception {
        Item group1 = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_BLUE, ItemBuilder.DRIVER_MAKAR), "AND");
        Item group2 = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_BLACK, ItemBuilder.DRIVER_SLAVIK), "OR");
        Item testedItem = itemBuilder.build(Arrays.asList(group1, group2), "OR");
        String expectedString = "select * from car, driver where ((car.color = 'blue' and driver.last_name = " +
                "'Makar') or car.color = 'black' or driver.first_name = 'Slavik')";
        Assert.assertEquals(expectedString, queryBuildService.getQueryString(testedItem));
    }

    @Test
    public void testQueryBuildOf5NestedGroups() throws Exception {
        Item group1 = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_BLUE, ItemBuilder.DRIVER_MAKAR), "AND");
        Item group3 = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_RED), "OR");
        Item group4 = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_3, ItemBuilder.CAR_GOLD), "AND");
        Item group5 = itemBuilder.build(Arrays.asList(ItemBuilder.CAR_BLACK, ItemBuilder.CAR_GOLD), "OR");

        Item group21 = itemBuilder.build(Arrays.asList(group1, ItemBuilder.DRIVER_MAKAR), "OR");
        Item group3214 = itemBuilder.build(Arrays.asList(group3, group21, group4), "OR");
        Item group6 = itemBuilder.build(Arrays.asList(group3214, group5), "AND");
        Item testedItem = itemBuilder.build(Arrays.asList(group6, ItemBuilder.CAR_GOLD), "OR");
        String expectedString = "select * from car, driver where " +
                "(car.color = 'gold' or ((car.color = 'red' or driver.last_name = 'Makar' or (car.color = 'blue' and" +
                " driver.last_name = 'Makar') or (car.id = '3' and car.color = 'gold')) and (car.color = 'black' or car.color = 'gold')))";
        Assert.assertEquals(expectedString, queryBuildService.getQueryString(testedItem));
    }
}
