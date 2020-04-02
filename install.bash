#! /bin/bash

# Any subsequent(*) commands which fail will cause the shell script to exit immediately
# set -e

if [ "$#" -ne 2 ]; then
  echo 'Usage: ./install.bash <avd_device_name> <p2p_server_endpoint>'
  echo 'Example usage: ./install nex5x https://3c058a5b.ngrok.io'
  exit 1
fi

# Killing previous instances of ngrok 3 times because sometimes it doesn't want to die
pkill -f java
pkill -f node

P2P_SERVER_ENDPOINT_URL=$2

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

# printf "\n====== Uninstalling com.vesta.android APK from all devices connected to ADB ======\n"
# ./android/run-parallel-adb.bash uninstall "com.vesta.android"

printf "\n====== Rinstalling com.vesta.android APK to all devices connected to ADB ======\n"
./android/run-parallel-adb.bash -e install -r "./android/app/build/outputs/apk/debug/app-debug.apk"

printf "\n====== Launching com.vesta.android APK on all devices connected to ADB ======\n"
./android/run-parallel-adb.bash shell am start -a android.intent.action.MAIN -n com.vesta.android/.implementation.view_impl.SplashScreenActivity -e "P2P_SERVER_URL" "$P2P_SERVER_ENDPOINT_URL"
printf "\n"
printf "\n"


printf "==========================================\n"
printf "============ BUILDING BACKEND ============\n"
printf "==========================================\n"
printf "\n"
(cd backend && npm install && (nohup npm run dev >> backend.log &) && cd ..)
printf "\n"
printf "\n"


printf "===========================================\n"
printf "============ BUILDING FRONTEND ============\n"
printf "===========================================\n"
printf "\n"
(cd frontend && npm install && npm install && REACT_APP_NGROK_URL="$P2P_SERVER_ENDPOINT_URL" npm start)
printf "\n"
printf "\n"


printf "===========================================\n"
printf "============ RUNNING VESTA APP ============\n"
printf "===========================================\n"
printf "\n"
printf "\n"
