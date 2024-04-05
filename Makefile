# Specify the target directory for compiled .class files
OUT_DIR = ./out

# Main class for running the app
MAIN_CLASS = com.fooddelivery.App

# Rule for compiling the Java application
compile:
	javac -d $(OUT_DIR) src/main/com/fooddelivery/App.java

# Define the target entry for starting the application
run: compile
	java -cp $(OUT_DIR) src/com.fooddelivery.App

test: compile
	javac -cp $(CLASS_DIR):lib/junit-platform-console-standalone-1.10.2.jar ./src/test/**/*.java -d $(OUT_DIR)

# Rule to run tests
run-tests: test
	java -jar lib/junit-platform-console-standalone-1.10.2.jar --class-path out --scan-class-path

# Define a clean action to remove the bin directory
clean:
	$(RM) -r $(OUT_DIR)

.PHONY: complie run clean