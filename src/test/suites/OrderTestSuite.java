package test.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import test.controllers.OrderControllerTest;
import test.daos.OrderDAOTest;
import test.models.OrderTest;

@Suite
@SelectClasses({OrderTest.class, OrderDAOTest.class, OrderControllerTest.class})
public class OrderTestSuite {}