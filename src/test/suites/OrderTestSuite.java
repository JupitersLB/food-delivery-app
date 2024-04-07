package test.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import test.daos.OrderDAOTest;
import test.models.OrderTest;

@Suite
@SelectClasses({OrderTest.class, OrderDAOTest.class})
public class OrderTestSuite {}