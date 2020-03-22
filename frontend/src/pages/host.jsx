import React from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';

//const submit click function JUST for TESTING purposes
//will be REMOVED when needed 
const submitClick = () => {
  var P2P = require('socket.io-p2p');
  var io = require('socket.io-client');
  var socket = io("http://21bda266.ngrok.io");
  var p2pSocket = new P2P(socket);

  var text = document.getElementById("submitText").value;

    //recieve msg from server from the channel
  //will get the encrypted sym key
  socket.emit('peer-msg', {textVal : text});

  socket.on('peer-msg', function(msg){
    console.log("IN HERERE")
    console.log(msg);
  });
  //send msg to server on peer-msg channel
  console.log("IT CLICKED")


}

const HostPage = () => {


  return (
    <div>
    <HostNav> </HostNav>

    <Menu> </Menu>

    <input id="submitText" type="text"/>
    <button type="submit" onClick={submitClick}>Submit</button>


    </div>

    

  );
};

export default HostPage;
