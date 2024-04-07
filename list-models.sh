#!/bin/bash

# Directory containing model classes
MODEL_DIR="./src/main/java/com/fooddelivery/models"

# Output file containing the list of model class names
OUTPUT_FILE="./src/main/resources/model_classes.txt"

# Create or empty the file
> $OUTPUT_FILE

# Loop through java files in the model directory
for file in $MODEL_DIR/*.java; do
    # Extract the class name from the file name
    className=$(basename "$file" .java)
    
    # Write the fully qualified class name to the output file
    echo "com.fooddelivery.models.$className" >> $OUTPUT_FILE
done

echo "Model class list generated at $OUTPUT_FILE"