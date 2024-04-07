# Specify the target directory for compiled .class files
OUT_DIR = ./out

SRC_DIRC = ./src/main/java

# Specify the database directory and name
DB_DIR = data
DB_NAME = database.db
DB_PATH = ./$(DB_DIR)/$(DB_NAME)

# JUnit and SQLite JDBC jar paths
JUNIT_JAR = lib/junit-platform-console-standalone-1.10.2.jar
SQLITE_JDBC_JAR = lib/sqlite-jdbc-3.45.2.0.jar
SLF_JAR = lib/slf4j-api-1.7.36.jar

# SQLite CLI tool command
SQLITE3 = sqlite3

# Classpath including the out directory and both JUnit and SQLite JDBC jars for running tests
TEST_CLASSPATH = $(OUT_DIR):$(JUNIT_JAR):$(SQLITE_JDBC_JAR)

# Classpath including the out directory and the SQLite JDBC jar for running the application
APP_CLASSPATH = $(OUT_DIR):$(SQLITE_JDBC_JAR):$(SLF_JAR)

generate-model-list:
	./list-models.sh

# Rule for compiling the Java application
compile: generate-model-list
	javac -d $(OUT_DIR) -cp $(APP_CLASSPATH) $(SRC_DIRC)/com/fooddelivery/**/*.java $(SRC_DIRC)/com/fooddelivery/utils/*.java

# Define the target entry for starting the application
run: compile
	java -cp $(APP_CLASSPATH) com.fooddelivery.App

compile-all-tests: compile
	javac -cp $(TEST_CLASSPATH) ./src/test/**/*.java -d $(OUT_DIR)

# Rule to run tests
run-all-tests: compile-all-tests
	java -jar $(JUNIT_JAR) --class-path $(OUT_DIR) --scan-class-path

# Compile specific model and its test
compile-test: 
	javac -d $(OUT_DIR) -cp $(TEST_CLASSPATH) $(SRC_DIRC)/com/fooddelivery/models/$(MODEL).java src/test/models/$(TEST).java

# Rule to run specific test
run-test: compile-test
	java -jar $(JUNIT_JAR) --class-path $(TEST_CLASSPATH) --scan-class-path --select-class com.fooddelivery.test.$(TEST)

drop-table:
	$(SQLITE3) $(DB_PATH) "DROP TABLE IF EXISTS $(TABLE_NAME);"

drop-db:
	rm -f $(DB_PATH)

# Define a clean action to remove the bin directory
clean:
	$(RM) -r $(OUT_DIR)

.PHONY: compile run clean compile-all-tests run-all-tests compile-test run-test
