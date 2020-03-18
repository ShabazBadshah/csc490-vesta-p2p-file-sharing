import React from "react";
import HostNav from '../components/hostNav';
import Menu from '../components/menu';


const submitClick = () => {
  var P2P = require('socket.io-p2p');
  var io = require('socket.io-client');
  var socket = io("http://3b3e9cb4.ngrok.io");
  var p2pSocket = new P2P(socket);

  var text = document.getElementById("submitText").value;

  socket.emit('peer-msg', {textVal : text});
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
