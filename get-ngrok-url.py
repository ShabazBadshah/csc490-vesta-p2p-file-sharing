import json
import os 

# Modified from https://stackoverflow.com/questions/34322988/view-random-ngrok-url-when-run-in-background

os.system("curl --silent http://localhost:4040/api/tunnels > tunnels.json")

with open('tunnels.json') as data_file:    
    datajson = json.load(data_file)

ngrok_url = datajson['tunnels'][0]['public_url']
print(ngrok_url)

