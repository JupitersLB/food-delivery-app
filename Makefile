# Specify the target directory for compiled .class files
OUT_DIR = ./out

SRC_DIRC = ./src/main/java

# Specify the database directory and name
DB_DIR = data
DB_NAME = database.db
DB_PATH = ./$(DB_DIR)/$(DB_NAME)
TEST_DB_PATH = ./$(DB_DIR)/test.db

# JUnit and SQLite JDBC jar paths
JUNIT_JAR = lib/junit-platform-console-standalone-1.10.2.jar
SQLITE_JDBC_JAR = lib/sqlite-jdbc-3.45.2.0.jar
SLF_JAR = lib/slf4j-api-1.7.36.jar
MOCKITO_JAR = lib/mockito-core-5.11.0.jar
BYTE_BUDDY_AGENT_JAR = lib/byte-buddy-agent-1.14.13.jar
BYTE_BUDDY_JAR = lib/byte-buddy-1.14.13.jar

# SQLite CLI tool command
SQLITE3 = sqlite3

# Classpath including the out directory and both JUnit and SQLite JDBC jars for running tests
TEST_CLASSPATH = $(OUT_DIR):$(JUNIT_JAR):$(SQLITE_JDBC_JAR):$(SLF_JAR):$(MOCKITO_JAR):$(BYTE_BUDDY_AGENT_JAR):$(BYTE_BUDDY_JAR)

# Classpath including the out directory and the SQLite JDBC jar for running the application
APP_CLASSPATH = $(OUT_DIR):$(SQLITE_JDBC_JAR):$(SLF_JAR)

generate-model-list:
	./list-models.sh

# Rule for compiling the Java application
compile: generate-model-list
	javac -d $(OUT_DIR) -cp $(APP_CLASSPATH) $(SRC_DIRC)/com/fooddelivery/**/*.java $(SRC_DIRC)/com/fooddelivery/*.java

# Define the target entry for starting the application
run: compile
	java -cp $(APP_CLASSPATH) com.fooddelivery.App

compile-all-tests: compile
	javac -d $(OUT_DIR) -cp $(TEST_CLASSPATH) ./src/test/**/*.java 

# Rule to run tests
run-all-tests: compile-all-tests
	java -Ddb.name=test.db -jar $(JUNIT_JAR) --class-path $(TEST_CLASSPATH) --scan-class-path

# Compile specific model and its test
compile-test: generate-model-list
	javac -d $(OUT_DIR) -cp $(TEST_CLASSPATH) $(SRC_DIRC)/com/fooddelivery/utils/*.java $(SRC_DIRC)/com/fooddelivery/daos/$(ENTITY)DAO.java $(SRC_DIRC)/com/fooddelivery/models/$(ENTITY).java $(SRC_DIRC)/com/fooddelivery/controllers/$(ENTITY)Controller.java ./src/test/models/$(ENTITY)Test.java ./src/test/controllers/$(ENTITY)ControllerTest.java ./src/test/controllers/AbstractControllerTest.java ./src/test/daos/$(ENTITY)DAOTest.java ./src/test/daos/AbstractDAOTest.java ./src/test/suites/$(ENTITY)TestSuite.java

# Rule to run specific test
run-test: compile-test
	java -Ddb.name=test.db -jar $(JUNIT_JAR) --class-path $(TEST_CLASSPATH) --select-class test.suites.$(ENTITY)TestSuite

drop-db:
	rm -f $(DB_PATH)
	rm -f $(TEST_DB_PATH)

# Define a clean action to remove the bin directory
clean:
	$(RM) -r $(OUT_DIR)

.PHONY: compile run clean compile-all-tests run-all-tests compile-test run-test drop-db generate-model-list
