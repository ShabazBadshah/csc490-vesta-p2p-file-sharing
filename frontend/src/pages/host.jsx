import React from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';

var localConnection = null;   // RTCPeerConnection for our "local" connection
var remoteConnection = null;  // RTCPeerConnection for the "remote"
  
var sendChannel = null;       // RTCDataChannel for the local (sender)
var receiveChannel = null;    // RTCDataChannel for the remote (receiver)

var P2P = require('socket.io-p2p');
var io = require('socket.io-client');
var socket = io("http://5a435b75.ngrok.io");
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
}

const HostPage = () => {


  return (
    <div>
    <HostNav> </HostNav>

    {/* <Menu> </Menu> */}

    <input id="submitText" type="text"/>
    <button type="submit" onClick={submitClick}>Submit</button>
    <button id="privateButton" type="submit" onClick={privateClick}>Private</button>

    </div>

    

  );
};

export default HostPage;
