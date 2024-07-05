#!/bin/bash

log_and_exit() {
	echo "[JAR_PACKAGE_SCRIPT] Error: $1"
	exit
}

cd ..

POM_FILE="pom.xml"

# Extract pom info
VERSION=$(awk -F '[><]' '/<version>/{print $3; exit}' $POM_FILE)
ARTIFACT_ID=$(awk -F '[><]' '/<artifactId>/{print $3; exit}' $POM_FILE)

#clean and package
mvn clean
mvn package

#get jar file
cp target/"${ARTIFACT_ID}"-"${VERSION}"-jar-with-dependencies.jar build/out/"${ARTIFACT_ID}"-"${VERSION}".jar

cd build || log_and_exit "Error reentering build directory"

