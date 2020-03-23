import React from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';


var localConnection = null;   // RTCPeerConnection for our "local" connection
var remoteConnection = null;  // RTCPeerConnection for the "remote"
  
var sendChannel = null;       // RTCDataChannel for the local (sender)
var receiveChannel = null;    // RTCDataChannel for the remote (receiver)

var P2P = require('socket.io-p2p');
var io = require('socket.io-client');
var socket = io("http://b8574d31.ngrok.io");
var opts = {autoUpgrade: false, numClients: 10};
var p2pSocket = new P2P(socket, opts)
p2pSocket.on('peer-msg', function (data) {
  console.log('From a peer %s', data.textVal);
});
p2pSocket.on('go-private', function () {
  p2pSocket.upgrade(); // upgrade to peerConnection
  privateClick();
});
const privateClick = () => {
  socket.emit('go-private', true);
  p2pSocket.useSockets = false;

}

const submitClick = () => {
  var text = document.getElementById("submitText").value;

  p2pSocket.emit('peer-msg', {textVal : text});
  console.log("IT CLICKED")



  // localConnection = new RTCPeerConnection();

  //   sendChannel = localConnection.createDataChannel("sendChannel");
  //   sendChannel.onopen = handleSendChannelStatusChange;
  //   sendChannel.onclose = handleSendChannelStatusChange;
    
  //   remoteConnection = new RTCPeerConnection();
  //   remoteConnection.ondatachannel = receiveChannelCallback;

  //   localConnection.onicecandidate = e => !e.candidate
  //   || remoteConnection.addIceCandidate(e.candidate)
  //   .catch((error) => console.log("addICECandidate failed: " + error.toString()));

  //   remoteConnection.onicecandidate = e => !e.candidate
  //   || localConnection.addIceCandidate(e.candidate)
  //   .catch((error) => console.log("addICECandidate failed!: " + error.toString()));
    
  //   console.log("creating offer now")
  //   localConnection.createOffer()
  //   .then(offer => localConnection.setLocalDescription(offer))
  //   .then(() => remoteConnection.setRemoteDescription(localConnection.localDescription))
  //   .then(() => remoteConnection.createAnswer())
  //   .then(answer => remoteConnection.setLocalDescription(answer))
  //   .then(() => localConnection.setRemoteDescription(remoteConnection.localDescription))
  //   .catch((error) => console.log("Unable to create an offer: " + error.toString()));
}

function handleSendChannelStatusChange(event) { // Fix
  if (sendChannel) {
    var state = sendChannel.readyState;
  
    // if (state === "open") {
    //   messageInputBox.disabled = false;
    //   messageInputBox.focus();
    //   sendButton.disabled = false;
    //   disconnectButton.disabled = false;
    //   connectButton.disabled = true;
    // } else {
    //   messageInputBox.disabled = true;
    //   sendButton.disabled = true;
    //   connectButton.disabled = false;
    //   disconnectButton.disabled = true;
    // }
  }
}

function receiveChannelCallback(event) {
  receiveChannel = event.channel;
  receiveChannel.onmessage = handleReceiveMessage;//TODO
  receiveChannel.onopen = handleReceiveChannelStatusChange;//TODO
  receiveChannel.onclose = handleReceiveChannelStatusChange;//TODO
}
function handleReceiveMessage(event) {
  var el = document.createElement("p");
  var txtNode = document.createTextNode(event.data);
  
  el.appendChild(txtNode);
  //receiveBox.appendChild(el);
}
function handleReceiveChannelStatusChange(event) {
  if (receiveChannel) {
    console.log("Receive channel's status has changed to " +
                receiveChannel.readyState);
  }
}
const HostPage = () => {


  return (
    <div>

    <HostNav> </HostNav>

    <Menu> </Menu>

    <input id="submitText" type="text"/>
    <button type="submit" onClick={submitClick}>Submit</button>
    <button id="privateButton" type="submit" onClick={privateClick}>Private</button>


    </div>

    

  );
};

export default HostPage;
