package test.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import test.daos.EmployeeDAOTest;
import test.models.EmployeeTest;


@Suite
@SelectClasses({EmployeeTest.class, EmployeeDAOTest.class})
public class EmployeeTestSuite {}