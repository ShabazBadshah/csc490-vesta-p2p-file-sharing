#! /bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: ./install.bash <avd_device_name>"
  echo "Example usage: ./install nex5x"
  exit 1
fi

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

echo "------ Launching Emulator ------"
# E.g. ~/Library/Android/sdk/emulator/emulator @nex5x -kernel ~/Library/Android/sdk/system-images/android-23/default/x86_64/kernel-qemu
# The following is: <path to emulator executable> @<name of avd device created> <path to system image being run on the avd device>
~/Library/Android/sdk/emulator/emulator @"$1" &

echo "------ Uninstalling com.vesta.android APK from all devices connected to ADB ------"
./android/run-parallel-adb.bash uninstall "com.vesta.android"

echo "------ Installing com.vesta.android APK to all devices connected to ADB ------"
./android/run-parallel-adb.bash -e install -r "./android/app/build/outputs/apk/debug/app-debug.apk"

echo "------ Launching com.vesta.android APK on all devices connected to ADB ------"
./android/run-parallel-adb.bash shell am start -a android.intent.action.MAIN -n com.vesta.android/.implementation.view_impl.SplashScreenActivity -e "P2P_SERVER_URL" "$NGROK_URL"