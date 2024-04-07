package test.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import test.models.CustomerTest;
import test.daos.CustomerDAOTest;

@Suite
@SelectClasses({CustomerTest.class, CustomerDAOTest.class})
public class CustomerTestSuite {}