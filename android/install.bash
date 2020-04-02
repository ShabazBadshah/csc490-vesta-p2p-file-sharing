#! /bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: ./install.bash SERVER_URL"
  echo "Please provide the ngrok endpoint to connect to"
  exit 1
fi

echo "============ BUILDING ANDROID ============"

echo "\n------ Buidling APK ------"
gradle assemble

echo "\n------ Uninstalling com.vesta.android APK from all devices connected to ADB ------"
./run-parallel-adb.bash uninstall "com.vesta.android"

echo "\n------ Installing com.vesta.android APK to all devices connected to ADB ------"
./run-parallel-adb.bash -e install -r "app/build/outputs/apk/debug/app-debug.apk"

echo "\n------ Launching com.vesta.android APK on all devices connected to ADB ------"
./run-parallel-adb.bash shell am start -a android.intent.action.MAIN -n com.vesta.android/.implementation.view_impl.SplashScreenActivity -e "P2P_SERVER_URL" $1