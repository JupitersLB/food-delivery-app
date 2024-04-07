package test.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import test.models.MealTest;
import test.daos.MealDAOTest;

@Suite
@SelectClasses({MealTest.class, MealDAOTest.class})
public class MealTestSuite {}