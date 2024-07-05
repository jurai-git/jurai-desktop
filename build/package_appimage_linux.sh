#!/bin/bash

if [ "$1" = "clean" ]; then
	sudo rm -rf out
	sudo rm -rf var
	mkdir out
	mkdir var
	cd ..
	mvn clean
	cd build || exit
	exit
fi

log_and_exit() {
	echo "[APPIMAGE_PACKAGE_SCRIPT] Error: $1"
	exit
}

# clean everything
sudo rm -rf out
sudo rm -rf var
mkdir out
mkdir var

POM_FILE="../pom.xml"

# Extract pom info
VERSION=$(awk -F '[><]' '/<version>/{print $3; exit}' $POM_FILE) || log_and_exit "Error nreading pom.xml file"
ARTIFACT_ID=$(awk -F '[><]' '/<artifactId>/{print $3; exit}' $POM_FILE) || log_and_exit "Error nreading pom.xml file"

echo "[APPIMAGE_PACKAGE_SCRIPT] Successfully read pom file"

# Create JPackage and move it to /var/
cd ..
mvn clean compile javafx:jlink jpackage:jpackage@linux_appimage
sudo cp -r target/dist/JurAI build/var || log_and_exit "Error creating JPackage"

echo "[APPIMAGE_PACKAGE_SCRIPT] Successfully JPackaged package"

cd build || log_and_exit "Error entering build directory"
cd out || log_and_exit "Error entering output directory"

# Create AppDir directory and its subdirectories
mkdir "${ARTIFACT_ID}".AppDir
cd "${ARTIFACT_ID}".AppDir || log_and_exit "Error creating AppDir directory"

mkdir usr
cd usr || log_and_exit "Error generating AppDir directory"

sudo mv ../../../var/"${ARTIFACT_ID}"/* .
mkdir -p share/application
mkdir -p share/icons/hicolor/256x256
sudo cp lib/"${ARTIFACT_ID}".png share/icons/hicolor/256x256/"${ARTIFACT_ID}".png
touch share/application/"${ARTIFACT_ID}".desktop
echo "[Desktop Entry]
Name=${ARTIFACT_ID}
Exec=${ARTIFACT_ID} %F
Icon=${ARTIFACT_ID}
Type=Application
Categories=Utility;
Comment=Auxiliador jurídico
Terminal=false
StartupNotify=true" >>share/application/"${ARTIFACT_ID}".desktop
cd ..
touch AppRun
cp ../../SampleAppRun AppRun
chmod a+x AppRun

ln -s usr/share/icons/hicolor/256x256/"${ARTIFACT_ID}".png "${ARTIFACT_ID}".png
cp usr/share/application/"${ARTIFACT_ID}".desktop "${ARTIFACT_ID}".desktop

cd ..
pwd
ls
appimagetool "${ARTIFACT_ID}".AppDir || log_and_exit "Error creating appimage"
cd ..
echo "success"
