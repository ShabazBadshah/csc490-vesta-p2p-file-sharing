#! /bin/bash

# if [ "$#" -ne 1 ]; then
#   echo "Usage: ./install.bash SERVER_URL"
#   echo "Please provide the ngrok endpoint to connect to"
#   exit 1
# fi

echo "============ RUNNING NGROK IN BACKGROUND ============"
ngrok http 80 > /dev/null &
NGROK_PID=$!
echo "Ngrok PID: $NGROK_PID"
echo "------ Grabbing Ngrok URL ------"
NGROK_URL=$(python ./get-ngrok-url.py)
echo "Ngrok URL: $NGROK_URL"

echo "============ BUILDING ANDROID ============"

echo "------ Buidling APK ------"
gradle --project-dir=android assemble

echo "------ Uninstalling com.vesta.android APK from all devices connected to ADB ------"
./android/run-parallel-adb.bash uninstall "com.vesta.android"

echo "------ Installing com.vesta.android APK to all devices connected to ADB ------"
./android/run-parallel-adb.bash -e install -r "./android/app/build/outputs/apk/debug/app-debug.apk"

echo "------ Launching com.vesta.android APK on all devices connected to ADB ------"
./android/run-parallel-adb.bash shell am start -a android.intent.action.MAIN -n com.vesta.android/.implementation.view_impl.SplashScreenActivity -e "P2P_SERVER_URL" "$NGROK_URL"