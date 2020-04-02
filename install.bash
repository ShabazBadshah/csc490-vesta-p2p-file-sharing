#! /bin/bash

if [ "$#" -ne 1 ]; then
  echo "Usage: ./install.bash <avd_device_name>"
  echo "Example usage: ./install nex5x"
  exit 1
fi

printf "=====================================================\n"
printf "============ RUNNING NGROK IN BACKGROUND ============\n"
printf "=====================================================\n"
printf "\n"
ngrok http 80 > /dev/null &
NGROK_PID=$!
printf "Ngrok PID: $NGROK_PID\n"
printf "====== Grabbing Ngrok URL ======\n"
NGROK_URL=$(python ./get-ngrok-url.py)
printf "Ngrok URL: $NGROK_URL\n"
printf "\n"
printf "\n"

printf "==========================================\n"
printf "============ BUILDING ANDROID ============\n"
printf "==========================================\n"
printf "\n"

printf "====== Buidling APK ======"
gradle --project-dir=android assemble

printf "\n====== Launching Emulator ======\n"
# E.g. ~/Library/Android/sdk/emulator/emulator @nex5x -kernel ~/Library/Android/sdk/system-images/android-23/default/x86_64/kernel-qemu
# The following is: <path to emulator executable> @<name of avd device created> <path to system image being run on the avd device>
~/Library/Android/sdk/emulator/emulator @"$1" &
printf "~/Library/Android/sdk/emulator/emulator @"$1" & \n"

printf "\n====== Uninstalling com.vesta.android APK from all devices connected to ADB ======\n"
./android/run-parallel-adb.bash uninstall "com.vesta.android"

printf "\n====== Installing com.vesta.android APK to all devices connected to ADB ======\n"
./android/run-parallel-adb.bash -e install -r "./android/app/build/outputs/apk/debug/app-debug.apk"

printf "\n====== Launching com.vesta.android APK on all devices connected to ADB ======\n"
./android/run-parallel-adb.bash shell am start -a android.intent.action.MAIN -n com.vesta.android/.implementation.view_impl.SplashScreenActivity -e "P2P_SERVER_URL" "$NGROK_URL"
printf "\n"
printf "\n"

printf "==========================================\n"
printf "============ BUILDING BACKEND ============\n"
printf "==========================================\n"
printf "\n"
(cd backend && npm install && (nohup npm run dev > backend.log &) && cd ..)

printf "===========================================\n"
printf "============ BUILDING FRONTEND ============\n"
printf "===========================================\n"
printf "\n"
(cd frontend && npm install && npm install && npm start)

printf "===========================================\n"
printf "============ RUNNING VESTA APP ============\n"
printf "===========================================\n"
printf "\n"