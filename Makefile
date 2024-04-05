# Specify the target directory for compiled .class files
OUT_DIR = ./out

# JUnit standalone jar
JUNIT_JAR = lib/junit-platform-console-standalone-1.10.2.jar

# Rule for compiling the Java application
compile:
	javac -d $(OUT_DIR) src/main/com/fooddelivery/App.java

# Define the target entry for starting the application
run: compile
	java -cp $(OUT_DIR) src/com.fooddelivery.App

compile-all-tests: compile
	javac -cp $(CLASS_DIR):lib/junit-platform-console-standalone-1.10.2.jar ./src/test/**/*.java -d $(OUT_DIR)

# Rule to run tests
run-all-tests: compile-all-tests
	java -jar $(JUNIT_JAR) --class-path out --scan-class-path

# Compile specific model and its test
compile-test: 
	javac -d $(OUT_DIR) -cp $(JUNIT_JAR) src/main/com/fooddelivery/models/$(MODEL).java src/test/models/$(TEST).java

# Rule to run specific test
run-test: compile-test
	java -jar $(JUNIT_JAR) --class-path $(OUT_DIR) --scan-class-path --select-class com.fooddelivery.test.$(TEST)

# Define a clean action to remove the bin directory
clean:
	$(RM) -r $(OUT_DIR)

.PHONY: complie run clean test run-tests compile-specific-test run-partial-test