#!/bin/bash

# Directories
SRC_DIR="./src/main/java/com/fooddelivery"
TEST_SRC_DIR="./src/test"
OUT_DIR="./out"
DB_NAME="test.db"

# JAR paths
JUNIT_JAR="./lib/junit-platform-console-standalone-1.10.2.jar"
SQLITE_JDBC_JAR="./lib/sqlite-jdbc-3.45.2.0.jar"
SLF_JAR="./lib/slf4j-api-1.7.36.jar"
MOCKITO_JAR="./lib/mockito-core-5.11.0.jar"
BYTE_BUDDY_AGENT_JAR="./lib/byte-buddy-agent-1.14.13.jar"
BYTE_BUDDY_JAR="./lib/byte-buddy-1.14.13.jar"

# Classpath
CLASSPATH="${OUT_DIR}:${JUNIT_JAR}:${SQLITE_JDBC_JAR}:${SLF_JAR}:${MOCKITO_JAR}:${BYTE_BUDDY_AGENT_JAR}:${BYTE_BUDDY_JAR}"

# Entity and Component passed as arguments
ENTITY=$1
COMPONENT=$2

# Always compile utils
find "${SRC_DIR}/utils" -type f -name "*.java" | xargs javac -d "${OUT_DIR}" -cp "${CLASSPATH}"

# Function to compile source and test files
compile_and_test() {
  local component_path=$1

  # Compile abstract test classes
  find "${TEST_SRC_DIR}/${component_path}s" -type f -name "Abstract*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +

  if [[ "$ENTITY" == "Order" && "$component_path" == "model" ]]; then
    # Compile all models
    find "${SRC_DIR}/models" -type f -name "*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +
  elif [[ "$ENTITY" == "Order" && ( "$component_path" == "dao" || "$component_path" == "controller" ) ]]; then
    # Compile all daos and models
    find "${SRC_DIR}/daos" -type f -name "*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +
    find "${SRC_DIR}/models" -type f -name "*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +
  else
    # Compile model always, as it's a dependency for both dao and controller
    find "${SRC_DIR}/models" -type f -name "${ENTITY}*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +

    if [[ "$component_path" == "controller" ]]; then
      # Compile dao as it's a dependency for controller
      find "${SRC_DIR}/daos" -type f -name "${ENTITY}*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +
    fi
  fi

  # Compile related source files
  find "${SRC_DIR}/${component_path}s" -type f -name "${ENTITY}*.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +

  # Compile related test files
  find "${TEST_SRC_DIR}/${component_path}s" -type f -name "${ENTITY}*Test.java" -exec javac -d "${OUT_DIR}" -cp "${CLASSPATH}" {} +
}

if [ -z "$COMPONENT" ]; then
  # No component specified, compile and test for all components
  for component in model dao controller; do  # Adjust these component names as per your project structure
      compile_and_test "$component"
  done
else
  # Compile and test for the specified component
  compile_and_test "$COMPONENT"
fi

# Run the tests
java -Ddb.name="${DB_NAME}" -jar "${JUNIT_JAR}" --class-path "${CLASSPATH}" --scan-class-path

