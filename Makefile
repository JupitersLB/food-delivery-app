# Specify the target directory for compiled .class files
OUT_DIR = ./out

# Main class for running the app
MAIN_CLASS = com.fooddelivery.App

# Rule for compiling the Java application
compile:
	javac -d $(OUT_DIR) src/com/fooddelivery/App.java

# Define the target entry for starting the application
run: compile
	java -cp $(OUT_DIR) src/com.fooddelivery.App

# Define a clean action to remove the bin directory
clean:
	$(RM) -r $(OUT_DIR)

.PHONY: complie run clean