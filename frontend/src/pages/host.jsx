import React from "react";
import QrGenerator from '../components/qrGenerator';


const submitClick = () => {
  var P2P = require('socket.io-p2p');
  var io = require('socket.io-client');
  var socket = io("http://13be2d76.ngrok.io");
  var p2pSocket = new P2P(socket);

  var text = document.getElementById("submitText").value;

  //send msg to server on peer-msg channel
  socket.emit('peer-msg', {textVal : text});
  console.log("IT CLICKED")

  //recieve msg from server from the channel
  socket.on('peer-msg', function(msg){
    console.log(msg);
  });

}

const HostPage = () => {


  return (
    <div>

    <h1> Host </h1>

    <QrGenerator> </QrGenerator>

    <input id="submitText" type="text"/>
    <button type="submit" onClick={submitClick}>Submit</button>


    </div>

    

  );
};

export default HostPage;
